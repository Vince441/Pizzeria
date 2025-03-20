package com.accenture.controller.ingredient;

import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.service.dto.ingredient.IngredientRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.DisplayName.class)
class IngredientControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("a) testPostIngredientAvecObjet")
    void testPostIngredientAvecObjet() throws Exception {
        Ingredient ingredient = new Ingredient("Basilic", 45);
        mockMvc.perform(MockMvcRequestBuilders.post("/ingredients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ingredient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(Matchers.not(0)))
                .andExpect(jsonPath("$.nom").value("Basilic"));
    }

    @Test
    @DisplayName("b) testPostIngredientFail")
    void testPostIngredientFail() throws Exception {
        Ingredient ingredient = new Ingredient(null, 45);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/ingredients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ingredient)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("Erreur validation"))
                .andExpect(jsonPath("$.message").value("Le nom doit être renseigné."));
    }

    @Test
    @DisplayName("c) testTrouverTous")
    void testTrouverTous() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/ingredients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nom").value("Pepperoni"))
                .andExpect(jsonPath("$[1].nom").value("Mozzarella"));
    }

    @Test
    @DisplayName("d) testPatch")
    void testPatch() throws Exception {
        int id = 1;
        IngredientRequestDTO ingredientRequestDTO = new IngredientRequestDTO(null, 45);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/ingredients/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingredientRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.stock").value(45))
                .andExpect(jsonPath("$.nom").value("Pepperoni"));
    }
}
