package com.example.demo.controller;

import com.example.demo.config.JwtUtils;
import com.example.demo.dto.UserDto;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.request.*;
import com.example.demo.dto.RecipeDto;
import com.example.demo.dto.RecipeDtoRegular;
import com.example.demo.service.RecipeService;
import com.example.demo.service.UserService;
import com.example.demo.exceptions.UnauthorizedAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeRepository recipeRepository;
    private final UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    public RecipeController(RecipeService recipeService, RecipeRepository recipeRepository, UserService userService) {

        this.recipeService = recipeService;
        this.recipeRepository = recipeRepository;
        this.userService = userService;
    }

    @GetMapping
    public List<RecipeDtoRegular> getAllRecipes(){
        return recipeService.getAllRecipes();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody RecipeDto recipe) {
        try {
            RecipeDto createdRecipe = recipeService.createRecipe(recipe);
            return ResponseEntity.ok(createdRecipe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @DeleteMapping("/delete/{recipeId}")
    public ResponseEntity<String> deleteRecipe(@PathVariable int recipeId /*, @RequestBody UserDto user*/) {
        try {
            recipeService.deleteRecipe(recipeId);
            return ResponseEntity.ok("Recipe deleted successfully");
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to delete this recipe. Only Admin or Creator can");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the recipe");
        }
    }


    @GetMapping("/by-name/{recipeName}")
    public RecipeDtoRegular getRecipeByName(@PathVariable String recipeName){
        return recipeService.searchRecipeByName(recipeName);
    }


    @GetMapping("/by-user/{userId}")
    public List<RecipeDtoRegular> getRecipesByUser(@PathVariable int userId){
        return recipeService.getRecipeDtosByUser(userId);
    }

    @GetMapping("/public")
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
    @PostMapping("/carbohydrates-range")
    public List<RecipeDtoRegular> getRecipesByCarbohydratesRangeAndUser(@RequestBody SearchRequest request){
        return recipeService.searchRecipesByCarbohydratesRangeAndUser(request.getUserId(),
                request.getMinCarbohydrates(), request.getMaxCarbohydrates());
    }

    @PostMapping("/proteins-range")
    public List<RecipeDtoRegular> getRecipesByProteinsRangeAndUser(@RequestBody SearchRequest request){
        return recipeService.searchRecipesByProteinsRangeAndUser(request.getUserId(),
                request.getMinProtein(), request.getMaxProtein());
    }

    @PostMapping("/fat-range")
    public List<RecipeDtoRegular> searchRecipesByFatRangeAndUser(@RequestBody SearchRequest request){
        return recipeService.searchRecipesByFatRangeAndUser(request.getUserId(),
                request.getMinFats(), request.getMaxFats());
    }

    @PostMapping("/calories-range")
    public List<RecipeDtoRegular> searchRecipesByCaloriesRangeAndUser(@RequestBody SearchRequest request){
        return recipeService.searchRecipesByCaloriesRangeAndUser(request.getUserId(),
                request.getMinCalories(), request.getMaxCalories());
    }

    // To enter all together
    @PostMapping("/nutrients-range")
    public List<RecipeDtoRegular> searchRecipesByNutrientRangesAndUser(@RequestBody SearchRequest nutrientRanges) {
        return recipeService.searchRecipesByNutrientRangesAndUser(nutrientRanges.getUserId(), nutrientRanges);
    }

}
