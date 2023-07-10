package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Query;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    String username,  firstName,  lastName,  email;

}