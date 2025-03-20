package com.accenture.service.pizza;

import com.accenture.exception.PizzaException;
import com.accenture.repository.dao.ingredient.IngredientDAO;
import com.accenture.repository.dao.pizza.PizzaDao;
import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.service.dto.ingredient.IngredientRequestDTO;
import com.accenture.service.dto.ingredient.IngredientResponseDTO;
import com.accenture.service.dto.pizza.PizzaRequestDto;
import com.accenture.service.dto.pizza.PizzaResponseDto;
import com.accenture.service.mapper.pizza.PizzaMapper;
import com.accenture.shared.Taille;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)

public class testPizzaServiceImplTest {

    @InjectMocks
    private PizzaServiceImpl service;

    @Mock
    PizzaMapper mapperMock;

    @Mock
    PizzaDao daoMock;

    @Mock
    IngredientDAO ingDaoMock;

    @Test
    void testAjouterPizzaNull() {
        PizzaException pe = assertThrows(PizzaException.class, () -> service.ajouter(null));
        assertEquals("La pizza doit exister", pe.getMessage());
    }

    @Test
    void testAjouterPizzaNomNull() {
        Map<Taille, Double> tarifTaille = getTailleDoubleHashMap();
        List<Integer> listeIngredientRequestDTOs = List.of(1);
        PizzaRequestDto dto = new PizzaRequestDto(null, tarifTaille, listeIngredientRequestDTOs);
        PizzaException pe = assertThrows(PizzaException.class, () -> service.ajouter(dto));
        assertEquals("Le nom est obligatoire", pe.getMessage());
    }

    @Test
    void testAjouterPizzaNomBlank() {
        Map<Taille, Double> tarifTaille = getTailleDoubleHashMap();
        List<Integer> listeIngredientRequestDTOs = List.of(1);
        PizzaRequestDto dto = new PizzaRequestDto("\n", tarifTaille, listeIngredientRequestDTOs);
        PizzaException pe = assertThrows(PizzaException.class, () -> service.ajouter(dto));
        assertEquals("Le nom est obligatoire", pe.getMessage());
    }

    @Test
    void testAjouterPizzaTailleNull() {
        List<Integer> listeIngredientRequestDTOs = List.of(1);
        Map<Taille, Double> tarifTaille = new HashMap<>();
        tarifTaille.put(null, 12.00);
        PizzaRequestDto dto = new PizzaRequestDto("Kebab", tarifTaille, listeIngredientRequestDTOs);

        PizzaException pe = assertThrows(PizzaException.class, () -> service.ajouter(dto));
        assertEquals("La taille est obligatoire", pe.getMessage());

    }




    @Test
    void ajouterUnePizzaOk() throws PizzaException {
        List<Ingredient> ingredients = List.of(new Ingredient("Tomate", 5), new Ingredient("Fromage", 7), new Ingredient("Jambon", 3));
        Map<Taille, Double> tarifMap = new HashMap<>();

        PizzaRequestDto requestDto = new PizzaRequestDto("Reine", tarifMap, List.of(1, 2, 3));
        Pizza pizzaEnreg = new Pizza("Reine", tarifMap, ingredients);
        pizzaEnreg.setId(1);
        PizzaResponseDto responseDto = new PizzaResponseDto(1, "Reine", tarifMap, List.of("Tomate", "Fromage", "Jambon"));
        when(ingDaoMock.findAllById(requestDto.listeIngredients())).thenReturn(ingredients);
        when(daoMock.save(any(Pizza.class))).thenReturn(pizzaEnreg);
        assertEquals(responseDto, service.ajouter(requestDto));
        verify(daoMock, Mockito.times(1)).save(any(Pizza.class));
        verify(ingDaoMock, Mockito.times(1)).findAllById(requestDto.listeIngredients());
    }


    @Test
    void testSupprimerExiste() {

        Pizza pizza = creePizza();
        Integer id = pizza.getId();
        when(daoMock.findById(id)).thenReturn(Optional.of(pizza));
        service.supprimer(id);
        Mockito.verify(daoMock, Mockito.times(1)).delete(pizza);

    }


    @Test
    void testSupprimerExistePas() {
        when(daoMock.findById(4)).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.supprimer(4));
        assertEquals("Pizza non trouvé", ex.getMessage());
    }

    @Test
    void testModifierPartiellement() {
        int id = 1;
        Map<Taille, Double> tarifTaille = getTailleDoubleHashMap();
        List<Integer> listeIngredientRequestDTOs = List.of(1);


        Pizza pizzaExistante = creePizza();
        PizzaResponseDto pizzaEnreg = creePizzaResponseDto();
        PizzaRequestDto pizzaRequestDto = new PizzaRequestDto("Bolognaise", tarifTaille, listeIngredientRequestDTOs);


        when(daoMock.findById(id)).thenReturn(Optional.of(pizzaExistante));
        when(mapperMock.toPizza(pizzaRequestDto)).thenReturn(pizzaExistante);
        when(daoMock.save(pizzaExistante)).thenReturn(pizzaExistante);
        when(mapperMock.toPizzaResponseDto(pizzaExistante)).thenReturn(pizzaEnreg);

        PizzaResponseDto result = service.modifierPartiellement(id, pizzaRequestDto);
        assertNotNull(result);
        assertEquals(pizzaEnreg, result);
        Mockito.verify(daoMock).findById(id);
        Mockito.verify(mapperMock).toPizza(pizzaRequestDto);
        Mockito.verify(daoMock).save(pizzaExistante);
        Mockito.verify(mapperMock).toPizzaResponseDto(pizzaExistante);

    }

    private List<IngredientRequestDTO> creerListeIngredientRequestDTOs() {
        return List.of(new IngredientRequestDTO("Pepperoni", 40), new IngredientRequestDTO("Mozarella", 30));
    }

    private static List<IngredientResponseDTO> creerListeIngredientReponseDTOs() {
        return List.of(new IngredientResponseDTO(1, "Pepperoni", 40), new IngredientResponseDTO(2, "Mozzarella", 20));
    }

    private Pizza creePizza() {
        Map<Taille, Double> tarifTaille = getTailleDoubleHashMap();

        Pizza p = new Pizza();
        p.setId(1);
        p.setNom("Margarita");
        p.setTarifTaille(tarifTaille);
        return p;
    }

    private static PizzaResponseDto creePizzaResponseDto() {
        Map<Taille, Double> tarifTaille = getTailleDoubleHashMap();
        List<String> listeIngredientRequestDTOs = List.of("Pepperoni");

        return new PizzaResponseDto(1, "Margarita", tarifTaille, listeIngredientRequestDTOs);
    }

    private static Map<Taille, Double> getTailleDoubleHashMap() {
        Map<Taille, Double> tarifTaille = new HashMap<>();
        tarifTaille.put(Taille.GRANDE, 12.00);
        return tarifTaille;
    }

}
