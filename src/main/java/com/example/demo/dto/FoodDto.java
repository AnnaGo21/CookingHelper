package com.example.demo.dto;

import com.example.demo.entity.Food;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FoodDto {
    private int foodId;

    private String foodName;
    private double calories;

    private double fat;

    private double carbohydrates;

    private double proteins;
}
