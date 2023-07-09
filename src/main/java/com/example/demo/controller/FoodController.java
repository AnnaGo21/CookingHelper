package com.example.demo.controller;

import com.example.demo.dto.FoodDto;
import com.example.demo.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/food")
public class FoodController {
    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }


    @GetMapping
    public List<FoodDto> getAllFood(){
        return foodService.getAllFoods();
    }

    @GetMapping("delete/{foodId}")
    public void deleteFoodById(@PathVariable int foodId){
        foodService.deleteFood(foodId);
    }

    @PutMapping("/create")
    public void createFood(@RequestBody FoodDto food){
        foodService.createFood(food);
    }


    @GetMapping("/ingredient_id/{ingredientId}")
    public List<FoodDto> getFoodByIngredientId(@PathVariable int ingredientId){
        return foodService.getFoodByIngredientId(ingredientId);
    }

    @GetMapping("/recipe_id/{recipeId}")
    public FoodDto getFoodByRecipeId(@PathVariable int recipeId){
        return foodService.getFoodByRecipeId(recipeId);
    }

    @GetMapping("min_calories/{minCalories}")
    public List<FoodDto> getFoodByCalories(@PathVariable double minCalories){
        return foodService.searchFoodsByCalories(minCalories);
    }

    @GetMapping("min_carbos/{minCarbohydrates}")
    public List<FoodDto> getFoodByCarbos(@PathVariable double minCarbohydrates){
        return foodService.searchFoodsByCarbohydrates(minCarbohydrates);
    }

    @GetMapping("max_fat/{maxFat}")
    public List<FoodDto> getFoodByFat(@PathVariable double maxFat){
        return foodService.searchFoodsByFat(maxFat);
    }

    @GetMapping("min_protein/{minProtein}")
    public List<FoodDto> getFoodByProtein(@PathVariable double minProtein){
        return foodService.searchFoodsByProtein(minProtein);
    }

    @GetMapping("name/{foodName}")
    public List<FoodDto> getFoodByName(@PathVariable String foodName){
        return foodService.searchFoodsByName(foodName);
    }
}
