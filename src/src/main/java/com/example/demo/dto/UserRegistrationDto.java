package com.example.demo.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserRegistrationDto {
    @NotEmpty(message = "Username must not be empty")
    @Pattern(regexp = "[A-Za-z][A-Za-z0-9]+", message = "Username name must consist of only english characters and optionally numbers")
    private String username;

    @NotEmpty(message = "First name must not be empty")
    @Pattern(regexp = "[A-Za-z]+", message = "First name must consist of only english letters")
    @JsonProperty("firstName")
    private String firstName;

    @NotEmpty(message = "Last name must not be empty")
    @Pattern(regexp = "[A-Za-z]+", message = "Last name must consist of only english letters")
    @JsonProperty("lastName")
    private String lastName;

    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email is not valid")
    private String email;

    @NotEmpty(message = "Password must not be empty")
    @Length(min = 8, message = "Password length must be of minimum 8")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$", message = "Password must contain both letters and numbers")
    private String password;
    @NotEmpty(message = "Confirmation password must not be empty")
    @JsonProperty("passwordConfirmation")
    private String passwordConfirmation;
}