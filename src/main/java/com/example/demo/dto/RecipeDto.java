package com.example.demo.dto;

import com.example.demo.entity.IngredientsRecipes;
import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RecipeDto {
    private UserDto createdByDto;

    @JsonProperty
    private int creatorUserId;

    @JsonProperty
    private int recipeId;

    @JsonProperty
    private int foodId;

    @JsonProperty
    private String recipeName;

    @JsonProperty
    private boolean isPublic;

    private int cookingTime;
    private String cuisine;

    private List<IngredientsRecipes> ingredientsRecipesList; // There is Food, which has proteins and so on
}
