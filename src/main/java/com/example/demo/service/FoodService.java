package com.example.demo.service;

import com.example.demo.entity.Food;
import com.example.demo.entity.Recipe;
import com.example.demo.repository.FoodRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {
    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public Food createFood(Food food) {
        return foodRepository.save(food);
    }

    public Food updateFood(int foodId, Food updatedFood) throws ChangeSetPersister.NotFoundException {
        Food existingFood = foodRepository.findById(foodId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        existingFood.setFoodName(updatedFood.getFoodName());
        existingFood.setProtein(updatedFood.getProtein());
        existingFood.setFat(updatedFood.getFat());
        existingFood.setCarbohydrates(updatedFood.getCarbohydrates());
        existingFood.setCalories(updatedFood.getCalories());
        return foodRepository.save(existingFood);
    }

    public void deleteFood(int foodId) {
        foodRepository.deleteById(foodId);
    }

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    public List<Food> searchFoodsByName(String name) {
        return foodRepository.findByName(name);
    }

    public List<Food> searchFoodsByProtein(double minProtein) {
        return foodRepository.findByProteins(minProtein);
    }

    public List<Food> searchFoodsByFat(double maxFat) {
        return foodRepository.findByFats(maxFat);
    }

    public List<Recipe> searchFoodsByCalories(double minCalories) {
        return foodRepository.findByCalories(minCalories);
    }

    public List<Recipe> searchFoodsByCarbohydrates(double minCarbohydrates){
        return foodRepository.findByCarbohydrates(minCarbohydrates);
    }

    public Food getFoodByIngredientId(int id) {
        return foodRepository.getFoodByIngredientId(id);
    }
}
