package com.accenture.service.ingredient;

import com.accenture.exception.IngredientException;
import com.accenture.repository.dao.ingredient.IngredientDAO;
import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.service.dto.ingredient.IngredientRequestDTO;
import com.accenture.service.dto.ingredient.IngredientResponseDTO;
import com.accenture.service.mapper.ingredient.IngredientMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientDAO ingredientDAO;
    private final IngredientMapper ingredientMapper;

    public IngredientServiceImpl(IngredientDAO ingredientDAO, IngredientMapper ingredientMapper) {
        this.ingredientDAO = ingredientDAO;
        this.ingredientMapper = ingredientMapper;
    }

    @Override
    public IngredientResponseDTO ajouter(IngredientRequestDTO ingredientRequestDTO) throws IngredientException {
        validerIngredient(ingredientRequestDTO);
        return retourneIngredientResponseApresAjout(ingredientRequestDTO);
    }

    private static void validerIngredient(IngredientRequestDTO ingredientRequestDTO) {
        if (ingredientRequestDTO == null)
            throw new IngredientException("L'ingrédient doit exister.");
        if (ingredientRequestDTO.nom() == null
                || ingredientRequestDTO.nom().isBlank())
            throw new IngredientException("Le nom doit être renseigné.");
        if (ingredientRequestDTO.stock() == null)
            throw new IngredientException("Le stock doit être renseigné.");
        if (ingredientRequestDTO.stock() <= 0)
            throw new IngredientException("Le stock doit être positif.");
    }

    @Override
    public List<IngredientResponseDTO> trouverTous() {
        return ingredientDAO.findAll().stream()
                .map(ingredientMapper::toIngredientResponseDTO)
                .toList();
    }

    /*
     * METHODES PRIVEES
     */

    private IngredientResponseDTO retourneIngredientResponseApresAjout(IngredientRequestDTO ingredientRequestDTO) {
        Ingredient ingredient = ingredientMapper.toIngredient(ingredientRequestDTO);
        Ingredient ingredientAjoute = ingredientDAO.save(ingredient);
        return ingredientMapper.toIngredientResponseDTO(ingredientAjoute);
    }
}
