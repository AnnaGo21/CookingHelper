package com.example.demo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchRequest {
    @JsonProperty("userId")
    private int userId;

    @JsonProperty("minProteins")
    private Double minProtein;

    @JsonProperty("maxProteins")
    private Double maxProtein;

    @JsonProperty("minFats")
    private Double minFats;

    @JsonProperty("maxFats")
    private Double maxFats;

    @JsonProperty("minCarbohydrates")
    private Double minCarbohydrates;

    @JsonProperty("maxCarbohydrates")
    private Double maxCarbohydrates;

    @JsonProperty("minCalories")
    private Double minCalories;

    @JsonProperty("maxCalories")
    private Double maxCalories;

    public SearchRequest(int userId, Double minProtein, Double maxProtein) {
        this.userId = userId;
        this.minProtein = minProtein;
        this.maxProtein = maxProtein;
    }

}
