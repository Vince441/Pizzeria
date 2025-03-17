package com.accenture.service.ingredient;

import com.accenture.exception.IngredientException;
import com.accenture.service.dto.ingredient.IngredientRequestDTO;
import com.accenture.service.dto.ingredient.IngredientResponseDTO;

public interface IngredientService {
    IngredientResponseDTO ajouter(IngredientRequestDTO ingredientRequestDTO) throws IngredientException;
}
