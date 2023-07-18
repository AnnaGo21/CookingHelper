package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodDtoRegular {

    private int foodId;

    private String foodName;

    private String type;

    private double totalProteins;
    private double totalFats;
    private double totalCarbohydrates;
    private double totalCalories;
}
