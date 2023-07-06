package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "INGREDIENTS")
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private int ingredientId; ////////////////////////////

    @Column(name = "ingredient_name")
    private String ingredientName;

    @Column(name = "ingredient_description")
    private String ingredientDescription;

    @Column(name = "ingredient_type")
    private String ingredientType;

    @Column(name = "measurement_unit")
    private String measurementUnit; // ml or gr

    @Column(name = "calories")
    private double calories;

    @Column(name = "fat")
    private double fat;

    @Column(name = "carbohydrates")
    private double carbohydrates;

    @Column(name = "proteins")
    private double proteins;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
    private List<IngredientsRecipes> ingredientsRecipesList;


}


