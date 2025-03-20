package com.accenture.service.dto.pizza;

import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.service.dto.ingredient.IngredientResponseDTO;
import com.accenture.shared.Taille;

import java.util.Map;
import java.util.List;

public record PizzaResponseDto(
        int id,
        String nom,
        Map<Taille, Double> tarifTaille,
        List<String> listeIngredients
) {
}
