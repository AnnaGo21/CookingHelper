package com.example.demo.dto;

import lombok.Data;

@Data
public class IngredientsRecipesDto {
    int id;
    double quantity;
    int recipeId;
    int ingredientId;
}