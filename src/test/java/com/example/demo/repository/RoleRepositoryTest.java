package com.example.demo.repository;

import com.example.demo.entity.Food;
import com.example.demo.entity.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;
    Role role;

    @BeforeEach
    void setUp(){
        role = new Role(1, "USER");
        roleRepository.save(role);
    }

    @AfterEach
    void tearDown(){
        role = null;
        roleRepository.deleteAll();
    }

    @Test
    void findByRoleName_Found(){
        Role roleByName = roleRepository.findRoleByName("USER");
        assertThat(roleByName.getName()).isEqualTo(role.getName());
    }

    @Test
    void findByRoleName_NotFound(){
        Role roleByName = roleRepository.findRoleByName("ADMIN");
        assertThat(roleByName).isEqualTo(null);
    }
}
