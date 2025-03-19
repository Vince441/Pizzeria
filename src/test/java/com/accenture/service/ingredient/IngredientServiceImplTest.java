package com.accenture.service.ingredient;

import com.accenture.exception.IngredientException;
import com.accenture.repository.dao.ingredient.IngredientDAO;
import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.service.dto.ingredient.IngredientRequestDTO;
import com.accenture.service.dto.ingredient.IngredientResponseDTO;
import com.accenture.service.mapper.ingredient.IngredientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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
    void ajouterSuccess() {
        IngredientRequestDTO ingredientRequestDTO = creerPepperoniRequestDTO();
        Ingredient ingredient = creerPepperoni();
        Ingredient returnedIngredient = creerPepperoni();
        returnedIngredient.setId(1);
        IngredientResponseDTO ingredientResponseDTO = creerPepperoniResponseDTO();
        when(ingredientMapper.toIngredient(ingredientRequestDTO)).thenReturn(ingredient);
        when(ingredientDAO.save(ingredient)).thenReturn(returnedIngredient);
        when(ingredientMapper.toIngredientResponseDTO(returnedIngredient)).thenReturn(ingredientResponseDTO);
        assertEquals(ingredientResponseDTO, ingredientService.ajouter(ingredientRequestDTO));
        verify(ingredientDAO).save(ingredient);
    }

    @Test
    void testTrouverTous() {
        Ingredient pepperoni = creerPepperoni();
        Ingredient mozzarella = creerMozzarella();
        List<Ingredient> listeIngredients = List.of(pepperoni, mozzarella);
        IngredientResponseDTO pepperoniResponseDTO = creerPepperoniResponseDTO();
        IngredientResponseDTO mozzarellaResponseDTO = creerMozzarellaResponseDTO();
        List<IngredientResponseDTO> listeIngredientResponseDTOs = List.of(pepperoniResponseDTO, mozzarellaResponseDTO);
        when(ingredientDAO.findAll()).thenReturn(listeIngredients);
        when(ingredientMapper.toIngredientResponseDTO(pepperoni)).thenReturn(pepperoniResponseDTO);
        when(ingredientMapper.toIngredientResponseDTO(mozzarella)).thenReturn(mozzarellaResponseDTO);
        assertEquals(listeIngredientResponseDTOs, ingredientService.trouverTous());
        verify(ingredientDAO).findAll();
    }

    @Test
    void mocifierFail() {
        IngredientRequestDTO ingredientRequestDTO = creerPepperoniRequestDTO();
        EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> ingredientService.modifier(44, ingredientRequestDTO));
        assertEquals("L'ingredient n'existe pas", entityNotFoundException.getMessage());
    }

    @Test
    void modifierSuccess() {
        int id = 1;
        IngredientRequestDTO ingredientRequestDTO = creerPepperoniRequestDTO();
        Ingredient ingredientExistant = creerPepperoni();
        ingredientExistant.setId(id);
        Optional<Ingredient> optionalIngredientExistant = Optional.of(ingredientExistant);
        Ingredient ingredientModifie = creerPepperoni();
        ingredientModifie.setId(id);
        Ingredient ingredientRetourne = creerPepperoni();
        ingredientRetourne.setId(id);
        IngredientResponseDTO ingredientResponseAttendu = creerAutrePepperoniResponseDTO();

        when(ingredientDAO.existsById(id)).thenReturn(true);
        when(ingredientDAO.findById(id)).thenReturn(optionalIngredientExistant);
        when(ingredientMapper.toIngredient(ingredientRequestDTO)).thenReturn(ingredientModifie);
        when(ingredientDAO.save(ingredientExistant)).thenReturn(ingredientRetourne);
        when(ingredientMapper.toIngredientResponseDTO(ingredientExistant)).thenReturn(ingredientResponseAttendu);

        IngredientResponseDTO resultat = ingredientService.modifier(id, ingredientRequestDTO);

        verify(ingredientDAO).save(ingredientExistant);
    }

    /*
     * METHODES PRIVEES
     */

    private static Ingredient creerMozzarella() {
        return new Ingredient("Mozzarella", 35);
    }

    private static Ingredient creerPepperoni() {
        return new Ingredient("Pepperoni", 50);
    }

    private IngredientResponseDTO creerPepperoniResponseDTO() {
        return new IngredientResponseDTO(1, "Pepperoni", 50);
    }

    private IngredientResponseDTO creerAutrePepperoniResponseDTO() {
        return new IngredientResponseDTO(1, "Pepperoni", 20);
    }

    private IngredientResponseDTO creerMozzarellaResponseDTO() {
        return new IngredientResponseDTO(2, "Mozzarella", 35);
    }

    private IngredientRequestDTO creerPepperoniRequestDTO() {
        return new IngredientRequestDTO("Pepperoni", 50);
    }
}
