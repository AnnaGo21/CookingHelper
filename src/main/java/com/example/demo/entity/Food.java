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

    @Transient
    private double totalProteins;

    @Transient
    private double totalFats;

    @Transient
    private double totalCarbohydrates;

    @Transient
    private double totalCalories;


    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<IngredientsRecipes> ingredientsRecipesList;

    public Food(int foodId, String foodName, String description, String type, String measurementUnit, double calories,
                double fat, double carbohydrates, double proteins, List<IngredientsRecipes> ingredientsRecipes) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.description = description;
        this.type = type;
        this.measurementUnit = measurementUnit;
        this.calories = calories;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
        this.ingredientsRecipesList = ingredientsRecipesList;
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodId=" + foodId +
                ", foodName='" + foodName + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", measurementUnit='" + measurementUnit + '\'' +
                ", calories=" + calories +
                ", fat=" + fat +
                ", carbohydrates=" + carbohydrates +
                ", proteins=" + proteins +
                '}';
    }

}


