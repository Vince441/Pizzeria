package com.accenture.service.commande;

import com.accenture.exception.CommandeException;
import com.accenture.repository.dao.commande.CommandeDAO;
import com.accenture.repository.entity.client.Client;
import com.accenture.repository.entity.commande.Commande;
import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.service.client.ClientService;
import com.accenture.service.dto.client.ClientRequestDTO;
import com.accenture.service.dto.client.ClientResponseDTO;
import com.accenture.service.dto.commande.CommandeRequestDTO;
import com.accenture.service.dto.commande.CommandeResponseDTO;
import com.accenture.service.dto.ingredient.IngredientRequestDTO;
import com.accenture.service.dto.ingredient.IngredientResponseDTO;
import com.accenture.service.dto.pizza.PizzaCommandeRequestDTO;
import com.accenture.service.dto.pizza.PizzaResponseDto;
import com.accenture.service.ingredient.IngredientService;
import com.accenture.service.mapper.client.ClientMapper;
import com.accenture.service.mapper.commande.CommandeMapper;
import com.accenture.service.mapper.ingredient.IngredientMapper;
import com.accenture.service.mapper.pizza.PizzaMapper;
import com.accenture.service.pizza.PizzaService;
import com.accenture.shared.Statut;
import com.accenture.shared.Taille;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandeServiceImplTest {
    @Mock
    private CommandeDAO commandeDAO;
    @Mock
    private CommandeMapper commandeMapper;
    @Mock
    private ClientService clientService;
    @Mock
    private ClientMapper clientMapper;
    @Mock
    private PizzaService pizzaService;
    @Mock
    private PizzaMapper pizzaMapper;
    @Mock
    private IngredientService ingredientService;
    @Mock
    private IngredientMapper ingredientMapper;
    @InjectMocks
    private CommandeServiceImpl commandeService;

    @Test
    void testAjouterNull() {
        CommandeException commandeException = assertThrows(CommandeException.class, () -> commandeService.ajouter(null));
        assertEquals("La commande doit exister.", commandeException.getMessage());
    }

    @Test
    void testAjouterSuccess() {
        when(clientMapper.toClient(creerClientRequestDTO())).thenReturn(creerClient());
        when(pizzaService.findById(1)).thenReturn(creerPizzaResponseDTO());
        Pizza pizza = creerPizza();
        when(pizzaMapper.toPizzaFromResponse(creerPizzaResponseDTO())).thenReturn(pizza);

        Commande commande = creerCommande();
        //Commande commandeRetourne = creerCommandeRetourne();
        Commande commandeRetourne = creerCommande();
        commandeRetourne.setId(1);
        CommandeRequestDTO commandeRequestDTO = creerCommandeRequestDTO();
        CommandeResponseDTO commandeResponseDTO = creerCommandeResponceDTO();
        //when(commandeMapper.toCommande(commandeRequestDTO)).thenReturn(commande);
        when(commandeDAO.save(any())).thenReturn(commandeRetourne);
        when(commandeMapper.toCommandeResponseDTO(commandeRetourne)).thenReturn(commandeResponseDTO);
        assertEquals(commandeResponseDTO, commandeService.ajouter(commandeRequestDTO));
        verify(commandeDAO).save(any());
    }

    /*
     * METHODES PRIVEES
     */

    private CommandeResponseDTO creerCommandeResponceDTO() {
        return new CommandeResponseDTO(1, creerClientResponseDTO(), creerListePizzasResponseDTOs(), Statut.EN_PREPARATION, 24.0);
    }

    private ClientResponseDTO creerClientResponseDTO() {
        return new ClientResponseDTO(1, "DUPONT", "Jean", "jean@mail.fr", 0);
    }

    private List<PizzaResponseDto> creerListePizzasResponseDTOs() {
        return List.of(creerPizzaResponseDTO(), creerPizzaResponseDTO());
    }

    private PizzaResponseDto creerPizzaResponseDTO() {
        Map<Taille, Double> tarif = new HashMap<>();
        tarif.put(Taille.MOYENNE, 12.0);
        return new PizzaResponseDto(1, "Margherita", tarif, creerListeIngredientReponseDTOs());
    }

    private List<IngredientResponseDTO> creerListeIngredientReponseDTOs() {
        return List.of(new IngredientResponseDTO(1, "Pepperoni", 40), new IngredientResponseDTO(2, "Mozzarella", 40));
    }

    private Commande creerCommande() {
        return new Commande(creerClient(), creerListePizzas(), Statut.EN_PREPARATION, 24.0);
    }

    private Commande creerCommandeRetourne() {
        return new Commande(1, creerClient(), creerListePizzaDecrementes(), Statut.EN_PREPARATION, 24.0);
    }

    private Client creerClient() {
        return new Client(1, "DUPONT", "Jean", "jean@mail.fr", 0);
    }

    private List<Pizza> creerListePizzas() {
        return List.of(creerPizza(), creerPizza());
    }

    private List<Pizza> creerListePizzaDecrementes() {
        return List.of(creerPizzaDecrementee(), creerPizzaDecrementee());
    }

    private Pizza creerPizzaDecrementee() {
        Map<Taille, Double> tarif = new HashMap<>();
        tarif.put(Taille.MOYENNE, 12.0);
        return new Pizza(1, "Margherita", tarif, creerListeIngredientDecrementes());
    }

    private List<Ingredient> creerListeIngredientDecrementes() {
        return List.of(new Ingredient(1, "Pepperoni", 38), new Ingredient(2, "Mozzarella", 18));
    }

    private Pizza creerPizza() {
        Map<Taille, Double> tarif = new HashMap<>();
        tarif.put(Taille.MOYENNE, 12.0);
        return new Pizza(1, "Margherita", tarif, creerListeIngredients());
    }

    private List<Ingredient> creerListeIngredients() {
        return List.of(creerIngredient(), creerAutreIngredient());
    }

    private Ingredient creerIngredient() {
        return new Ingredient(1, "Pepperoni", 40);
    }

    private Ingredient creerAutreIngredient() {
        return new Ingredient(2, "Mozzarella", 20);
    }

    private CommandeRequestDTO creerCommandeRequestDTO() {
        return new CommandeRequestDTO(creerClientRequestDTO(), creerListePizzaCommandeRequestDTOs());
    }

    private ClientRequestDTO creerClientRequestDTO() {
        return new ClientRequestDTO(
                "DUPONT",
                "Jean",
                "jean@mail.fr",
                0
        );
    }

    private List<PizzaCommandeRequestDTO> creerListePizzaCommandeRequestDTOs() {
        return List.of(creerPizzaCommandeRequestDTO(), creerPizzaCommandeRequestDTO());
    }

    private PizzaCommandeRequestDTO creerPizzaCommandeRequestDTO() {
        return new PizzaCommandeRequestDTO(1, Taille.MOYENNE);
    }
}
