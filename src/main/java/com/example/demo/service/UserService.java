package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {


    private UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

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
        if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getPasswordConfirmation())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        if (userRepository.existsByEmail(userRegistrationDto.getEmail())) {
            throw new IllegalArgumentException("User by mentioned email already exists");
        }
        if (userRepository.existsByUsername(userRegistrationDto.getUsername())){
            throw new IllegalArgumentException("User by mentioned username already exists");
        }

        User registeredUser = userMapper.userRegistrationDtoToUser(userRegistrationDto);

        Role role = roleRepository.findRoleByName("USER");
        registeredUser.addRole(role);

        userRepository.save(registeredUser);

        UserDto userDto = userMapper.UserToUserDto(registeredUser);

        return userDto;
    }
}

