package com.example.demo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarbohydratesRequest {
    @JsonProperty("userId")
    private int userId;

    @JsonProperty("minCarbohydrates")
    private double minCarbohydrates;
}
