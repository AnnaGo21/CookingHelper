package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "INGREDIENTS_RECIPES")
@NoArgsConstructor
@AllArgsConstructor
public class IngredientsRecipes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double quantity;

    @ManyToOne
    @JoinColumn(name = "recipe_id") // foreign key
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id") // foreign key
    private Ingredient ingredient;

    @ManyToOne // Many ingredients -> one food
    private Food food;
}


