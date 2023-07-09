package com.example.demo.service;

import com.example.demo.dto.FoodDto;
import com.example.demo.entity.Food;
import com.example.demo.entity.Ingredient;
import com.example.demo.entity.Recipe;
import com.example.demo.repository.FoodRepository;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    private List<FoodDto> deletedFoods;
    private List<FoodDto> restoredFoods;


    public FoodService(FoodRepository foodRepository, IngredientRepository ingredientRepository, RecipeRepository recipeRepository) {
        this.foodRepository = foodRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }


    public FoodDto createFood(FoodDto food) {
        Food newFood = new Food();
        newFood.setFoodId(food.getFoodId());
        newFood.setFoodName(food.getFoodName());
        newFood.setCarbohydrates(food.getCarbohydrates());
        newFood.setProteins(food.getProteins());
        newFood.setFat(food.getFat());
        newFood.setCalories(food.getCalories());
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

    public void restoreDeletedFoods(Food deletedFood){
        this.restoredFoods = new ArrayList<>();
        for (FoodDto food : deletedFoods){
            FoodDto restoredFood = createFood(food);
            restoredFoods.add(restoredFood);
        }
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

    public List<FoodDto> searchFoodsByName(String name) {
        List<Food> foodList = foodRepository.findByFoodName(name);
        List<FoodDto> foodDtos = new ArrayList<>();
        for (Food food : foodList){
            foodDtos.add(foodToFoodDto(food));
        }
        return foodDtos;
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
        Ingredient ingredient = ingredientRepository.findById(ingredientId).get();
        if (ingredient == null) {
            return Collections.emptyList();
        }
        return ingredient.getIngredientsRecipesList()
                .stream()
                .map(ingredientsRecipes ->
                        foodToFoodDto(foodRepository.getByFoodId(ingredientsRecipes.getRecipe().getFoodId())))
                .collect(Collectors.toList());
    }


    /* One recipe is related to only one food */
    public FoodDto getFoodByRecipeId(int recipeId){
        Recipe recipe = recipeRepository.getReferenceById(recipeId);
        return foodToFoodDto(foodRepository.getByFoodId(recipe.getFoodId()));
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