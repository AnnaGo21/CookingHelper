package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "iNGREDIENTS_RECIPES")
public class IngredientsRecipes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recipeID;

    @ManyToOne // Many ingredients -> one recipe
    private Ingredient ingredient;

    private double quantity;
}


