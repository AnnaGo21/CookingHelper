package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "RECIPE")
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recipeID;
    private String recipeName;
    private int cookingTime;
    private String difficulty;
    private String cuisine;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipeID")
    private List<IngredientsRecipes> ingredientsRecipesList;

    @ManyToMany
    private List<Food> foodList;

    @ElementCollection
    private List<String> steps;

    private boolean isPublic;

    @ManyToOne
    private User createdBy;

}


