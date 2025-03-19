package com.accenture.repository.entity.ingredient;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private Integer stock;

    public Ingredient(String nom, Integer stock) {
        this.nom = nom;
        this.stock = stock;
    }

    public Ingredient(String nom) {
        this.nom = nom;
    }
}
