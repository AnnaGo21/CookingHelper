package com.example.demo.controller;

import com.example.demo.request.CaloriesRequest;
import com.example.demo.request.CarbohydratesRequest;
import com.example.demo.request.FatRequest;
import com.example.demo.request.ProteinRequest;
import com.example.demo.dto.RecipeDto;
import com.example.demo.dto.RecipeDtoRegular;
import com.example.demo.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {

        this.recipeService = recipeService;
    }

    @PutMapping("/create")
    public void createRecipe(@RequestBody RecipeDto recipe){
        recipeService.createRecipe(recipe);
    }

    @GetMapping
    public List<RecipeDtoRegular> getAllRecipes(){
        return recipeService.getAllRecipes();
    }

    @GetMapping("byUser/{userId}")
    public List<RecipeDtoRegular> getRecipesByUser(@PathVariable int userId){
        return recipeService.getRecipesByUser(userId);
    }

    @GetMapping("publicRecipes")
    public List<RecipeDtoRegular> getAllPublicRecipes(){
        return recipeService.getAllPublicRecipes(true);
    }

    @GetMapping("/ingredient/{ingredient}")
    public List<RecipeDtoRegular> getRecipesByIngredient(@PathVariable String ingredient){
        return recipeService.searchRecipesByIngredients(ingredient);
    }

    @PostMapping("/protein")
    public List<RecipeDtoRegular> getRecipesByProteinAndUser(@RequestBody ProteinRequest proteinRequest){
        return recipeService.searchRecipesByProteinAndUser(proteinRequest.getUserId(), proteinRequest.getMinProtein());
    }

    @PostMapping("/fat")
    public List<RecipeDtoRegular> getRecipesByFatAndUser(@RequestBody FatRequest fatRequest){
        return recipeService.searchRecipesByFatAndUser(fatRequest.getUserId(), fatRequest.getMaxFat());
    }

    @PostMapping("/calories")
    public List<RecipeDtoRegular> getRecipesByCaloriesAndUser(@RequestBody CaloriesRequest caloriesRequest){
        return recipeService.searchRecipesByCaloriesAndUser(caloriesRequest.getUserId(),
                caloriesRequest.getMinCalories());
    }

    @PostMapping("/carbohydrates")
    public List<RecipeDtoRegular> getRecipesByCarbohydratesAndUser(@RequestBody CarbohydratesRequest carbohydratesRequest){
        return recipeService.searchRecipesByCarbohydratesAndUser(carbohydratesRequest.getUserId(),
                carbohydratesRequest.getMinCarbohydrates());
    }

}
