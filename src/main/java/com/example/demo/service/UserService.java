package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.model.ERole;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {


    private UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;



    public UserDto getUserDtoByUsername(String username) {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return userMapper.UserToUserDto(user);
    }


    public UserDto getUserById(int id){
        return userMapper.UserToUserDto(userRepository.getUserById(id));
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

        Role role = roleRepository.findRoleByName(ERole.USER);
        registeredUser.addRole(role);

        userRepository.save(registeredUser);

        UserDto userDto = userMapper.UserToUserDto(registeredUser);

        return userDto;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserByUsername(String username){
        return userRepository.getUserByUsername(username);
    }
    public User getUserByEmail(String email){
        return userRepository.getUserByEmail(email);
    }

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}

