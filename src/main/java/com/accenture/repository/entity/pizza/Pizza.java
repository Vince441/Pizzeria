package com.accenture.repository.entity.pizza;


import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.shared.Taille;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private Map<Taille, Double> tarifTaille;
    private List<Ingredient> listeIngredients;


    public Pizza(String nom, Map<Taille, Double> tarifTaille, List<Ingredient> listeIngredients) {
        this.nom = nom;
        this.tarifTaille = tarifTaille;
        this.listeIngredients = listeIngredients;
    }
}
