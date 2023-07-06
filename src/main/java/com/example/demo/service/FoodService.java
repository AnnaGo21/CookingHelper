package com.example.demo.service;

import com.example.demo.dto.FoodDto;
import com.example.demo.entity.Food;
import com.example.demo.entity.Ingredient;
import com.example.demo.repository.FoodRepository;
import com.example.demo.repository.IngredientRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final IngredientRepository ingredientRepository;

    public FoodService(FoodRepository foodRepository, IngredientRepository ingredientRepository) {
        this.foodRepository = foodRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public Food createFood(Food food) {
        return foodRepository.save(food);
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
        foodRepository.deleteById(foodId);
    }

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    public List<Food> searchFoodsByName(String name) {
        return foodRepository.findByFoodName(name);
    }

    public List<Food> searchFoodsByProtein(double minProtein) {
        return foodRepository.findByProteinsGreaterThanEqual(minProtein);
    }

    public List<Food> searchFoodsByFat(double maxFat) {
        return foodRepository.findByFatLessThanEqual(maxFat);
    }

    public List<Food> searchFoodsByCalories(double minCalories) {
        return foodRepository.findByCaloriesGreaterThanEqual(minCalories);
    }

    public List<Food> searchFoodsByCarbohydrates(double minCarbohydrates){
        return foodRepository.findByCarbohydratesGreaterThanEqual(minCarbohydrates);
    }

    public List<FoodDto> getFoodByIngredientId(int ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).get();
        return ingredient.getIngredientsRecipesList()
                .stream()
                .map(ingredientsRecipes ->
                        FoodToFoodDto(foodRepository.getByFoodId(ingredientsRecipes.getRecipe().getFoodId())))
                .collect(Collectors.toList());
    }

    private FoodDto FoodToFoodDto(Food food){
        return FoodDto.builder()
                .foodName(food.getFoodName())
                .carbohydrates(food.getCarbohydrates())
                .fat(food.getFat())
                .calories(food.getCalories())
                .proteins(food.getProteins())
                .build();
    }

    public Food getFoodByRecipeId(int recipeId){
        return foodRepository.findByIngredientsRecipesListRecipeRecipeId(recipeId);
    }
}