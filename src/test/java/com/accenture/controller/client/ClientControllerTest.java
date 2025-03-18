package com.accenture.controller.client;

import com.accenture.repository.entity.client.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
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
class ClientControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPostClientAvecObjet() throws Exception {
        Client client = new Client("THEBAULT", "Elian", "elian@mail.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/clients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(Matchers.not(0)))
                .andExpect(jsonPath("$.nom").value("THEBAULT"));
    }

    @Test
    void testPostClientFail() throws Exception {
        Client client = new Client(null, "Elian", "elian@mail.com");
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/clients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("Erreur validation"))
                .andExpect(jsonPath("$.message").value("Le nom est obligatoire"));
    }
}
