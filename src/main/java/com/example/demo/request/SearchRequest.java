package com.example.demo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {
    @JsonProperty("userId")
    private int userId;

    @JsonProperty("minProteins")
    private double minProtein;

    @JsonProperty("maxProteins")
    private double maxProtein;

    @JsonProperty("minFats")
    private double minFats;

    @JsonProperty("maxFats")
    private double maxFats;

    @JsonProperty("minCarbohydrates")
    private double minCarbohydrates;

    @JsonProperty("maxCarbohydrates")
    private double maxCarbohydrates;

    @JsonProperty("minCalories")
    private double minCalories;

    @JsonProperty("maxCalories")
    private double maxCalories;

    @JsonProperty("type")
    private String type;

    public SearchRequest(int userId, double minProtein, double maxProtein) {
        this.userId = userId;
        this.minProtein = minProtein;
        this.maxProtein = maxProtein;
    }

}
