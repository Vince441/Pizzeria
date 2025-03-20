package com.accenture.service.commande;

import com.accenture.exception.CommandeException;
import com.accenture.exception.IngredientException;
import com.accenture.repository.dao.commande.CommandeDAO;
import com.accenture.repository.entity.client.Client;
import com.accenture.repository.entity.commande.Commande;
import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.service.client.ClientService;
import com.accenture.service.dto.client.ClientRequestDTO;
import com.accenture.service.dto.commande.CommandeRequestDTO;
import com.accenture.service.dto.commande.CommandeResponseDTO;
import com.accenture.service.dto.pizza.PizzaCommandeRequestDTO;
import com.accenture.service.ingredient.IngredientService;
import com.accenture.service.mapper.client.ClientMapper;
import com.accenture.service.mapper.commande.CommandeMapper;
import com.accenture.service.mapper.ingredient.IngredientMapper;
import com.accenture.service.mapper.pizza.PizzaMapper;
import com.accenture.service.pizza.PizzaService;
import com.accenture.shared.Statut;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeServiceImpl implements CommandeService {
    private final PizzaService pizzaService;
    private final PizzaMapper pizzaMapper;
    private final IngredientService ingredientService;
    private final IngredientMapper ingredientMapper;
    private final ClientService clientService;
    private final ClientMapper clientMapper;
    private final CommandeDAO commandeDAO;
    private final CommandeMapper commandeMapper;

    public CommandeServiceImpl(PizzaService pizzaService, PizzaMapper pizzaMapper, IngredientService ingredientService, IngredientMapper ingredientMapper, ClientService clientService, ClientMapper clientMapper, CommandeDAO commandeDAO, CommandeMapper commandeMapper) {
        this.pizzaService = pizzaService;
        this.pizzaMapper = pizzaMapper;
        this.ingredientService = ingredientService;
        this.ingredientMapper = ingredientMapper;
        this.clientService = clientService;
        this.clientMapper = clientMapper;
        this.commandeDAO = commandeDAO;
        this.commandeMapper = commandeMapper;
    }

    @Override
    @Transactional
    public CommandeResponseDTO ajouter(CommandeRequestDTO commandeRequestDTO) throws CommandeException, IngredientException {
        validerCommande(commandeRequestDTO);
        Client client = clientMapper.toClient(commandeRequestDTO.clientRequestDTO());
        List<Pizza> listePizzas = trouverListePizza(commandeRequestDTO.listePizzaCommandeRequestDTOs());
        Double prixTotal = calculPrixTotal(commandeRequestDTO);
        if (verifierClientVIP(commandeRequestDTO.clientRequestDTO()))
            prixTotal  = reductionVIP(prixTotal);
        CommandeResponseDTO commandeResponseDTO = retourneCommandeResponseApresAjout(client, listePizzas, prixTotal);
        incrementClientTotalAchat(client);
        //TODO incrémenter totalachat dans client
        for (Pizza pizza : listePizzas)
            verifierIngredients(pizza);
        return commandeResponseDTO;
    }

    @Override
    public CommandeResponseDTO modifier(int id, Statut statut) throws EntityNotFoundException, CommandeException {
        if (!commandeDAO.existsById(id))
            throw new EntityNotFoundException("La commande n'existe pas.");
        Commande commande = commandeDAO.findById(id).get();
        verifierEtModifierCommande(statut, commande);
        return commandeMapper.toCommandeResponseDTO(commandeDAO.save(commande));
    }

    private static void verifierEtModifierCommande(Statut statut, Commande commande) {
        if (statut != null)
            if (commande.getStatut() != Statut.EN_PREPARATION
                    && statut == Statut.PRETE
                    || commande.getStatut() == Statut.PRETE
                    && statut == Statut.LIVREE)
                commande.setStatut(statut);
        else
            throw new CommandeException("Le statut doit être renseigné.");
    }

    /*
     * METHODES PRIVEES
     */

    private void incrementClientTotalAchat(Client client) {
        client.setTotalAchat(client.getTotalAchat() + 1);
        clientService.modifier(client.getId(), clientMapper.toClientRequestDTO(client));
    }

    private CommandeResponseDTO retourneCommandeResponseApresAjout(Client client, List<Pizza> listePizzas, Double prixTotal) {
        Commande commande = new Commande(client, listePizzas, Statut.EN_PREPARATION, prixTotal);
        Commande commandeRetourne = commandeDAO.save(commande);
        return commandeMapper.toCommandeResponseDTO(commandeRetourne);
    }

    private List<Pizza> trouverListePizza(List<PizzaCommandeRequestDTO> pizzaCommandeRequestDTOS) {
        List<Pizza> listePizza = new ArrayList<>();
        for (PizzaCommandeRequestDTO pizzaCommandeRequestDTO : pizzaCommandeRequestDTOS) {
            Pizza pizza = pizzaMapper.toPizzaFromResponse(pizzaService.findById(pizzaCommandeRequestDTO.pizza_id()));
            listePizza.add(pizza);
        }
        return listePizza;
    }

    private void verifierIngredients(Pizza pizza) {
        for (Ingredient ingredient : pizza.getListeIngredients()) {
            if (ingredient.getStock() <= 0)
                throw new IngredientException("L'ingrédient n'est pas en stock'");
            ingredient.setStock(ingredient.getStock() - 1);
            ingredientService.modifier(ingredient.getId(), ingredientMapper.toIngredientRequestDTO(ingredient));
        }
    }

    private Double reductionVIP(Double prixTotal) {
        return prixTotal -= (prixTotal / 100) * 10;
    }

    private Double calculPrixTotal(CommandeRequestDTO commandeRequestDTO) {
        Double prixTotal = 0.0;
        for (PizzaCommandeRequestDTO pizzaCommandeRequestDTO : commandeRequestDTO.listePizzaCommandeRequestDTOs()) {
            Pizza pizza = pizzaMapper.toPizzaFromResponse(pizzaService.findById(pizzaCommandeRequestDTO.pizza_id()));
            prixTotal += pizza.getTarifTaille().get(pizzaCommandeRequestDTO.taille());
        }
        return prixTotal;
    }

    private boolean verifierClientVIP(ClientRequestDTO clientRequestDTO) {
        return clientRequestDTO.totalAchat() >= 10;
    }

    private void validerCommande(CommandeRequestDTO commandeRequestDTO) {
        if (commandeRequestDTO == null)
            throw new CommandeException("La commande doit exister.");
        if (commandeRequestDTO.clientRequestDTO() == null)
            throw new CommandeException("Le client doit exister.");
        if (commandeRequestDTO.listePizzaCommandeRequestDTOs() == null)
            throw new CommandeException("La liste des pizzas doit exister.");
    }
}
