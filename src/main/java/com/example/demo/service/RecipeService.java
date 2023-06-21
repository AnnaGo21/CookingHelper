package com.example.demo.service;

import com.example.demo.entity.Recipe;
import com.example.demo.entity.User;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.UserRepository;
import exceptions.UnauthorizedAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe createRecipe(User user, Recipe recipe) {
        recipe.setCreatedBy(user);
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(User user, int recipeID) throws UnauthorizedAccessException {
        Recipe recipe = recipeRepository.findById(recipeID);
        if (recipe.getCreatedBy().equals(user)) {
            recipeRepository.delete(recipe);
        } else {
            throw new UnauthorizedAccessException("You are not authorized to delete this recipe. Only Admin or Creator can.");
        }
    }

    public Recipe getRecipeById(int id){
        return recipeRepository.findById(id);
    }

    public List<Recipe> getRecipesByUser(User user) {
        return recipeRepository.findByUser(user);
    }

    public List<Recipe> getAllPublicRecipes() {
        return recipeRepository.findByPublicity();
    }

    public List<Recipe> searchRecipesByIngredients(String ingredientName) {
        return recipeRepository.findByIngredients(ingredientName);
    }

    public List<Recipe> searchRecipesByProtein(double minProtein) {
        return recipeRepository.findByProtein(minProtein);
    }

    public List<Recipe> searchRecipesByFat(double maxFat) {
        return recipeRepository.findByFats(maxFat);
    }

    public List<Recipe> searchRecipesByCalories(double minCalories) {
        return recipeRepository.findByCalories(minCalories);
    }

    public List<Recipe> searchRecipesByCarbohydrates(double minCarbohydrates){
        return recipeRepository.findByCarbohydrates(minCarbohydrates);
    }

    public Recipe getRecipeByIngredientId(int id) {
        return recipeRepository.getRecipeByIngredientId(id);
    }
}
