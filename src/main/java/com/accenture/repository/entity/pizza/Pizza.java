package com.accenture.repository.entity.pizza;


import com.accenture.shared.Taille;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pizza {

    private int id;
    private String nom;
    private HashMap<Taille, Double> tarifTaille;
//    private List<Ingredient> ingredientsList;


    public Pizza(String nom, HashMap<Taille, Double> tarifTaille) {
        this.nom = nom;
        this.tarifTaille = tarifTaille;
    }
}
