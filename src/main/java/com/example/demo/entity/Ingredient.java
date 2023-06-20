package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "INGREDIENTS")
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ingredientID;
    private String ingredientName;
    private String ingredientDescription;
    private String ingredientType;
    private String measurementUnit; //ml or gr
    private double calories;
    private double fat;
    private double carbohydrates;
    private double protein;

}


