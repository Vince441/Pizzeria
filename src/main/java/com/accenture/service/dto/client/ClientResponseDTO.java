package com.accenture.service.dto.client;

public record ClientResponseDTO(
        Integer id,
        String nom,
        String prenom,
        String email,
        Integer totalAchat
) {
}
