package com.accenture.service.commande;

import com.accenture.exception.CommandeException;
import com.accenture.repository.dao.commande.CommandeDAO;
import com.accenture.repository.entity.client.Client;
import com.accenture.repository.entity.commande.Commande;
import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.service.dto.client.ClientRequestDTO;
import com.accenture.service.dto.client.ClientResponseDTO;
import com.accenture.service.dto.commande.CommandeRequestDTO;
import com.accenture.service.dto.commande.CommandeResponseDTO;
import com.accenture.service.dto.ingredient.IngredientRequestDTO;
import com.accenture.service.dto.ingredient.IngredientResponseDTO;
import com.accenture.service.dto.pizza.PizzaRequestDto;
import com.accenture.service.dto.pizza.PizzaResponseDto;
import com.accenture.service.mapper.commande.CommandeMapper;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommandeServiceImplTest {
    @Mock
    private CommandeDAO commandeDAO;
    @Mock
    private CommandeMapper commandeMapper;
    @InjectMocks
    private CommandeServiceImpl commandeService;

    @Test
    void testAjouterNull() {
        CommandeException commandeException = assertThrows(CommandeException.class, () -> commandeService.ajouter(null));
        assertEquals("La commande doit exister.", commandeException.getMessage());
    }

    @Test
    void testAjouterSuccess() {
        CommandeRequestDTO commandeRequestDTO = creerCommandeRequestDTO();
        Commande commande = creerCommande();
        Commande commandeRetourne = creerCommande();
        commandeRetourne.setId(1);
        CommandeResponseDTO commandeResponseDTO = creerCommandeResponceDTO();

        when(commandeMapper.toCommande(commandeRequestDTO)).thenReturn(commande);
        when(commandeDAO.save(commande)).thenReturn(commandeRetourne);
        when(commandeMapper.toCommandeResponseDTO(commandeRetourne)).thenReturn(commandeResponseDTO);
        assertEquals(commandeResponseDTO, commandeService.ajouter(commandeRequestDTO));
        verify(commandeDAO).save(commande);
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
        return List.of(new IngredientResponseDTO(1, "Pepperoni", 40), new IngredientResponseDTO(2, "Mozzarella", 20));
    }

    private Commande creerCommande() {
        return new Commande(creerClient(), creerListePizzas(), Statut.EN_PREPARATION, 24.0);
    }

    private Client creerClient() {
        return new Client("DUPONT", "Jean", "jean@mail.fr", 0);
    }

    private List<Pizza> creerListePizzas() {
        return List.of(creerPizza(), creerPizza());
    }

    private Pizza creerPizza() {
        Map<Taille, Double> tarif = new HashMap<>();
        tarif.put(Taille.MOYENNE, 12.0);
        return new Pizza("Margherita", tarif, creerListeIngredients());
    }

    private List<Ingredient> creerListeIngredients() {
        return List.of(new Ingredient("Pepperoni", 40), new Ingredient("Mozzarella", 20));
    }

    private CommandeRequestDTO creerCommandeRequestDTO() {
        return new CommandeRequestDTO(creerClientRequestDTO(), creerListePizzaRequestDTOs(), Statut.EN_PREPARATION, 24.0);
    }

    private ClientRequestDTO creerClientRequestDTO() {
        return new ClientRequestDTO(
                "THEBAULT",
                "Elian",
                "elian@mail.com",
                0
        );
    }

    private List<PizzaRequestDto> creerListePizzaRequestDTOs() {
        return List.of(creerPizzaReauestDTO(), creerPizzaReauestDTO());
    }

    private PizzaRequestDto creerPizzaReauestDTO() {
        Map<Taille, Double> tarif = new HashMap<>();
        tarif.put(Taille.MOYENNE, 12.0);
        return new PizzaRequestDto("Margherita", tarif, creerListeIngredientRequestDTOs());
    }

    private List<IngredientRequestDTO> creerListeIngredientRequestDTOs() {
        return List.of(new IngredientRequestDTO("Pepperoni", 40), new IngredientRequestDTO("Mozarella", 30));
    }
}
