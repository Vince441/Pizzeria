package com.accenture.service.ingredient;

import com.accenture.exception.IngredientException;
import com.accenture.service.dto.ingredient.IngredientRequestDTO;
import com.accenture.service.dto.ingredient.IngredientResponseDTO;

public class IngredientServiceImpl implements IngredientService {

    @Override
    public IngredientResponseDTO ajouter(IngredientRequestDTO ingredientRequestDTO) throws IngredientException {
        if (ingredientRequestDTO == null)
            throw new IngredientException("L'ingrédient doit exister.");
        return null;
    }
}
