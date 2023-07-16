package com.example.demo.dto;

import com.example.demo.entity.IngredientsRecipes;
import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecipeDtoRegular {

    //private User createdBy;

    private int creatorUserId;

    private int recipeId;

    private int foodId;

    private String recipeName;

    private boolean isPublic;

    private double totalProteins;
    private double totalFats;
    private double totalCarbohydrates;
    private double totalCalories;
}
