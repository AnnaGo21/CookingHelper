package com.example.demo.repository;

import com.example.demo.entity.Recipe;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    Recipe findByRecipeId(int recipeId);
    List<Recipe> findByCreatedBy(User user);
    List<Recipe> findByIsPublic(boolean isPublic);
    List<Recipe> findByIngredientsRecipesListIngredientIngredientName(String ingredientName);
    List<Recipe> findByIngredientsRecipesListIngredientProteinsGreaterThanEqual(double minProtein);
    List<Recipe> findByIngredientsRecipesListIngredientFatLessThanEqual(double maxFat);
    List<Recipe> findByIngredientsRecipesListIngredientCaloriesGreaterThanEqual(double minCalories);
    List<Recipe> findByIngredientsRecipesListIngredientCarbohydratesGreaterThanEqual(double minCalories);

    //List<Recipe> findByIngredientsRecipesListRecipe(int id);
}