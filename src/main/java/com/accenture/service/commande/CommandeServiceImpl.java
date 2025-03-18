package com.accenture.service.commande;

import com.accenture.exception.CommandeException;
import com.accenture.repository.dao.commande.CommandeDAO;
import com.accenture.repository.entity.commande.Commande;
import com.accenture.service.dto.commande.CommandeResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class CommandeServiceImpl implements CommandeService {
    private final CommandeDAO commandeDAO;

    public CommandeServiceImpl(CommandeDAO commandeDAO) {
        this.commandeDAO = commandeDAO;
    }

    @Override
    public CommandeResponseDTO ajouter(Commande commande) throws CommandeException {
        if (commande == null)
            throw new CommandeException("La commande doit exister.");
        return null;
    }
}
