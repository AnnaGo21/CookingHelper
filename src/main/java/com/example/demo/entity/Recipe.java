package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "recipe_id")
    private int recipeId;

    @Column(name = "food_id")
    private int foodId;

    @Column(name = "recipe_name")
    private String recipeName;

    @Column(name = "cooking_time")
    private int cookingTime;

    @Column(name = "difficulty")
    private String difficulty;

    @Column(name = "cuisine")
    private String cuisine;

    @Column(name = "is_public")
    private boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<IngredientsRecipes> ingredientsRecipesList;

    @Transient
    private double totalProteins;

    @Transient
    private double totalFats;

    @Transient
    private double totalCarbohydrates;

    @Transient
    private double totalCalories;

    @ElementCollection
    @CollectionTable(name = "RECIPE_STEPS", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "step")
    private List<String> steps;

}


