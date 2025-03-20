package com.accenture.service.commande;

import com.accenture.exception.CommandeException;
import com.accenture.repository.dao.commande.CommandeDAO;
import com.accenture.repository.entity.client.Client;
import com.accenture.repository.entity.commande.Commande;
import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.repository.entity.pizza.PizzaCommande;
import com.accenture.service.client.ClientService;
import com.accenture.service.dto.client.ClientRequestDTO;
import com.accenture.service.dto.client.ClientResponseDTO;
import com.accenture.service.dto.commande.CommandeRequestDTO;
import com.accenture.service.dto.commande.CommandeResponseDTO;
import com.accenture.service.dto.ingredient.IngredientRequestDTO;
import com.accenture.service.dto.ingredient.IngredientResponseDTO;
import com.accenture.service.dto.pizza.PizzaCommandeRequestDTO;
import com.accenture.service.dto.pizza.PizzaRequestDto;
import com.accenture.service.dto.pizza.PizzaResponseDto;
import com.accenture.service.ingredient.IngredientService;
import com.accenture.service.mapper.client.ClientMapper;
import com.accenture.service.mapper.commande.CommandeMapper;
import com.accenture.service.mapper.ingredient.IngredientMapper;
import com.accenture.service.mapper.pizza.PizzaMapper;
import com.accenture.service.pizza.PizzaService;
import com.accenture.shared.Statut;
import com.accenture.shared.Taille;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        when(pizzaMapper.toPizzaCommande(creerPizzaCommandeRequestDTO())).thenReturn(creerPizzaCommande());
        Commande commandeRetourne = creerCommande();
        commandeRetourne.setId(1);
        CommandeRequestDTO commandeRequestDTO = creerCommandeRequestDTO();
        CommandeResponseDTO commandeResponseDTO = creerCommandeResponceDTO();
        when(commandeDAO.save(any())).thenReturn(commandeRetourne);
        when(commandeMapper.toCommandeResponseDTO(commandeRetourne)).thenReturn(commandeResponseDTO);
        assertEquals(commandeResponseDTO, commandeService.ajouter(commandeRequestDTO));
        verify(commandeDAO).save(any());
    }

    @Test
    void testModifierFail() {
        EntityNotFoundException entityNotFountException = assertThrows(EntityNotFoundException.class, () -> commandeService.modifier(44,null));
        assertEquals("La commande n'existe pas.", entityNotFountException.getMessage());
    }

    @Test
    void testModifierSuccess() {
        int id = 1;
        Statut statut = Statut.PRETE;
        Statut statutRetourne = Statut.LIVREE;
        Commande commande = creerCommande();
        Commande commandeRetourne = creerCommande();
        commande.setId(id);
        commandeRetourne.setId(id);
        commande.setStatut(statut);
        commandeRetourne.setStatut(statutRetourne);

        CommandeResponseDTO commandeResponseDTO = commandeMapper.toCommandeResponseDTO(commandeRetourne);

        when(commandeDAO.existsById(id)).thenReturn(true);
        when(commandeDAO.findById(id)).thenReturn(Optional.of(commande));
        when(commandeDAO.save(commande)).thenReturn(commandeRetourne);
        when(commandeMapper.toCommandeResponseDTO(commandeRetourne)).thenReturn(commandeResponseDTO);
        assertEquals(commandeResponseDTO, commandeService.modifier(id, statut));
        verify(commandeDAO).save(commande);
    }

    /*
     * METHODES PRIVEES
     */

    private List<PizzaCommande> creerListePizzaCommande() {
        return List.of(creerPizzaCommande(), creerPizzaCommande());
    }

    private PizzaCommande creerPizzaCommande() {
        return new PizzaCommande(creerPizza(), Taille.MOYENNE);
    }

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

    private List<String> creerListeIngredientReponseDTOs() {
        return List.of("Pepperoni","Mozzarella");
    }

    private Commande creerCommande() {
        return new Commande(creerClient(), creerListePizzaCommande(), Statut.EN_PREPARATION, 24.0);
    }

    //private Commande creerCommandeRetourne() {
    //    return new Commande(1, creerClient(), creerListePizzaDecrementes(), Statut.EN_PREPARATION, 24.0);
    //}

    private Client creerClient() {
        return new Client(1, "DUPONT", "Jean", "jean@mail.fr", 0);
    }

    private List<Pizza> creerListePizzas() {
        return List.of(creerPizza(), creerPizza());
    }

    //private List<PizzaCommande> creerListePizzaDecrementes() {
    //    return List.of(creerPizzaDecrementee(), creerPizzaDecrementee());
    //}

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
        return new CommandeRequestDTO(creerClientRequestDTO(), creerListePizzaCommandeRequestDTOs(), Statut.EN_PREPARATION, 24.0);
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
        return new PizzaCommandeRequestDTO(creerPizzaRequestDTO(), Taille.MOYENNE);
    }

    private PizzaRequestDto creerPizzaRequestDTO() {
        Map<Taille, Double> tarif = new HashMap<>();
        tarif.put(Taille.MOYENNE, 12.0);
        return new PizzaRequestDto("Marguerita", tarif, creerListeIngredientDTO());
    }

    private List<IngredientRequestDTO> creerListeIngredientDTO() {
        return List.of(new IngredientRequestDTO("Pepperonni", 50), new IngredientRequestDTO("Mozzarella", 30));
    }
}
