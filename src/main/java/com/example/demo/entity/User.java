package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "REGISTERED_USERS")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Recipe> recipes;

}


