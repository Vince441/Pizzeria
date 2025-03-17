package com.accenture.service.ingredient;

import com.accenture.exception.IngredientException;
import com.accenture.repository.dao.ingredient.IngredientDAO;
import com.accenture.service.dto.ingredient.IngredientRequestDTO;
import com.accenture.service.mapper.ingredient.IngredientMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
