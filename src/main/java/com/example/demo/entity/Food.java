package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "measurementUnit")
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


    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getProtein() {
        return proteins;
    }

    public void setProtein(double protein) {
        this.proteins = protein;
    }
}


