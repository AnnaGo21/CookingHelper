package com.example.demo.controller;

import com.example.demo.dto.FoodDto;
import com.example.demo.entity.Food;
import com.example.demo.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public List<Food> getAllFood(){
        return new ArrayList<>();
        //return foodService.getAllFoods();
    }
    

    @GetMapping("/ingredient_id/{ingredientId}")
    public List<FoodDto> getFoodByIngredientId(@PathVariable int ingredientId){
        return foodService.getFoodByIngredientId(ingredientId);
    }

    @GetMapping("/recipe/{recipeId}")
    public Food getFoodByRecipeId(@PathVariable int recipeId){
        return foodService.getFoodByRecipeId(recipeId);
    }
}
