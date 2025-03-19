package com.accenture.repository.entity.commande;

import com.accenture.repository.entity.client.Client;
import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.shared.Statut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private Client client;
    @OneToMany
    private List<Pizza> listePizzas;
    private Statut statut;
    private Double prixTotal;

    public Commande(Client client, List<Pizza> listePizzas, Statut statut, Double prixTotal) {
        this.client = client;
        this.listePizzas = listePizzas;
        this.statut = statut;
        this.prixTotal = prixTotal;
    }
}
