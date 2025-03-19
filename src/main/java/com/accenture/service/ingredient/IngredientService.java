package com.accenture.service.ingredient;

import com.accenture.exception.IngredientException;
import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.service.dto.ingredient.IngredientRequestDTO;
import com.accenture.service.dto.ingredient.IngredientResponseDTO;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface IngredientService {
    IngredientResponseDTO ajouter(IngredientRequestDTO ingredientRequestDTO) throws IngredientException;
    List<IngredientResponseDTO> trouverTous();
    IngredientResponseDTO modifier(int id, IngredientRequestDTO ingredientRequestDTO) throws EntityNotFoundException, IngredientException;
    //IngredientResponseDTO decrementerIngredient(Ingredient ingredient);
}
