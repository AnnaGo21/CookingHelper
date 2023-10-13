package com.example.demo.service;

import com.example.demo.dto.FoodDto;
import com.example.demo.dto.FoodDtoRegular;
import com.example.demo.entity.*;
import com.example.demo.repository.FoodRepository;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

@Transactional
@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    private List<FoodDto> deletedFoods;
    private List<FoodDto> restoredFoods;

    public List<FoodDto> getRestoredFoods() {
        return restoredFoods;
    }

    public FoodService(FoodRepository foodRepository, IngredientRepository ingredientRepository, RecipeRepository recipeRepository, UserRepository userRepository) {
        this.foodRepository = foodRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        deletedFoods = new ArrayList<>();
    }


    public FoodDto createFood(FoodDto food) {
        Food newFood = new Food();
        newFood.setFoodId(food.getFoodId());
        newFood.setFoodName(food.getFoodName());
        newFood.setCarbohydrates(food.getCarbohydrates());
        newFood.setProteins(food.getProteins());
        newFood.setFat(food.getFat());
        newFood.setCalories(food.getCalories());
        newFood.setType(food.getType());
        return foodToFoodDto(foodRepository.save(newFood));
    }

    public Food updateFood(int foodId, Food updatedFood) throws ChangeSetPersister.NotFoundException {
        Food existingFood = foodRepository.findById(foodId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        existingFood.setFoodName(updatedFood.getFoodName());
        existingFood.setProteins(updatedFood.getProteins());
        existingFood.setFat(updatedFood.getFat());
        existingFood.setCarbohydrates(updatedFood.getCarbohydrates());
        existingFood.setCalories(updatedFood.getCalories());
        return existingFood;
    }

    public void deleteFood(int foodId) {
        //Want to save food to restore then, just if we delete it by mistake
        Food foodToDelete = recipeRepository.findByFoodId(foodId);
        if(foodToDelete != null) {
            recipeRepository.deleteById(foodId);
            foodRepository.deleteById(foodId);
            deletedFoods.add(foodToFoodDto(foodToDelete));
        }
    }

    public List<FoodDto> getAllFoods() {
        List<Food> foodList = foodRepository.findAll();
        List<FoodDto> foodDtos = new ArrayList<>();
        for (Food food : foodList){
            foodDtos.add(foodToFoodDto(food));
        }
        return foodDtos;
    }

    public FoodDto searchFoodByName(String name) {
        Food foodByName = foodRepository.findByFoodName(name);
        if(foodByName == null){
            return null;
        }
        return foodToFoodDto(foodByName);
    }

    public List<FoodDto> searchFoodsByProtein(double minProtein) {
        List<Food> foodList = foodRepository.findByProteinsGreaterThanEqual(minProtein);
        List<FoodDto> foodDtos = new ArrayList<>();
        for (Food food : foodList){
            foodDtos.add(foodToFoodDto(food));
        }
        return foodDtos;
    }

    public List<FoodDto> searchFoodsByFat(double maxFat) {
        List<Food> foodList = foodRepository.findByFatLessThanEqual(maxFat);
        List<FoodDto> foodDtos = new ArrayList<>();
        for (Food food : foodList){
            foodDtos.add(foodToFoodDto(food));
        }
        return foodDtos;
    }

    public List<FoodDto> searchFoodsByCarbohydrates(double minCarbohydrates){
        List<Food> foodList = foodRepository.findByCarbohydratesGreaterThanEqual(minCarbohydrates);
        List<FoodDto> foodDtos = new ArrayList<>();
        for (Food food : foodList){
            foodDtos.add(foodToFoodDto(food));
        }
        return foodDtos;
    }

    public List<FoodDto> searchFoodsByCalories(double minCalories) {
        List<Food> foodList = foodRepository.findByCaloriesGreaterThanEqual(minCalories);
        List<FoodDto> foodDtos = new ArrayList<>();
        for (Food food : foodList){
            foodDtos.add(foodToFoodDto(food));
        }
        return foodDtos;
    }


    public List<FoodDto> getFoodByIngredientId(int ingredientId) {
        Optional ingredient = ingredientRepository.findById(ingredientId);
        if(ingredient.isPresent()){
            Ingredient ingredient1 = ingredientRepository.findById(ingredientId).get();
            return ingredient1.getIngredientsRecipesList()
                .stream()
                .map(ingredientsRecipes ->
                        foodToFoodDto(foodRepository.getByFoodId(ingredientsRecipes.getRecipe().getFoodId())))
                .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }


    /* One recipe is related to only one food */
    public FoodDto getFoodByRecipeId(int recipeId){
        Recipe recipe = recipeRepository.getReferenceById(recipeId);
        return foodToFoodDto(foodRepository.getByFoodId(recipe.getFoodId()));
    }

    private void calculateFoodSummary(Food food) {
        List<IngredientsRecipes> ingredientsRecipesList = food.getIngredientsRecipesList();
        if(ingredientsRecipesList == null){
            food.setTotalProteins(0);
            food.setTotalFats(0);
            food.setTotalCarbohydrates(0.0);
            food.setTotalCalories(0);
            return;
        }
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

        food.setTotalProteins(totalProteins);
        food.setTotalFats(totalFats);
        food.setTotalCarbohydrates(totalCarbohydrates);
        food.setTotalCalories(totalCalories);
    }


    public List<FoodDtoRegular> searchFoodByProteinsRangeAndType(String type, double minProteins, double maxProteins) {
        List<FoodDtoRegular> foodDtos = new ArrayList<>();
        List<Food> foodByType = foodRepository.findAllByType(type);

        for (Food food : foodByType) {
            calculateFoodSummary(food);
            if (food.getTotalProteins() >= minProteins && food.getTotalProteins() <= maxProteins) {
                foodDtos.add(foodToFoodDtoRegular(food));
            }
        }
        return foodDtos;
    }

    public List<FoodDtoRegular> searchFoodByFatRangeAndType(String type, double minFat, double maxFat) {
        List<FoodDtoRegular> foodDtos = new ArrayList<>();
        List<Food> foodByType = foodRepository.findAllByType(type);

        for (Food food : foodByType) {
            calculateFoodSummary(food);
            if (food.getTotalFats() >= minFat && food.getTotalFats() <= maxFat) {
                foodDtos.add(foodToFoodDtoRegular(food));
            }
        }
        return foodDtos;
    }

    public List<FoodDtoRegular> searchFoodByCarbohydratesRangeAndType(String type, double minCarbohydrates, double maxCarbohydrates) {
        List<FoodDtoRegular> foodDtos = new ArrayList<>();
        List<Food> foodByType = foodRepository.findAllByType(type);

        for (Food food : foodByType) {
            calculateFoodSummary(food);
            if (food.getTotalCarbohydrates() >= minCarbohydrates && food.getTotalCarbohydrates() <= maxCarbohydrates) {
                foodDtos.add(foodToFoodDtoRegular(food));
            }
        }
        return foodDtos;
    }

    public List<FoodDtoRegular> searchFoodByCaloriesRangeAndType(String type, double minCalories, double maxCalories) {
        List<FoodDtoRegular> foodDtos = new ArrayList<>();
        List<Food> foodByType = foodRepository.findAllByType(type);

        for (Food food : foodByType) {
            calculateFoodSummary(food);
            if (food.getTotalCalories() >= minCalories && food.getTotalCalories() <= maxCalories) {
                foodDtos.add(foodToFoodDtoRegular(food));
            }
        }
        return foodDtos;
    }

    private FoodDtoRegular foodToFoodDtoRegular(Food food) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return FoodDtoRegular.builder()
                .foodId(food.getFoodId())
                .foodName(food.getFoodName())
                .type(food.getType())
                .totalProteins(Double.parseDouble(decimalFormat.format(food.getTotalProteins())))
                .totalFats(Double.parseDouble(decimalFormat.format(food.getTotalFats())))
                .totalCarbohydrates(Double.parseDouble(decimalFormat.format(food.getTotalCarbohydrates())))
                .totalCalories(Double.parseDouble(decimalFormat.format(food.getTotalCalories())))
                .build();
    }

    private FoodDto foodToFoodDto(Food food){
        return FoodDto.builder()
                .foodId(food.getFoodId())
                .foodName(food.getFoodName())
                .carbohydrates(food.getCarbohydrates())
                .fat(food.getFat())
                .calories(food.getCalories())
                .proteins(food.getProteins())
                .build();
    }
}