package com.accenture.repository.entity.pizza;


import com.accenture.shared.Taille;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private HashMap<Taille, Double> tarifTaille;
//    private List<Ingredient> ingredientsList;


    public Pizza(String nom, HashMap<Taille, Double> tarifTaille) {
        this.nom = nom;
        this.tarifTaille = tarifTaille;
    }
}
