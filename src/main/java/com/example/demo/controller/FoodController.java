package com.example.demo.controller;

import com.example.demo.dto.FoodDto;
import com.example.demo.dto.FoodDtoRegular;
import com.example.demo.request.SearchRequest;
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

    @GetMapping("/ingredient-id/{ingredientId}")
    public List<FoodDto> getFoodByIngredientId(@PathVariable int ingredientId){
        return foodService.getFoodByIngredientId(ingredientId);
    }

    @GetMapping("/recipe-id/{recipeId}")
    public FoodDto getFoodByRecipeId(@PathVariable int recipeId){
        return foodService.getFoodByRecipeId(recipeId);
    }

    @GetMapping("min-calories/{minCalories}")
    public List<FoodDto> getFoodByCalories(@PathVariable double minCalories){
        return foodService.searchFoodsByCalories(minCalories);
    }

    @GetMapping("min-carbos/{minCarbohydrates}")
    public List<FoodDto> getFoodByCarbohydrates(@PathVariable double minCarbohydrates){
        return foodService.searchFoodsByCarbohydrates(minCarbohydrates);
    }

    @GetMapping("max-fat/{maxFat}")
    public List<FoodDto> getFoodByFat(@PathVariable double maxFat){
        return foodService.searchFoodsByFat(maxFat);
    }

    @GetMapping("min-protein/{minProtein}")
    public List<FoodDto> getFoodByProtein(@PathVariable double minProtein){
        return foodService.searchFoodsByProtein(minProtein);
    }

    @GetMapping("name/{foodName}")
    public FoodDto getFoodByName(@PathVariable String foodName){
        return foodService.searchFoodByName(foodName);
    }

    @PostMapping("/protein-range")
    public List<FoodDtoRegular> getFoodByProteinsRangeAndType(@RequestBody SearchRequest request){
        return foodService.searchFoodByProteinsRangeAndType(request.getType(),
                request.getMinProtein(), request.getMaxProtein());
    }

    @PostMapping("/fat-range")
    public List<FoodDtoRegular> getFoodByFatRangeAndType(@RequestBody SearchRequest request){
        return foodService.searchFoodByFatRangeAndType(request.getType(),
                request.getMinFats(), request.getMaxFats());
    }

    @PostMapping("/carbo-range")
    public List<FoodDtoRegular> getFoodByCarbohydratesRangeAndType(@RequestBody SearchRequest request){
        return foodService.searchFoodByCarbohydratesRangeAndType(request.getType(),
                request.getMinCarbohydrates(), request.getMaxCarbohydrates());
    }

    @PostMapping("/calories-range")
    public List<FoodDtoRegular> getFoodByCaloriesRangeAndType(@RequestBody SearchRequest request){
        return foodService.searchFoodByCaloriesRangeAndType(request.getType(),
                request.getMinCalories(), request.getMaxCalories());
    }
}
