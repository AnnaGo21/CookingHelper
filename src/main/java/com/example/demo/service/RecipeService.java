package com.example.demo.service;

//import com.example.demo.config.IsOwnerOrAdmin;
import com.example.demo.dto.*;
import com.example.demo.model.ERole;
import com.example.demo.util.UserMapper;
import com.example.demo.entity.*;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.SearchRequest;
import com.example.demo.exceptions.UnauthorizedAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final RecipeSecurityService recipeSecurityService;
    private List<RecipeDtoRegular> filteredLlist;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository, RecipeSecurityService recipeSecurityService) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.recipeSecurityService = recipeSecurityService;
        filteredLlist = new ArrayList<>();
    }

    public RecipeDto createRecipe(RecipeDto recipeDto) {
        User createdBy = userRepository.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
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
        if(ingredientsRecipesList == null){
            recipe.setTotalProteins(0);
            recipe.setTotalFats(0);
            recipe.setTotalCarbohydrates(0.0);
            recipe.setTotalCalories(0);
            return;
        }
        double totalProteins = 0;
        double totalFats = 0;
        double totalCarbohydrates = 0.0;
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

    public void deleteRecipe(int recipeId) throws UnauthorizedAccessException {
        UserDto user = userMapper.UserToUserDto(recipeRepository.findByRecipeId(recipeId).getCreatedBy());

        String principalUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User principalUser = userRepository.getUserByUsername(principalUsername);
        boolean isPrincipalAdmin = principalUser.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ADMIN));

        if(!user.getUsername().equals(principalUsername) && !isPrincipalAdmin)
            throw new UnauthorizedAccessException("You are not authorized to delete this recipe. Only the creator can delete it.");
        recipeRepository.deleteById(recipeId);

    }

    private UserDto userToUserDto(User user){
        return UserDto.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    public List<RecipeDtoRegular> getRecipeDtosByUser(int userId) {
        User searchUser = userRepository.getUserById(userId);
        if(searchUser == null){
            return Collections.emptyList();
        }
        List<Recipe> recipeList = recipeRepository.findByCreatedById(userId);
        List<RecipeDtoRegular> recipeDtos = new ArrayList<>();
        for (Recipe recipe : recipeList){
            calculateRecipeSummary(recipe);
            recipeDtos.add(recipeToRecipeDtoRegular(recipe));
        }
        return recipeDtos;
    }

    public RecipeDtoRegular searchRecipeByName(String recipeName) {
        Recipe recipeByName = recipeRepository.findByRecipeNameContainingIgnoreCase(recipeName);
        if(recipeByName != null){
            calculateRecipeSummary(recipeByName);
            return recipeToRecipeDtoRegular(recipeByName);
        }
        return null;
    }

    public RecipeDtoRegular getRecipeById(int id){
        return recipeToRecipeDtoRegular(recipeRepository.findByRecipeId(id));
    }

    public List<RecipeDtoRegular> getAllPublicRecipes(boolean isPublic) {
        List<Recipe> publicRecipes = recipeRepository.findByIsPublic(isPublic);
        List<Recipe> calculatedPublicRecipes = new ArrayList<>();
        for (Recipe recipe : publicRecipes){
            calculateRecipeSummary(recipe);
            calculatedPublicRecipes.add(recipe);
        }
        return calculatedPublicRecipes.stream()
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

    // For total results
    public List<RecipeDtoRegular> searchRecipesByCarbohydratesRangeAndUser(int userId, double minCarbohydrates, double maxCarbohydrates) {
        List<RecipeDtoRegular> recipeDtos = new ArrayList<>();
        List<Recipe> userRecipes = getRecipesByUser(userId);

        for (Recipe recipe : userRecipes) {
            calculateRecipeSummary(recipe);
            User createdBy = recipe.getCreatedBy();

            if (createdBy != null && createdBy.getId() == userId) {
                if (recipe.getTotalCarbohydrates() >= minCarbohydrates && recipe.getTotalCarbohydrates() <= maxCarbohydrates) {
                    recipeDtos.add(recipeToRecipeDtoRegular(recipe));
                }
            }
        }
        return recipeDtos;
    }


    public List<RecipeDtoRegular> searchRecipesByProteinsRangeAndUser(int userId, double minProteins, double maxProteins) {
        List<RecipeDtoRegular> recipeDtos = new ArrayList<>();
        List<Recipe> userRecipes = getRecipesByUser(userId);

        for (Recipe recipe : userRecipes) {
            calculateRecipeSummary(recipe);
            User createdBy = recipe.getCreatedBy();

            if (createdBy != null && createdBy.getId() == userId) {
                if (recipe.getTotalProteins() >= minProteins && recipe.getTotalProteins() <= maxProteins) {
                    recipeDtos.add(recipeToRecipeDtoRegular(recipe));
                }
            }
        }
        return recipeDtos;
    }

    public List<RecipeDtoRegular> searchRecipesByFatRangeAndUser(int userId, double minFats, double maxFats) {
        List<RecipeDtoRegular> recipeDtos = new ArrayList<>();
        List<Recipe> userRecipes = getRecipesByUser(userId);

        for (Recipe recipe : userRecipes) {
            calculateRecipeSummary(recipe);
            User createdBy = recipe.getCreatedBy();

            if (createdBy != null && createdBy.getId() == userId) {
                if (recipe.getTotalFats() >= minFats && recipe.getTotalFats() <= maxFats) {
                    recipeDtos.add(recipeToRecipeDtoRegular(recipe));
                }
            }
        }
        return recipeDtos;
    }

    public List<RecipeDtoRegular> searchRecipesByCaloriesRangeAndUser(int userId, double minCalories, double maxCalories) {
        List<RecipeDtoRegular> recipeDtos = new ArrayList<>();
        List<Recipe> userRecipes = getRecipesByUser(userId);

        for (Recipe recipe : userRecipes) {
            calculateRecipeSummary(recipe);
            User createdBy = recipe.getCreatedBy();

            if (createdBy != null && createdBy.getId() == userId) {
                if (recipe.getTotalCalories() >= minCalories && recipe.getTotalCalories() <= maxCalories) {
                    recipeDtos.add(recipeToRecipeDtoRegular(recipe));
                }
            }
        }
        return recipeDtos;
    }

    public List<RecipeDtoRegular> searchRecipesByNutrientRangesAndUser(int userId, SearchRequest nutrientRanges) {
        List<RecipeDtoRegular> recipeDtos = new ArrayList<>();
        List<Recipe> userRecipes = getRecipesByUser(userId);

        for (Recipe recipe : userRecipes) {
            calculateRecipeSummary(recipe);
            User createdBy = recipe.getCreatedBy();

            if (createdBy != null && createdBy.getId() == userId) {
                if (isInRange(recipe.getTotalCarbohydrates(), nutrientRanges.getMinCarbohydrates(), nutrientRanges.getMaxCarbohydrates())
                        && isInRange(recipe.getTotalFats(), nutrientRanges.getMinFats(), nutrientRanges.getMaxFats())
                        && isInRange(recipe.getTotalProteins(), nutrientRanges.getMinProtein(), nutrientRanges.getMaxProtein())
                        && isInRange(recipe.getTotalCalories(), nutrientRanges.getMinCalories(), nutrientRanges.getMaxCalories())) {
                    recipeDtos.add(recipeToRecipeDtoRegular(recipe));
                }
            }
        }
        return recipeDtos;
    }

    private boolean isInRange(double value, Double min, Double max) {
        return (min == null || value >= min) && (max == null || value <= max);
    }


    public List<Recipe> getRecipesByUser(int userId) {
        User searchUser = userRepository.getUserById(userId);
        if(searchUser == null){
            return Collections.emptyList();
        }
        return recipeRepository.findByCreatedById(userId);
    }



    // For transfer to DTO classes
    public RecipeDtoRegular recipeToRecipeDtoRegular(Recipe recipe){
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
            UserDto userDto = userToUserDto(createdBy);

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
    }
}
