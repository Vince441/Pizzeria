package com.accenture.repository.entity.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    private int id;
    private String nom;
    private Integer stock;

    public Ingredient(String nom, Integer stock) {
        this.nom = nom;
        this.stock = stock;
    }
}
