package com.accenture.service.ingredient;

import com.accenture.exception.IngredientException;
import com.accenture.repository.dao.ingredient.IngredientDAO;
import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.service.dto.ingredient.IngredientRequestDTO;
import com.accenture.service.dto.ingredient.IngredientResponseDTO;
import com.accenture.service.mapper.ingredient.IngredientMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {
    @Mock
    private IngredientDAO ingredientDAO;
    @Mock
    private IngredientMapper ingredientMapper;
    @InjectMocks
    private IngredientServiceImpl ingredientService;

    @Test
    void testAjouterNull() {
        IngredientException ingredientException = assertThrows(IngredientException.class, () -> ingredientService.ajouter(null));
        assertEquals("L'ingrédient doit exister.", ingredientException.getMessage());
    }

    @ParameterizedTest
    @CsvSource(value = {
            ", 50, Le nom doit être renseigné.",
            "'', 50, Le nom doit être renseigné.",
            "Pepperoni, , Le stock doit être renseigné.",
            "Pepperoni, 0, Le stock doit être positif."
    })
    void testAjouterFail(String nom, Integer stock, String expected) {
        IngredientRequestDTO ingredientRequestDTO = new IngredientRequestDTO(nom, stock);
        IngredientException ingredientException = assertThrows(IngredientException.class, () -> ingredientService.ajouter(ingredientRequestDTO));
        assertEquals(expected, ingredientException.getMessage());
    }

    @Test
    void testTrouverTous() {
        Ingredient pepperoni = creerPepperoni();
        Ingredient mozarella = creerMozarella();
        List<Ingredient> listeIngredients = List.of(pepperoni, mozarella);
        IngredientResponseDTO pepperoniResponseDTO = creerPepperoniResponseDTO();
        IngredientResponseDTO mozarellaResponseDTO = creerMozarellaResponseDTO();
        List<IngredientResponseDTO> listeIngredientResponseDTOs = List.of(pepperoniResponseDTO, mozarellaResponseDTO);
        when(ingredientDAO.findAll()).thenReturn(listeIngredients);
        when(ingredientMapper.toIngredientResponseDTO(pepperoni)).thenReturn(pepperoniResponseDTO);
        when(ingredientMapper.toIngredientResponseDTO(mozarella)).thenReturn(mozarellaResponseDTO);
        assertEquals(listeIngredientResponseDTOs, ingredientService.trouverTous());
        verify(ingredientDAO).findAll();
    }

    /*
     * METHODES PRIVEES
     */

    private static Ingredient creerMozarella() {
        return new Ingredient("Mozarella", 35);
    }

    private static Ingredient creerPepperoni() {
        return new Ingredient("Pepperoni", 50);
    }

    private IngredientResponseDTO creerPepperoniResponseDTO() {
        return new IngredientResponseDTO(1, "Pepperoni", 50);
    }

    private IngredientResponseDTO creerMozarellaResponseDTO() {
        return new IngredientResponseDTO(2, "Mozarella", 35);
    }
}
