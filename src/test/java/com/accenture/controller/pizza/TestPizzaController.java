package com.accenture.controller.pizza;


import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.service.dto.ingredient.IngredientResponseDTO;
import com.accenture.service.dto.pizza.PizzaResponseDto;
import com.accenture.service.pizza.PizzaService;
import com.accenture.shared.Taille;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestPizzaController {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;




    @Test
    void testPostPizzaAvecObject() throws Exception {
        Map<Taille, Double> tarifTaille = getTailleDoubleHashMap();
        Pizza pizza = new Pizza("4 fromages", tarifTaille, creerListeIngredients());

        mockMvc.perform(MockMvcRequestBuilders.post("/pizza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pizza)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nom").value("4 fromages"))
                .andExpect(jsonPath("$.tarifTaille.GRANDE").value(17.0));
    }


    @Test
    void testPostPizzaFail() throws Exception {
        Map<Taille, Double> tarifTaille = getTailleDoubleHashMap();
        Pizza pizza = new Pizza(null, tarifTaille, creerListeIngredients());
        mockMvc.perform(MockMvcRequestBuilders.post("/pizza")
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
    void testTrouverParId() throws Exception {
        PizzaResponseDto pizzaResponseDto = getPizzaResponseDto1();

        mockMvc.perform(MockMvcRequestBuilders.get("/pizza/id/10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Margarita"));


    }

    @Test
    void testTrouverParNom() throws Exception {
        PizzaResponseDto pizzaResponseDto = getPizzaResponseDto1();

        mockMvc.perform(MockMvcRequestBuilders.get("/pizza/nom/Margarita"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Margarita"));

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

    private static Pizza getPizza() {
        Map<Taille, Double> tarifTaille = getTailleDoubleHashMap();
        return new Pizza("Margarita", tarifTaille, creerListeIngredients());
    }

    private static Pizza getPizza2() {
        Map<Taille, Double> tarifTaille = getTailleDoubleHashMap();
        return new Pizza("Reine", tarifTaille, creerListeIngredients());
    }

    private static PizzaResponseDto getPizzaResponseDto1() {
        Map<Taille, Double> tarifTaille = getTailleDoubleHashMap();
        return new PizzaResponseDto(1, "Margarita", tarifTaille, creerListeIngredientReponseDTOs());

    }

    private static PizzaResponseDto getPizzaResponseDto2() {
        Map<Taille, Double> tarifTaille = getTailleDoubleHashMap();
        return new PizzaResponseDto(2, "Reine", tarifTaille, creerListeIngredientReponseDTOs());

    }


}
