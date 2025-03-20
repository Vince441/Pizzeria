package com.accenture.controller.commande;

import com.accenture.repository.entity.client.Client;
import com.accenture.repository.entity.commande.Commande;
import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.repository.entity.pizza.PizzaCommande;
import com.accenture.shared.Statut;
import com.accenture.shared.Taille;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommandeControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPostCommandeAvecObjet() throws Exception {
        Commande commande = creerCommande();
        System.out.println("commande = " + commande);
        mockMvc.perform(MockMvcRequestBuilders.post("/commandes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commande)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(Matchers.not(0)));
    }

    private Commande creerCommande() {
        return new Commande(creerClient(), creerListePizzaCommandes(), Statut.EN_PREPARATION, 24.0);
    }

    private List<PizzaCommande> creerListePizzaCommandes() {
        return List.of(creerPizzaCommande(), creerPizzaCommande());
    }

    private PizzaCommande creerPizzaCommande() {
        return new PizzaCommande(creerPizza(), Taille.MOYENNE);
    }

    private Client creerClient() {
        return new Client(1, "DUPONT", "Jean", "jean@mail.fr", 0);
    }

    private Pizza creerPizza() {
        Map<Taille, Double> tarif = new HashMap<>();
        tarif.put(Taille.MOYENNE, 12.0);
        return new Pizza(1, "Margherita", tarif, creerListeIngredients());
    }

    private List<Ingredient> creerListeIngredients() {
        return List.of(creerIngredient(), creerAutreIngredient());
    }

    private Ingredient creerIngredient() {
        return new Ingredient(1, "Pepperoni", 40);
    }

    private Ingredient creerAutreIngredient() {
        return new Ingredient(2, "Mozzarella", 20);
    }
}
