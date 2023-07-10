package com.example.demo.repository;

import com.example.demo.entity.Food;
import com.example.demo.entity.IngredientsRecipes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FoodRepositoryTest {
    @Autowired
    private FoodRepository foodRepository;
    Food food;
    List<IngredientsRecipes> ingredientsRecipesList;

    @BeforeEach
    void setUp(){
        ingredientsRecipesList = new ArrayList<>();
        food = new Food(0, "Fish", "Grilled fish", "Ocean fish",
                "grams", 220, 2.1, 45, 8, ingredientsRecipesList);
        foodRepository.save(food);
    }

    @AfterEach
    void tearDown(){
        food = null;
        foodRepository.deleteAll();
    }

    @Test
    void findByFoodName_Found(){
        List<Food> foodListByName = foodRepository.findByFoodName("Fish");
        assertThat(foodListByName.get(0).getFoodName()).isEqualTo(food.getFoodName());
    }

    @Test
    void findByFoodName_NotFound() {
        List<Food> foodListByName = foodRepository.findByFoodName("Apple");
        // Test case fails when list is not empty, while food of mentioned name doesn't exist in list
        assertThat(foodListByName.isEmpty()).isTrue(); // so it has to be True
    }

    @Test
    void findByCaloriesGreaterThanEqual_Found() {
        List<Food> foodsByCalories = foodRepository.findByCaloriesGreaterThanEqual(200);
        assertThat(foodsByCalories.get(0).getCalories()).isGreaterThanOrEqualTo(food.getCalories());
    }

    @Test
    void findByCaloriesGreaterThanEqual_NotFound() {
        List<Food> foodsByCalories = foodRepository.findByCaloriesGreaterThanEqual(250);
        assertThat(foodsByCalories.isEmpty()).isTrue();
    }

    @Test
    void findByFatLessThanEqual_Found() {
        List<Food> foodsByFat = foodRepository.findByFatLessThanEqual(3);
        assertThat(foodsByFat.get(0).getFat()).isLessThanOrEqualTo(food.getFat());
    }

    @Test
    void findByFatLessThanEqual_NotFound() {
        List<Food> foodsByFat = foodRepository.findByFatLessThanEqual(1);
        assertThat(foodsByFat.isEmpty()).isTrue();
    }

    @Test
    void findByCarbohydratesGreaterThanEqual_Found() {
        List<Food> foodsByCarbohydrates = foodRepository.findByCarbohydratesGreaterThanEqual(40);
        assertThat(foodsByCarbohydrates.get(0).getCarbohydrates()).isGreaterThanOrEqualTo(food.getCarbohydrates());
    }

    @Test
    void findByCarbohydratesGreaterThanEqual_NotFound() {
        List<Food> foodsByCarbohydrates = foodRepository.findByCarbohydratesGreaterThanEqual(50);
        assertThat(foodsByCarbohydrates.isEmpty()).isTrue();
    }

    @Test
    void findByProteinsGreaterThanEqual_Found() {
        List<Food> foodsByProteins = foodRepository.findByProteinsGreaterThanEqual(5);
        assertThat(foodsByProteins.get(0).getProteins()).isGreaterThanOrEqualTo(food.getProteins());
    }

    @Test
    void findByProteinsGreaterThanEqual_NotFound() {
        List<Food> foodsByProteins = foodRepository.findByProteinsGreaterThanEqual(10);
        assertThat(foodsByProteins.isEmpty()).isTrue();
    }


}
