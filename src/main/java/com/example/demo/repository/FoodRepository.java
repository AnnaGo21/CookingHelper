package com.example.demo.repository;

import com.example.demo.dto.FoodDto;
import com.example.demo.entity.Food;
import com.example.demo.entity.Ingredient;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.User;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
    List<Food> findAllByType(String type);

    Food findByFoodName(String foodName);

    Food getByFoodId(int foodId);

    List<Food> findByCaloriesGreaterThanEqual(double minCalories);

    List<Food> findByFatLessThanEqual(double maxFat);

    List<Food> findByCarbohydratesGreaterThanEqual(double minCarbohydrates);

    List<Food> findByProteinsGreaterThanEqual(double minProteins);

}

