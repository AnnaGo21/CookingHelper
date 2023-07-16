package com.example.demo.entity;


import com.example.demo.model.ERole;
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

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

}