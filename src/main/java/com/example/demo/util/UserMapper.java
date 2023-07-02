package com.example.demo.util;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User UserRegistrationDtoToUser(UserRegistrationDto userRegistrationDto) {
        User result = new User();
        result.setFirstName(userRegistrationDto.getFirstName().toLowerCase());
        result.setLastName(userRegistrationDto.getLastName().toLowerCase());
        result.setEmail(userRegistrationDto.getEmail());
        String hashedPassword = passwordEncoder.encode(userRegistrationDto.getPassword());
        result.setHashedPassword(hashedPassword);
        return result;
    }

    public static UserDto UserToUserDto(User user) {
        return new UserDto(user.getFirstName(), user.getLastName(), user.getEmail());
    }
}