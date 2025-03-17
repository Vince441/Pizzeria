package com.accenture.service.ingredient;

import com.accenture.exception.IngredientException;
import com.accenture.service.dto.ingredient.IngredientRequestDTO;
import com.accenture.service.dto.ingredient.IngredientResponseDTO;

public class IngredientServiceImpl implements IngredientService {

    @Override
    public IngredientResponseDTO ajouter(IngredientRequestDTO ingredientRequestDTO) throws IngredientException {
        if (ingredientRequestDTO == null)
            throw new IngredientException("L'ingrédient doit exister.");
        if (ingredientRequestDTO.nom() == null
                || ingredientRequestDTO.nom().isBlank())
            throw new IngredientException("Le nom doit être renseigné.");
        if (ingredientRequestDTO.stock() == null)
            throw new IngredientException("Le stock doit être renseigné.");
        if (ingredientRequestDTO.stock() <= 0)
            throw new IngredientException("Le stock doit être positif.");
        return null;
    }
}
