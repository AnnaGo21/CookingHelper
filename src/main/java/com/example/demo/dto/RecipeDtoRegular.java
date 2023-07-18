package com.example.demo.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDtoRegular {

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
