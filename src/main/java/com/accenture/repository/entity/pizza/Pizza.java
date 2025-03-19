package com.accenture.repository.entity.pizza;


import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.shared.Taille;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Map;
import java.util.List;


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

    @OneToMany(fetch = FetchType.LAZY)
    private List<Ingredient> listeIngredients;


    public Pizza(String nom, Map<Taille, Double> tarifTaille, List<Ingredient> listeIngredients) {
        this.nom = nom;
        this.tarifTaille = tarifTaille;
        this.listeIngredients = listeIngredients;
    }
}
