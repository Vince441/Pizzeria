package com.accenture.service.dto.pizza;

import com.accenture.shared.Taille;

public record PizzaCommandeRequestDTO(
        PizzaRequestDto pizzaRequestDto,
        Taille taille
) {
}
