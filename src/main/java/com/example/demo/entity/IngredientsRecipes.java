package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "iNGREDIENTS_RECIPES")
@NoArgsConstructor
@AllArgsConstructor
public class IngredientsRecipes {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recipeID;

    private double quantity;

    @ManyToOne // Many ingredients -> one recipe
    private Recipe recipe;

    @ManyToOne
    private Ingredient ingredient;

}


