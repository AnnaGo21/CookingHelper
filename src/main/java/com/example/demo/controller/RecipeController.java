package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.request.*;
import com.example.demo.dto.RecipeDto;
import com.example.demo.dto.RecipeDtoRegular;
import com.example.demo.service.RecipeService;
import exceptions.UnauthorizedAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeController(RecipeService recipeService, RecipeRepository recipeRepository) {

        this.recipeService = recipeService;
        this.recipeRepository = recipeRepository;
    }

    @PutMapping("/create")
    public void createRecipe(@RequestBody RecipeDto recipe){
        recipeService.createRecipe(recipe);
    }

    @GetMapping
    public List<RecipeDtoRegular> getAllRecipes(){
        return recipeService.getAllRecipes();
    }

    @DeleteMapping("/delete/{recipeId}")
    public ResponseEntity<String> deleteRecipe(@PathVariable int recipeId, @RequestBody UserDto user) {
        try {
            recipeService.deleteRecipe(user, recipeId);
            return ResponseEntity.ok("Recipe deleted successfully");
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to delete this recipe. Only Admin or Creator can");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the recipe");
        }
    }

    @GetMapping("byName/{recipeName}")
    public RecipeDtoRegular getRecipeByName(@PathVariable String recipeName){
        return recipeService.searchRecipeByName(recipeName);
    }


    @GetMapping("byUser/{userId}")
    public List<RecipeDtoRegular> getRecipesByUser(@PathVariable int userId){
        return recipeService.getRecipeDtosByUser(userId);
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

    // For total results
    @PostMapping("/carbohydratesRange")
    public List<RecipeDtoRegular> getRecipesByCarbohydratesRangeAndUser(@RequestBody SearchRequest request){
        return recipeService.searchRecipesByCarbohydratesRangeAndUser(request.getUserId(),
                request.getMinCarbohydrates(), request.getMaxCarbohydrates());
    }

    @PostMapping("/proteinsRange")
    public List<RecipeDtoRegular> getRecipesByProteinsRangeAndUser(@RequestBody SearchRequest request){
        return recipeService.searchRecipesByProteinsRangeAndUser(request.getUserId(),
                request.getMinProtein(), request.getMaxProtein());
    }

    @PostMapping("/fatRange")
    public List<RecipeDtoRegular> searchRecipesByFatRangeAndUser(@RequestBody SearchRequest request){
        return recipeService.searchRecipesByFatRangeAndUser(request.getUserId(),
                request.getMinFats(), request.getMaxFats());
    }

    @PostMapping("/caloriesRange")
    public List<RecipeDtoRegular> searchRecipesByCaloriesRangeAndUser(@RequestBody SearchRequest request){
        return recipeService.searchRecipesByCaloriesRangeAndUser(request.getUserId(),
                request.getMinCalories(), request.getMaxCalories());
    }

    // To enter all together
    @PostMapping("/nutrientsRange")
    public List<RecipeDtoRegular> searchRecipesByNutrientRangesAndUser(@RequestBody SearchRequest nutrientRanges) {
        return recipeService.searchRecipesByNutrientRangesAndUser(nutrientRanges.getUserId(), nutrientRanges);
    }

}
