package com.example.demo.repository;

import com.example.demo.dto.FoodDto;
import com.example.demo.entity.Food;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    Recipe findByRecipeId(int recipeId);
    Recipe findByRecipeNameContainingIgnoreCase(String recipeName); // Because each recipe has th unique name
    Food findByFoodId(int foodId);
    List<Recipe> findByCreatedBy(User user);
    List<Recipe> findByCreatedById(int userId);
    List<Recipe> findByIsPublic(boolean isPublic);
    List<Recipe> findByIngredientsRecipesList_Ingredient_IngredientName(String ingredientName);
    List<Recipe> findByIngredientsRecipesList_Ingredient_ProteinsGreaterThanEqual(double minProtein);
    List<Recipe> findByIngredientsRecipesList_Ingredient_FatLessThanEqual(double maxFat);
    List<Recipe> findByIngredientsRecipesList_Ingredient_CaloriesGreaterThanEqual(double minCalories);
    List<Recipe> findByIngredientsRecipesList_Ingredient_CarbohydratesGreaterThanEqual(double minCarbohydrates);

    //List<Recipe> findByTotalCarbohydratesBetween(double minCarbohydrates, double maxCarbohydrates);

//    @Query(
//            nativeQuery = true,
//            value = ""
//    )
//    List<Recipe> findAllByTotalCarbohydratesBetween(double from, double to);
}