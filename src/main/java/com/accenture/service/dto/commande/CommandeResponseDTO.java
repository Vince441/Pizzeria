package com.accenture.service.dto.commande;

import com.accenture.service.dto.client.ClientResponseDTO;
import com.accenture.service.dto.pizza.PizzaResponseDto;
import com.accenture.shared.Statut;

import java.util.List;

public record CommandeResponseDTO(
        int id,
        ClientResponseDTO clientResponseDTO,
        List<PizzaResponseDto> listePizzaResponseDTOs,
        Statut statut,
        Double totalPrix
) {
}
