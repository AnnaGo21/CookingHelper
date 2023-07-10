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

//    @ManyToOne
//    @JoinColumn(name = "food_id")
//    private Food food;


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
    //@JsonIgnore
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


    //    @ManyToMany
//    @JoinTable(
//            name = "FOOD_RECIPES",
//            joinColumns = @JoinColumn(name = "recipe_id"),
//            inverseJoinColumns = @JoinColumn(name = "food_id")
//    )
//    private List<Food> foodList;

    @ElementCollection
    @CollectionTable(name = "RECIPE_STEPS", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "step")
    private List<String> steps;

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "food_id")
//    private Food food;

    /*
    * JPA treats it as a separate table in the database.
    * The steps table will have a foreign key to the Recipe table to establish the relationship.
    * Each element in the steps collection will be stored as a separate row in the steps table,
    with a reference to the corresponding Recipe entity.
     */

}


