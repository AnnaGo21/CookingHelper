package com.example.demo.service;

import com.example.demo.dto.FoodDto;
import com.example.demo.entity.Food;
import com.example.demo.entity.Ingredient;
import com.example.demo.entity.IngredientsRecipes;
import com.example.demo.entity.Recipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.repository.FoodRepository;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.RecipeRepository;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class FoodServiceTest {

    private FoodRepository foodRepository;
    private IngredientRepository ingredientRepository;
    private RecipeRepository recipeRepository;
    private FoodService foodService;
    FoodDto foodDto;
    Food food;

    @BeforeEach
    void setUp() {
        foodDto = new FoodDto();
        foodDto.setFoodId(1);
        foodDto.setFoodName("Apple");
        foodDto.setCarbohydrates(10.0);
        foodDto.setProteins(0.5);
        foodDto.setFat(0.3);
        foodDto.setCalories(52.0);

        food = new Food();
        food.setFoodId(1);
        food.setFoodName("Apple");
        food.setCarbohydrates(10.0);
        food.setProteins(0.5);
        food.setFat(0.3);
        food.setCalories(52.0);

        foodRepository = mock(FoodRepository.class);
        ingredientRepository = mock(IngredientRepository.class);
        recipeRepository = mock(RecipeRepository.class);
        foodService = new FoodService(foodRepository, ingredientRepository, recipeRepository);
    }

    @AfterEach
    void tearDown() {
        foodRepository = null;
        ingredientRepository = null;
        recipeRepository = null;
        foodService = null;

        food = null;
        foodDto = null;
    }

    @Test
    void createFood() {
        FoodDto foodDto = this.foodDto;

        Food savedFood = this.food;

        when(foodRepository.save(any(Food.class))).thenReturn(savedFood);

        FoodDto createdFood = foodService.createFood(foodDto);

        assertNotNull(createdFood);
        assertEquals(foodDto.getFoodId(), createdFood.getFoodId());
        assertEquals(foodDto.getFoodName(), createdFood.getFoodName());
        assertEquals(foodDto.getCarbohydrates(), createdFood.getCarbohydrates());
        assertEquals(foodDto.getProteins(), createdFood.getProteins());
        assertEquals(foodDto.getFat(), createdFood.getFat());
        assertEquals(foodDto.getCalories(), createdFood.getCalories());

        verify(foodRepository, times(1)).save(any(Food.class));
    }

    @Test
    void updateFood() {
        int foodId = 1;
        Food existingFood = food;

        Food updatedFood = new Food();
        updatedFood.setFoodName("Updated Apple");
        updatedFood.setCarbohydrates(20.0);
        updatedFood.setProteins(1.0);
        updatedFood.setFat(0.5);
        updatedFood.setCalories(100.0);

        when(foodRepository.findById(foodId)).thenReturn(Optional.of(existingFood));

        Food result = null;
        try {
            result = foodService.updateFood(foodId, updatedFood);
        } catch (ChangeSetPersister.NotFoundException e) {
            fail("NotFoundException should not be thrown.");
        }

        assertNotNull(result);
        assertEquals(updatedFood.getFoodName(), result.getFoodName());
        assertEquals(updatedFood.getCarbohydrates(), result.getCarbohydrates());
        assertEquals(updatedFood.getProteins(), result.getProteins());
        assertEquals(updatedFood.getFat(), result.getFat());
        assertEquals(updatedFood.getCalories(), result.getCalories());

        verify(foodRepository, times(1)).findById(foodId);
    }


    @Test
    void deleteFood() {
        int foodId = 1;
        Food foodToDelete = this.food;

        when(recipeRepository.findByFoodId(foodId)).thenReturn(foodToDelete);

        foodService.deleteFood(foodId);

        verify(recipeRepository, times(1)).deleteById(foodId);
        verify(foodRepository, times(1)).deleteById(foodId);
    }

    @Test
    void getAllFoods() {
        List<Food> foodList = new ArrayList<>();
        Food food1 = new Food();
        food1.setFoodId(1);
        food1.setFoodName("Food 1");
        food1.setCarbohydrates(10.0);
        food1.setProteins(0.5);
        food1.setFat(0.3);
        food1.setCalories(52.0);
        foodList.add(food1);

        Food food2 = new Food();
        food2.setFoodId(2);
        food2.setFoodName("Food 2");
        food2.setCarbohydrates(20.0);
        food2.setProteins(1.0);
        food2.setFat(0.5);
        food2.setCalories(100.0);
        foodList.add(food2);

        when(foodRepository.findAll()).thenReturn(foodList);

        List<FoodDto> result = foodService.getAllFoods();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(foodDto -> foodDto.getFoodId() == food1.getFoodId()));
        assertTrue(result.stream().anyMatch(foodDto -> foodDto.getFoodId() == food2.getFoodId()));

        verify(foodRepository, times(1)).findAll();
    }

    @Test
    void searchFoodsByName() {
        String name = "Apple";
        List<Food> foodList = new ArrayList<>();
        Food food1 = this.food;
        foodList.add(food1);

        when(foodRepository.findByFoodName(name)).thenReturn(foodList);

        List<FoodDto> result = foodService.searchFoodsByName(name);

        assertEquals(1, result.size());
        assertEquals(food1.getFoodId(), result.get(0).getFoodId());
        assertEquals(food1.getFoodName(), result.get(0).getFoodName());
        assertEquals(food1.getCarbohydrates(), result.get(0).getCarbohydrates());
        assertEquals(food1.getProteins(), result.get(0).getProteins());
        assertEquals(food1.getFat(), result.get(0).getFat());
        assertEquals(food1.getCalories(), result.get(0).getCalories());

        verify(foodRepository, times(1)).findByFoodName(name);
    }


    @Test
    void searchFoodsByProtein() {
        double minProtein = 0.5;
        List<Food> foodList = new ArrayList<>();
        Food food1 = this.food;
        foodList.add(food1);

        when(foodRepository.findByProteinsGreaterThanEqual(minProtein)).thenReturn(foodList);

        List<FoodDto> result = foodService.searchFoodsByProtein(minProtein);

        assertEquals(1, result.size());
        assertEquals(food1.getFoodId(), result.get(0).getFoodId());
        assertEquals(food1.getFoodName(), result.get(0).getFoodName());
        assertEquals(food1.getCarbohydrates(), result.get(0).getCarbohydrates());
        assertEquals(food1.getProteins(), result.get(0).getProteins());
        assertEquals(food1.getFat(), result.get(0).getFat());
        assertEquals(food1.getCalories(), result.get(0).getCalories());

        verify(foodRepository, times(1)).findByProteinsGreaterThanEqual(minProtein);
    }

    @Test
    void searchFoodsByFat() {
        double maxFat = 0.5;
        List<Food> foodList = new ArrayList<>();
        Food food1 = this.food;
        foodList.add(food1);

        when(foodRepository.findByFatLessThanEqual(maxFat)).thenReturn(foodList);

        List<FoodDto> result = foodService.searchFoodsByFat(maxFat);

        assertEquals(1, result.size());
        assertEquals(food1.getFoodId(), result.get(0).getFoodId());
        assertEquals(food1.getFoodName(), result.get(0).getFoodName());
        assertEquals(food1.getCarbohydrates(), result.get(0).getCarbohydrates());
        assertEquals(food1.getProteins(), result.get(0).getProteins());
        assertEquals(food1.getFat(), result.get(0).getFat());
        assertEquals(food1.getCalories(), result.get(0).getCalories());

        verify(foodRepository, times(1)).findByFatLessThanEqual(maxFat);
    }

    @Test
    void searchFoodsByCarbohydrates() {
        double minCarbohydrates = 5.0;
        List<Food> foodList = new ArrayList<>();
        Food food1 = this.food;
        foodList.add(food1);

        when(foodRepository.findByCarbohydratesGreaterThanEqual(minCarbohydrates)).thenReturn(foodList);

        List<FoodDto> result = foodService.searchFoodsByCarbohydrates(minCarbohydrates);

        assertEquals(1, result.size());
        assertEquals(food1.getFoodId(), result.get(0).getFoodId());
        assertEquals(food1.getFoodName(), result.get(0).getFoodName());
        assertEquals(food1.getCarbohydrates(), result.get(0).getCarbohydrates());
        assertEquals(food1.getProteins(), result.get(0).getProteins());
        assertEquals(food1.getFat(), result.get(0).getFat());
        assertEquals(food1.getCalories(), result.get(0).getCalories());

        verify(foodRepository, times(1)).findByCarbohydratesGreaterThanEqual(minCarbohydrates);
    }

    @Test
    void searchFoodsByCalories() {
        double minCalories = 50.0;
        List<Food> foodList = new ArrayList<>();
        Food food1 = this.food;
        foodList.add(food1);

        when(foodRepository.findByCaloriesGreaterThanEqual(minCalories)).thenReturn(foodList);

        List<FoodDto> result = foodService.searchFoodsByCalories(minCalories);

        assertEquals(1, result.size());
        assertEquals(food1.getFoodId(), result.get(0).getFoodId());
        assertEquals(food1.getFoodName(), result.get(0).getFoodName());
        assertEquals(food1.getCarbohydrates(), result.get(0).getCarbohydrates());
        assertEquals(food1.getProteins(), result.get(0).getProteins());
        assertEquals(food1.getFat(), result.get(0).getFat());
        assertEquals(food1.getCalories(), result.get(0).getCalories());

        verify(foodRepository, times(1)).findByCaloriesGreaterThanEqual(minCalories);
    }

    @Test
    void getFoodByRecipeId() {
        int recipeId = 1;
        Recipe recipe = new Recipe();
        recipe.setFoodId(1);

        Food food = this.food;

        when(recipeRepository.getReferenceById(recipeId)).thenReturn(recipe);
        when(foodRepository.getByFoodId(recipe.getFoodId())).thenReturn(food);

        FoodDto result = foodService.getFoodByRecipeId(recipeId);

        assertNotNull(result);
        assertEquals(food.getFoodId(), result.getFoodId());
        assertEquals(food.getFoodName(), result.getFoodName());
        assertEquals(food.getCarbohydrates(), result.getCarbohydrates());
        assertEquals(food.getProteins(), result.getProteins());
        assertEquals(food.getFat(), result.getFat());
        assertEquals(food.getCalories(), result.getCalories());

        verify(recipeRepository, times(1)).getReferenceById(recipeId);
        verify(foodRepository, times(1)).getByFoodId(recipe.getFoodId());
    }

    @Test
    void getFoodByIngredientId() {

    }
}