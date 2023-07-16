package com.example.demo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaloriesRequest {
    @JsonProperty("userId")
    private int userId;

    @JsonProperty("minCalories")
    private double minCalories;
}
