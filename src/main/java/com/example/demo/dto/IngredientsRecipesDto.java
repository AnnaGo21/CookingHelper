package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class IngredientsRecipesDto {
    int id;
    double quantity;
    int recipeId;
    int ingredientId;
}