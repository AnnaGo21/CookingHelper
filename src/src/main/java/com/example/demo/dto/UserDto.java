package com.example.demo.dto;

import lombok.Builder;

@Builder
public record UserDto(String username, String firstName, String lastName, String email) {

}