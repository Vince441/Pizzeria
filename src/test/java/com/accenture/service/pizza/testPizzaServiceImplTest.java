package com.accenture.service.pizza;

import com.accenture.exception.PizzaException;
import com.accenture.repository.dao.pizza.PizzaDao;
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


@ExtendWith(MockitoExtension.class)

public class testPizzaServiceImplTest {

    @InjectMocks
    private PizzaServiceImpl service;

    @Mock
    PizzaMapper mapperMock;

    @Mock
    PizzaDao daoMock;


    @Test
    void testAjouterPizzaNull() {
        PizzaException pe = assertThrows(PizzaException.class, () -> service.ajouter(null));
        assertEquals("La pizza doit exister", pe.getMessage());
    }

    @Test
    void testAjouterPizzaNomNull() {
        Map<Taille, Double> tarifTaille = getTailleDoubleHashMap();
        List<IngredientRequestDTO> listeIngredientRequestDTOs = List.of(new IngredientRequestDTO("Pepperoni", 40), new IngredientRequestDTO("Mozzarella", 20));
        PizzaRequestDto dto = new PizzaRequestDto(null, tarifTaille, listeIngredientRequestDTOs);
        PizzaException pe = assertThrows(PizzaException.class, () -> service.ajouter(dto));
        assertEquals("Le nom est obligatoire", pe.getMessage());
    }

    @Test
    void testAjouterPizzaNomBlank() {
        Map<Taille, Double> tarifTaille = getTailleDoubleHashMap();

        PizzaRequestDto dto = new PizzaRequestDto("\n", tarifTaille, creerListeIngredientRequestDTOs());
        PizzaException pe = assertThrows(PizzaException.class, () -> service.ajouter(dto));
        assertEquals("Le nom est obligatoire", pe.getMessage());
    }

    @Test
    void testAjouterPizzaTailleNull(){
        Map<Taille, Double> tarifTaille = new HashMap<>();
        tarifTaille.put(null, 12.00);
        PizzaRequestDto dto = new PizzaRequestDto("Kebab",tarifTaille, creerListeIngredientRequestDTOs());

        PizzaException pe = assertThrows(PizzaException.class, () -> service.ajouter(dto));
        assertEquals("La taille est obligatoire", pe.getMessage());

    }


    @Test
    void testAjouterOk() {
        Map<Taille, Double> tarifTaille = getTailleDoubleHashMap();

        PizzaRequestDto requestDto = new PizzaRequestDto("Margarita", tarifTaille, creerListeIngredientRequestDTOs());
        Pizza pizzaAvantEnreg = creePizza();

        Pizza pizzaApresEnreg = creePizza();
        PizzaResponseDto responseDto = creePizzaResponseDto();

        Mockito.when(mapperMock.toPizza(requestDto)).thenReturn(pizzaAvantEnreg);
        Mockito.when(daoMock.save(pizzaAvantEnreg)).thenReturn(pizzaApresEnreg);
        Mockito.when(mapperMock.toPizzaResponseDto(pizzaApresEnreg)).thenReturn(responseDto);

        assertSame(responseDto, service.ajouter(requestDto));
    }


    @Test
    void testSupprimerExiste() {

        Pizza pizza = creePizza();
        Integer id = pizza.getId();
        Mockito.when(daoMock.findById(id)).thenReturn(Optional.of(pizza));
        service.supprimer(id);
        Mockito.verify(daoMock, Mockito.times(1)).delete(pizza);

    }


    @Test
    void testSupprimerExistePas() {
        Mockito.when(daoMock.findById(4)).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.supprimer(4));
        assertEquals("Pizza non trouvé", ex.getMessage());
    }

    @Test
    void testModifierPartiellement() {
        int id = 1;
        Map<Taille, Double> tarifTaille = getTailleDoubleHashMap();


        Pizza pizzaExistante = creePizza();
        PizzaResponseDto pizzaEnreg = creePizzaResponseDto();
        PizzaRequestDto pizzaRequestDto = new PizzaRequestDto("Bolognaise", tarifTaille, creerListeIngredientRequestDTOs());


        Mockito.when(daoMock.findById(id)).thenReturn(Optional.of(pizzaExistante));
        Mockito.when(mapperMock.toPizza(pizzaRequestDto)).thenReturn(pizzaExistante);
        Mockito.when(daoMock.save(pizzaExistante)).thenReturn(pizzaExistante);
        Mockito.when(mapperMock.toPizzaResponseDto(pizzaExistante)).thenReturn(pizzaEnreg);

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

        return new PizzaResponseDto(1, "Margarita", tarifTaille, creerListeIngredientReponseDTOs());
    }

    private static Map<Taille, Double> getTailleDoubleHashMap() {
        Map<Taille, Double> tarifTaille = new HashMap<>();
        tarifTaille.put(Taille.GRANDE, 12.00);
        return tarifTaille;
    }

}
