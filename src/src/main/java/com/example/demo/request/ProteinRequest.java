package com.example.demo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProteinRequest {
    @JsonProperty("userId")
    private int userId;

    @JsonProperty("minProtein")
    private double minProtein;
}