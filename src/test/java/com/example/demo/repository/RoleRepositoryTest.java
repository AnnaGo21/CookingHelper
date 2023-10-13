package com.example.demo.repository;

import com.example.demo.entity.Role;
import com.example.demo.model.ERole;
import com.example.demo.repository.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;
    Role role;

    @BeforeEach
    void setUp(){
        role = new Role(1, ERole.USER);
        roleRepository.save(role);
    }

    @AfterEach
    void tearDown(){
        role = null;
        roleRepository.deleteAll();
    }

    @Test
    void findByRoleName_Found(){
        Role roleByName = roleRepository.findRoleByName(ERole.USER);
        assertThat(roleByName.getName()).isEqualTo(role.getName());
    }

    @Test
    void findByRoleName_NotFound(){
        Role roleByName = roleRepository.findRoleByName(ERole.OTHER);
        assertThat(roleByName).isEqualTo(null);
    }
}
