package com.example.demo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "FOOD")
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    @Id
    @GeneratedValue
    private int foodID;
    private String foodName;
    private int recipeID;

}


