package com.accenture.service.dto.commande;

import com.accenture.service.dto.client.ClientRequestDTO;
import com.accenture.service.dto.pizza.PizzaCommandeRequestDTO;
import com.accenture.shared.Statut;

import java.util.List;

public record CommandeRequestDTO(
        ClientRequestDTO clientRequestDTO,
        List<PizzaCommandeRequestDTO> listePizzaCommandeRequestDTOs
) {
}
