package com.example.demo.repository;

import com.example.demo.entity.Food;
import com.example.demo.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
    List<Food> findByName(String name);

    List<Food> findByProteins(double minProtein);

    List<Food> findByFats(double maxFat);

    List<Recipe> findByCalories(double minCalories);

    List<Recipe> findByCarbohydrates(double minCarbohydrates);

    Food getFoodByIngredientId(int id);
}

