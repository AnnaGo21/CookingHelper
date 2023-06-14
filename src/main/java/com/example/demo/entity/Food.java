package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Entity
@Table(name = "FOOD")
public class Food {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private Double protein;
    private Double fat;
    private Double carbohydrates;
    private int kcal;

    public void setId() {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}


