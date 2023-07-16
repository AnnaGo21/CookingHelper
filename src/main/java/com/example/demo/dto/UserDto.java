package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Query;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    String username,  firstName,  lastName,  email;
}