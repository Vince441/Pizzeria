package com.accenture.service.dto.pizza;

import com.accenture.service.dto.ingredient.IngredientRequestDTO;
import com.accenture.shared.Taille;

import java.util.List;
import java.util.Map;

public record PizzaRequestDto(
        String nom,
        Map<Taille, Double> tarifTaille,
        List<IngredientRequestDTO> listeIngredients
) {
}
