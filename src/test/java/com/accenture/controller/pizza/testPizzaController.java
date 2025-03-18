package com.accenture.controller.pizza;

import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.repository.entity.pizza.Pizza;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class testPizzaController {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testPostPizzaAvecObject() throws Exception {
        HashMap<Taille, Double> tarifTaille = getTailleDoubleHashMap();
        List<Ingredient> listeIngredients = List.of(new Ingredient("Pepperoni", 40), new Ingredient("Mozarella", 30));

        Pizza pizza = new Pizza("Burger", tarifTaille, listeIngredients);
        mockMvc.perform(MockMvcRequestBuilders.post("/pizza")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pizza)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(Matchers.not(0)))
                .andExpect(jsonPath("$.nom").value("Burger"));



    }

    private static HashMap<Taille, Double> getTailleDoubleHashMap() {
        HashMap<Taille, Double> tarifTaille = new HashMap<>();
        tarifTaille.put(Taille.GRANDE, 12.00);
        return tarifTaille;
    }

}
