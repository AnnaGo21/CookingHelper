package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodDto {
    private int foodId;

    private String foodName;

    private double calories;
    private double fat;
    private double carbohydrates;
    private double proteins;
    private String type;
}
