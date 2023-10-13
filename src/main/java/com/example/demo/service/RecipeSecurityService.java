package com.example.demo.service;

import com.example.demo.entity.Recipe;
import com.example.demo.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeSecurityService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeSecurityService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public boolean isRecipeOwner(int recipeId, String username) {
        Recipe recipe = recipeRepository.findByRecipeId(recipeId);
        return recipe != null && recipe.getCreatedBy().getUsername().equals(username);
    }
}