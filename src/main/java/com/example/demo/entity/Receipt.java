package com.example.demo.entity;

import com.example.demo.entity.Food;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
@Entity
@Table(name = "RECEIPT")
public class Receipt {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String food1;
    private String food2;
    private String food3;
    private String food4;

    public void setId() {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}


