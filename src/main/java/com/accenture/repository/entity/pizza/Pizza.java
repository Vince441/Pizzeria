package com.accenture.repository.entity.pizza;


import com.accenture.shared.Taille;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
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


    @ElementCollection
    @CollectionTable(name = "pizza_prix", joinColumns = @JoinColumn(name = "pizza_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "prix")
    private Map<Taille, Double> tarifTaille;
//    private List<Ingredient> ingredientsList;


    public Pizza(String nom, HashMap<Taille, Double> tarifTaille) {
        this.nom = nom;
        this.tarifTaille = tarifTaille;
    }
}
