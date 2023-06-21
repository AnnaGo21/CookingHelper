package com.example.demo.controller;

import com.example.demo.entity.Food;
import com.example.demo.entity.Recipe;
import com.example.demo.service.FoodService;
import com.example.demo.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {

        this.recipeService = recipeService;
    }

    @GetMapping("/id")
    public Recipe getRecipeById(@RequestParam int id){

        return recipeService.getRecipeById(id);
    }

    @GetMapping("/ingredient_id")
    public Recipe getRecipeByIngredientId(@RequestParam int id){
        return recipeService.getRecipeByIngredientId(id);
    }
}
