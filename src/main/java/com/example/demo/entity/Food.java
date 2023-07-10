package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "FOOD")
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private int foodId;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "measurement_unit")
    private String measurementUnit;

    @Column(name = "calories")
    private double calories;

    @Column(name = "fat")
    private double fat;

    @Column(name = "carbohydrates")
    private double carbohydrates;

    @Column(name = "proteins")
    private double proteins;


    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL) // One food -> many ingredients
    private List<IngredientsRecipes> ingredientsRecipesList;


//    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL) // One food (pizza) has many kind of recipes
//    private List<Recipe> recipeList;

}


