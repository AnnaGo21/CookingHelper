package com.example.demo.repository;

import com.example.demo.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
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

