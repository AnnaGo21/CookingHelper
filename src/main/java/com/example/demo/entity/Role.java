package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "ROLES")
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

}