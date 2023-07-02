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
    private int recipeId;

    private String recipeName;
    private int cookingTime;
    private String difficulty;
    private String cuisine;
    private boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<IngredientsRecipes> ingredientsRecipesList;

    @ManyToMany
    @JoinTable(
            name = "FOOD_RECIPES",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    private List<Food> foodList;

    @ElementCollection
    @CollectionTable(name = "RECIPE_STEPS", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "step")
    private List<String> steps;

    /*
    * JPA treats it as a separate table in the database.
    * The steps table will have a foreign key to the Recipe table to establish the relationship.
    * Each element in the steps collection will be stored as a separate row in the steps table,
    with a reference to the corresponding Recipe entity.
     */

}


