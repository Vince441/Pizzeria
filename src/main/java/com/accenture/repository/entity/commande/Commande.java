package com.accenture.repository.entity.commande;

import com.accenture.repository.entity.client.Client;
import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.repository.entity.pizza.PizzaCommande;
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
    @ManyToOne
    private Client client;
    @OneToMany
    private List<PizzaCommande> listePizzaCommandes;
    private Statut statut;
    private Double prixTotal;

    public Commande(Client client, List<PizzaCommande> listePizzaCommandes, Statut statut, Double prixTotal) {
        this.client = client;
        this.listePizzaCommandes = listePizzaCommandes;
        this.statut = statut;
        this.prixTotal = prixTotal;
    }
}
