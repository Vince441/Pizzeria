package com.accenture.controller.pizza;


import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.service.dto.ingredient.IngredientRequestDTO;
import com.accenture.service.dto.ingredient.IngredientResponseDTO;
import com.accenture.service.dto.pizza.PizzaResponseDto;
import com.accenture.service.pizza.PizzaService;
import com.accenture.shared.Taille;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestPizzaController {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private PizzaService pizzaService;

    @Test
    void testPostPizzaAvecObject() throws Exception {
        Map<Taille, Double> tarifTaille = getTailleDoubleHashMap();

        List<Ingredient> ListeIngredients = creerListeIngredients();
        Pizza pizza = new Pizza("Reine", tarifTaille, ListeIngredients);

        mockMvc.perform(MockMvcRequestBuilders.post("/pizzas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pizza)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nom").value("Reine"))
                .andExpect(jsonPath("$.tarifTaille.GRANDE").value(17.0))
                .andExpect(jsonPath("$.listeIngredients[0].nom").value("Pepperoni"))
                .andExpect(jsonPath("$.listeIngredients[1].nom").value("Mozzarella"));

    }


    @Test
    void testPostPizzaFail() throws Exception {
        Map<Taille, Double> tarifTaille = getTailleDoubleHashMap();
        Pizza pizza = new Pizza(null, tarifTaille, creerListeIngredients());
        mockMvc.perform(MockMvcRequestBuilders.post("/pizzas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pizza)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("Erreur validation"))
                .andExpect(jsonPath("$.message").value("Le nom est obligatoire"));
    }


    @Test
    void testTrouverTous() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/pizzas"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString("4 fromages")));

    }


    @Test
    void supprimerPizza() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/pizzas/id/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletePizzapasOk() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/pizzas/id/20"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value("Erreur base"))
                .andExpect(jsonPath("$.message").value("Pizza non trouvé"));
    }



    @Test
    void testTrouverParId() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.get("/pizzas/id/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("4 fromages"));
    }

    @Test
    void testTrouverParNom() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/pizzas/nom/Saumon Buratta"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Saumon Buratta"));

    }

    private static List<Ingredient> creerListeIngredients() {
        return List.of(new Ingredient("Pepperoni", 40), new Ingredient("Mozzarella", 20));
    }

    private static List<IngredientResponseDTO> creerListeIngredientReponseDTOs() {
        return List.of(new IngredientResponseDTO(1, "Pepperoni", 40), new IngredientResponseDTO(2, "Mozzarella", 20));
    }

    private static Map<Taille, Double> getTailleDoubleHashMap() {
        Map<Taille, Double> tarifTaille = new HashMap<>();
        tarifTaille.put(Taille.GRANDE, 17.00);
        return tarifTaille;
    }

}
