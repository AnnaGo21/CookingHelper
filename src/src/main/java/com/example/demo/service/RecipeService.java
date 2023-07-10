package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.UserRepository;
import exceptions.UnauthorizedAccessException;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private List<RecipeDtoRegular> filteredLlist;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        filteredLlist = new ArrayList<>();
    }

    public RecipeDto createRecipe(RecipeDto recipeDto) {
        User createdBy = userRepository.getUserById(recipeDto.getCreatorUserId());
        Recipe newRecipe = new Recipe();
        newRecipe.setCreatedBy(createdBy);
        newRecipe.setRecipeId(recipeDto.getRecipeId());
        newRecipe.setRecipeName(recipeDto.getRecipeName());
        newRecipe.setFoodId(recipeDto.getFoodId());
        newRecipe.setPublic(recipeDto.isPublic());
        newRecipe.setCookingTime(recipeDto.getCookingTime());
        newRecipe.setCuisine(recipeDto.getCuisine());
        newRecipe.setIngredientsRecipesList(recipeDto.getIngredientsRecipesList());
        return recipeToRecipeDto(recipeRepository.save(newRecipe));
    }

    public List<RecipeDtoRegular> getAllRecipes() {
        List<Recipe> recipeList = recipeRepository.findAll();

        List<RecipeDtoRegular> recipeDtos = new ArrayList<>();
        for (Recipe recipe : recipeList){
            calculateRecipeSummary(recipe);
            recipeDtos.add(recipeToRecipeDtoRegular(recipe));
        }
        return recipeDtos;
    }

    private void calculateRecipeSummary(Recipe recipe) {
        List<IngredientsRecipes> ingredientsRecipesList = recipe.getIngredientsRecipesList();
        double totalProteins = 0;
        double totalFats = 0;
        double totalCarbohydrates = 0;
        double totalCalories = 0;

        for (IngredientsRecipes ingredientsRecipes : ingredientsRecipesList){
            Ingredient ingredient = ingredientsRecipes.getIngredient();
            double quantity = ingredientsRecipes.getQuantity();

            totalProteins += ingredient.getProteins() * quantity;
            totalFats += ingredient.getFat() * quantity;
            totalCarbohydrates += ingredient.getCarbohydrates() * quantity;
            totalCalories += ingredient.getCalories() * quantity;
        }

        recipe.setTotalProteins(totalProteins);
        recipe.setTotalFats(totalFats);
        recipe.setTotalCarbohydrates(totalCarbohydrates);
        recipe.setTotalCalories(totalCalories);
    }

    public void deleteRecipe(User user, int recipeID) throws UnauthorizedAccessException {
        Recipe recipe = recipeRepository.findByRecipeId(recipeID);
        if (recipe.getCreatedBy().equals(user)) {
            recipeRepository.delete(recipe);
        } else {
            throw new UnauthorizedAccessException("You are not authorized to delete this recipe. Only Admin or Creator can.");
        }
    }

    public List<RecipeDtoRegular> getRecipesByUser(int userId) {
        User searchUser = userRepository.getUserById(userId);
        if(searchUser == null){
            return Collections.emptyList();
        }
        List<Recipe> recipeList = recipeRepository.findByCreatedById(userId);
        List<RecipeDtoRegular> recipeDtos = new ArrayList<>();
        for (Recipe recipe : recipeList){
            recipeDtos.add(recipeToRecipeDtoRegular(recipe));
        }
        return recipeDtos;
    }

    public RecipeDtoRegular getRecipeById(int id){
        return recipeToRecipeDtoRegular(recipeRepository.findByRecipeId(id));
    }

    public List<RecipeDtoRegular> getAllPublicRecipes(boolean isPublic) {
        List<Recipe> publicRecipes = recipeRepository.findByIsPublic(isPublic);
        return publicRecipes.stream()
                .map(this::recipeToRecipeDtoRegular)
                .collect(Collectors.toList());
    }

    public List<RecipeDtoRegular> searchRecipesByIngredients(String ingredientName) {
        List<Recipe> recipeList = recipeRepository.findByIngredientsRecipesList_Ingredient_IngredientName(ingredientName);
        return recipeList.stream()
                .map(this::recipeToRecipeDtoRegular)
                .collect(Collectors.toList());

    }

    public List<RecipeDtoRegular> searchRecipesByProteinAndUser(int userId, double minProtein) {
        List<Recipe> recipeList = recipeRepository.findByIngredientsRecipesList_Ingredient_ProteinsGreaterThanEqual(minProtein);
        return getRecipeDtoRegulars(userId, recipeList);
    }

    public List<RecipeDtoRegular> searchRecipesByFatAndUser(int userId, double maxFat) {
        List<Recipe> recipeList = recipeRepository.findByIngredientsRecipesList_Ingredient_FatLessThanEqual(maxFat);
        return getRecipeDtoRegulars(userId, recipeList);
    }

    public List<RecipeDtoRegular> searchRecipesByCaloriesAndUser(int userId, double minCalories) {
        List<Recipe> recipeList = recipeRepository.findByIngredientsRecipesList_Ingredient_CaloriesGreaterThanEqual(minCalories);
        return getRecipeDtoRegulars(userId, recipeList);
    }

    public List<RecipeDtoRegular> searchRecipesByCarbohydratesAndUser(int userId, double minCarbohydrates){
        List<Recipe> recipeList = recipeRepository.findByIngredientsRecipesList_Ingredient_CarbohydratesGreaterThanEqual(minCarbohydrates);
        return getRecipeDtoRegulars(userId, recipeList);
    }

    private List<RecipeDtoRegular> getRecipeDtoRegulars(int userId, List<Recipe> recipeList) {
        filteredLlist = new ArrayList<>();
        for (Recipe recipe : recipeList){
            User user = recipe.getCreatedBy();
            if(user != null && user.getId() == userId){
                filteredLlist.add(recipeToRecipeDtoRegular(recipe));
            }
        }
        return filteredLlist;
    }


    private RecipeDtoRegular recipeToRecipeDtoRegular(Recipe recipe){
        User user = recipe.getCreatedBy();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        if(user != null) {
            return RecipeDtoRegular.builder()
                    .creatorUserId(recipe.getCreatedBy().getId())
                    .recipeId(recipe.getRecipeId())
                    .recipeName(recipe.getRecipeName())
                    .foodId(recipe.getFoodId())
                    .isPublic(recipe.isPublic())
                    .totalProteins(Double.parseDouble(decimalFormat.format(recipe.getTotalProteins())))
                    .totalFats(Double.parseDouble(decimalFormat.format(recipe.getTotalFats())))
                    .totalCarbohydrates(Double.parseDouble(decimalFormat.format(recipe.getTotalCarbohydrates())))
                    .totalCalories(Double.parseDouble(decimalFormat.format(recipe.getTotalCalories())))
                    .build();
        } else {
            return RecipeDtoRegular.builder()
                    .recipeId(recipe.getRecipeId())
                    .recipeName(recipe.getRecipeName())
                    .foodId(recipe.getFoodId())
                    .isPublic(recipe.isPublic())
                    .totalProteins(Double.parseDouble(decimalFormat.format(recipe.getTotalProteins())))
                    .totalFats(Double.parseDouble(decimalFormat.format(recipe.getTotalFats())))
                    .totalCarbohydrates(Double.parseDouble(decimalFormat.format(recipe.getTotalCarbohydrates())))
                    .totalCalories(Double.parseDouble(decimalFormat.format(recipe.getTotalCalories())))
                    .build();
        }
    }

    private RecipeDto recipeToRecipeDto(Recipe recipe){
        User createdBy = recipe.getCreatedBy();
        if (createdBy != null) {
            UserDto userDto = UserDto.builder()
                    .username(createdBy.getUsername())
                    .firstName(createdBy.getFirstName())
                    .lastName(createdBy.getLastName())
                    .email(createdBy.getEmail())
                    .build();

            return RecipeDto.builder()
                    .createdByDto(userDto)
                    .creatorUserId(createdBy.getId())
                    .recipeId(recipe.getRecipeId())
                    .recipeName(recipe.getRecipeName())
                    .foodId(recipe.getFoodId())
                    .isPublic(recipe.isPublic())
                    .cookingTime(recipe.getCookingTime())
                    .cuisine(recipe.getCuisine())
                    .ingredientsRecipesList(recipe.getIngredientsRecipesList())
                    .build();
        } else {
            return RecipeDto.builder()
                    .recipeId(recipe.getRecipeId())
                    .recipeName(recipe.getRecipeName())
                    .foodId(recipe.getFoodId())
                    .isPublic(recipe.isPublic())
                    .cookingTime(recipe.getCookingTime())
                    .cuisine(recipe.getCuisine())
                    .ingredientsRecipesList(recipe.getIngredientsRecipesList())
                    .build();
        }


//        List<IngredientsRecipesDto> ingredientsRecipesDTOList = new ArrayList<>();
//        List<IngredientsRecipes> ingredientsRecipesList = recipe.getIngredientsRecipesList();
//        if (ingredientsRecipesList != null) {
//            for (IngredientsRecipes ingredientsRecipes : ingredientsRecipesList) {
//                IngredientsRecipesDto ingredientsRecipesDTO = new IngredientsRecipesDto();
//                ingredientsRecipesDTO.setIngredientId(ingredientsRecipes.getIngredient().getIngredientId());
//                ingredientsRecipesDTO.setQuantity(ingredientsRecipes.getQuantity());
//                ingredientsRecipesDTO.setRecipeId(ingredientsRecipesDTO.getRecipeId());
//                ingredientsRecipesDTO.setId(ingredientsRecipesDTO.getId());
//                ingredientsRecipesDTOList.add(ingredientsRecipesDTO);
//            }
//            RecipeDto.builder()
//                    .ingredientsRecipesList(recipe.getIngredientsRecipesList());
//        }
    }

//    public Recipe getRecipeByIngredientId(int id) {
//        return recipeRepository.getRecipeByIngredientId(id);
//    }
}
