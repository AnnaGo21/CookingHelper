package com.example.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "REGISTERED_USERS")
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String username;
    private String first_name;
    private String last_name;
    private String email;
    private String mobile;


//    public void setId(int id) {
//        this.id = id;
//    }
// Uncomment if necessary
//    public int getId() {
//        return id;
//    }
}


