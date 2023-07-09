package com.example.demo.repository;

import com.example.demo.dto.FoodDto;
import com.example.demo.entity.Food;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    Recipe findByRecipeId(int recipeId);
    Recipe findByRecipeName(String recipeName);
    Food findByFoodId(int foodId);
    List<Recipe> findByCreatedBy(User user);
    List<Recipe> findByCreatedById(int userId);
    List<Recipe> findByIsPublic(boolean isPublic);
    List<Recipe> findByIngredientsRecipesList_Ingredient_IngredientName(String ingredientName); // Modified path
    List<Recipe> findByIngredientsRecipesList_Ingredient_ProteinsGreaterThanEqual(double minProtein); // Modified path
    List<Recipe> findByIngredientsRecipesList_Ingredient_FatLessThanEqual(double maxFat); // Modified path
    List<Recipe> findByIngredientsRecipesList_Ingredient_CaloriesGreaterThanEqual(double minCalories); // Modified path
    List<Recipe> findByIngredientsRecipesList_Ingredient_CarbohydratesGreaterThanEqual(double minCarbohydrates); // Modified path



    //List<Recipe> findByIngredientsRecipesListRecipe(int id);
}