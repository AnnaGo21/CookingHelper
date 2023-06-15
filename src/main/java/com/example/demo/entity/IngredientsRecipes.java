package com.example.demo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "iNGREDIENTS_RECIPES")
@NoArgsConstructor
@AllArgsConstructor
public class IngredientsRecipes {
    @Id
    @GeneratedValue
    private int recipeID;
    private int ingredientID;
    private double quantity;
}


