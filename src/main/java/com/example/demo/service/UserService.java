package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {


    private UserRepository userRepository;
    private final UserMapper userMapper;

    public User saveDetails(User user){
        return userRepository.save(user);
    }

    public User getUserByEmail(String email){
        return userRepository.getUserByEmail(email);
    }

    public User getUserById(int id){
        return userRepository.getUserById(id);
    }

    private void logUserRegistration(UserRegistrationDto userRegistrationDto) {
        log.info("Registered user: {first name: {}, last name: {}, email: {}}",
                userRegistrationDto.getFirstName().toLowerCase(),
                userRegistrationDto.getLastName().toLowerCase(),
                userRegistrationDto.getEmail());
    }

    public UserDto createUser(UserRegistrationDto userRegistrationDto) {
        if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmationPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        User registeredUser = userMapper.UserRegistrationDtoToUser(userRegistrationDto);
        return registeredUser;
    }
}

