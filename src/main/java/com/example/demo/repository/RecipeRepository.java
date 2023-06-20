package com.example.demo.repository;

import com.example.demo.entity.Recipe;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findByUser(User user);

    List<Recipe> findByPublicity(); // Public receipts and private receipts

    List<Recipe> findByIngredients(String ingredientName);

    List<Recipe> findByProtein(double minProtein);

    List<Recipe> findByFats(double maxFat);

    List<Recipe> findByCalories(double minCalories);

    List<Recipe> findByCarbohydrates(double minCarbohydrates);
}

