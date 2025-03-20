package com.accenture.repository.entity.pizza;

import com.accenture.shared.Taille;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PizzaCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Pizza pizza;
    private Taille taille;

    public PizzaCommande(Pizza pizza, Taille taille) {
        this.pizza = pizza;
        this.taille = taille;
    }
}
