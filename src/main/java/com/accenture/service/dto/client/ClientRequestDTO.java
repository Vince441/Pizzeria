package com.accenture.service.dto.client;

public record ClientRequestDTO(
        String nom,
        String prenom,
        String email
) {
}
