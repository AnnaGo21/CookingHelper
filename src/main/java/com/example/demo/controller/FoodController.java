package com.example.demo.controller;

import com.example.demo.entity.Food;
import com.example.demo.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/foods")
public class FoodController {
    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping
    public List<Food> getAllFood(){
        return foodService.getAllFoods();
    }

    @GetMapping("/ingredient_id")
    public Food getFoodByIngredientId(@RequestParam int id){
        return foodService.getFoodByIngredientId(id);
    }

    @GetMapping("/recipe_id")
    public Food getFoodByRecipeId(@RequestParam int id){
        return foodService.getFoodByRecipeId(id);
    }
}
