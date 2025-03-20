package com.accenture.controller.pizza;


import com.accenture.exception.PizzaException;
import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.service.dto.ingredient.IngredientRequestDTO;
import com.accenture.service.dto.ingredient.IngredientResponseDTO;
import com.accenture.service.dto.pizza.PizzaRequestDto;
import com.accenture.service.dto.pizza.PizzaResponseDto;
import com.accenture.service.ingredient.IngredientService;
import com.accenture.service.pizza.PizzaService;
import com.accenture.shared.Taille;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.RecordComponent;
import java.util.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    void ajouterPizza() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/pizzas")
                .param("nom", "Margherita")
                .param("petiteTarif", "10.0")
                .param("moyenneTarif", "15.0")
                .param("grandeTarif", "20.0")
                .param("list", "1,2"))
                .andExpect(status().isOk());
    }



@Test
void testPostPizzaFail() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/pizzas")
                    .param("nom", "")
                    .param("petiteTarif", "10.0")
                    .param("moyenneTarif", "15.0")
                    .param("grandeTarif", "20.0")
                    .param("list", "1,2,3"))
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

    @Test
    void testPatchPizza() throws Exception {

    }

private static List<Ingredient> creerListeIngredients() {
    return List.of(new Ingredient(1, "Pepperoni", 50), new Ingredient(2, "Mozzarella", 35));
}


private static Map<Taille, Double> getTailleDoubleHashMap() {
    Map<Taille, Double> tarifTaille = new HashMap<>();
    tarifTaille.put(Taille.PETITE, 7.00);
    tarifTaille.put(Taille.MOYENNE, 13.00);
    tarifTaille.put(Taille.GRANDE, 17.00);
    return tarifTaille;
}

}
