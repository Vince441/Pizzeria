package com.accenture.service.ingredient;

import com.accenture.exception.IngredientException;
import com.accenture.repository.dao.ingredient.IngredientDAO;
import com.accenture.service.mapper.ingredient.IngredientMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
}
