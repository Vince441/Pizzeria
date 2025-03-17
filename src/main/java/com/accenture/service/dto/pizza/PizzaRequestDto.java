package com.accenture.service.dto.pizza;

import com.accenture.shared.Taille;

import java.util.HashMap;

public record PizzaRequestDto(
        String nom,
        HashMap<Taille, Double> tarifTaille
//        List<IngredientRequestDTO> listIngredient
        ) {
}
