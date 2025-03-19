package com.accenture.service.dto.pizza;

import com.accenture.shared.Taille;

public record PizzaCommandeRequestDTO(
        int pizza_id,
        Taille taille
) {
}
