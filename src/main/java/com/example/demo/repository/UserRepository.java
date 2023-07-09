package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserByEmail(String email);

    User getUserById(int id);

    User findUserById(int userId);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}

