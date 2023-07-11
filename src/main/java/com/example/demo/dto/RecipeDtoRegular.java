package com.example.demo.dto;

import com.example.demo.entity.IngredientsRecipes;
import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDtoRegular {

    //private User createdBy;

    @JsonProperty
    private int creatorUserId;

    @JsonProperty
    private int recipeId;

    @JsonProperty
    private int foodId;

    @JsonProperty
    private String recipeName;

    //@JsonProperty("Publicity")
    private boolean isPublic;

    private double totalProteins;
    private double totalFats;
    private double totalCarbohydrates;
    private double totalCalories;
}
