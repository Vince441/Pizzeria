package com.accenture.service.commande;

import com.accenture.exception.CommandeException;
import com.accenture.repository.dao.commande.CommandeDAO;
import com.accenture.repository.entity.commande.Commande;
import com.accenture.service.dto.commande.CommandeRequestDTO;
import com.accenture.service.dto.commande.CommandeResponseDTO;
import com.accenture.service.mapper.commande.CommandeMapper;
import org.springframework.stereotype.Service;

@Service
public class CommandeServiceImpl implements CommandeService {
    private final CommandeDAO commandeDAO;
    private final CommandeMapper commandeMapper;

    public CommandeServiceImpl(CommandeDAO commandeDAO, CommandeMapper commandeMapper) {
        this.commandeDAO = commandeDAO;
        this.commandeMapper = commandeMapper;
    }

    @Override
    public CommandeResponseDTO ajouter(CommandeRequestDTO commandeRequestDTO) throws CommandeException {
        validerCommande(commandeRequestDTO);
        return retourneCommandeResponseApresAjout(commandeRequestDTO);
    }

    private static void validerCommande(CommandeRequestDTO commandeRequestDTO) {
        if (commandeRequestDTO == null)
            throw new CommandeException("La commande doit exister.");
        if (commandeRequestDTO.clientRequestDTO() == null)
            throw new CommandeException("Le client doit exister.");
        if (commandeRequestDTO.listePizzaRequestDTOs() == null)
            throw new CommandeException("La liste des pizzas doit exister.");
        if (commandeRequestDTO.prixTotal() == null)
            throw new CommandeException("Le prix total doit exister.");
        if (commandeRequestDTO.prixTotal() < 0)
            throw new CommandeException("Le prix total doit être positif.");
    }

    private CommandeResponseDTO retourneCommandeResponseApresAjout(CommandeRequestDTO commandeRequestDTO) {
        Commande commande = commandeMapper.toCommande(commandeRequestDTO);
        Commande commandeAjoute = commandeDAO.save(commande);
        return commandeMapper.toCommandeResponseDTO(commandeAjoute);
    }
}
