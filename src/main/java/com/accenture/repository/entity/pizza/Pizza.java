package com.accenture.repository.entity.pizza;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pizza {

    private int id;
    private String nom;

    public Pizza(String nom) {
        this.nom = nom;
    }
}
