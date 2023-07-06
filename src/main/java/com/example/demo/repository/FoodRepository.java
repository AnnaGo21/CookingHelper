package com.example.demo.repository;

import com.example.demo.dto.FoodDto;
import com.example.demo.entity.Food;
import com.example.demo.entity.Ingredient;
import com.example.demo.entity.Recipe;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
    List<Food> findByFoodName(String foodName);

    Food getByFoodId(int foodId);
    List<Food> findByType(String type);

    List<Food> findByCaloriesGreaterThanEqual(double minCalories);

    List<Food> findByFatLessThanEqual(double maxFat);

    List<Food> findByCarbohydratesGreaterThanEqual(double minCarbohydrates);

    List<Food> findByProteinsGreaterThanEqual(double minProteins);

    Food findByIngredientsRecipesListRecipeRecipeId(int recipeId);
}

