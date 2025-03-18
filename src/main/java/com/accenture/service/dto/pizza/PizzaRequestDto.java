package com.accenture.service.dto.pizza;

import com.accenture.shared.Taille;

import java.util.HashMap;
import java.util.Map;

public record PizzaRequestDto(
        String nom,
        Map<Taille, Double> tarifTaille
//        List<IngredientRequestDTO> listIngredient
        ) {
}
