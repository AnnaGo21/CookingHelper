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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipeID") // One recipe -> many ingredients
    private List<IngredientsRecipes> ingredientsRecipesList;

    @ManyToMany
    private List<Food> foodList;

    @ElementCollection
    private List<String> steps;
    /*
    * JPA treats it as a separate table in the database.
    * The steps table will have a foreign key to the Recipe table to establish the relationship.
    * Each element in the steps collection will be stored as a separate row in the steps table,
    with a reference to the corresponding Recipe entity.
     */

    private boolean isPublic;

    @ManyToOne
    private User createdBy;

}


