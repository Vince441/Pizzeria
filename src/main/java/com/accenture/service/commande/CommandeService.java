package com.accenture.service.commande;

import com.accenture.exception.CommandeException;
import com.accenture.repository.entity.commande.Commande;
import com.accenture.service.dto.commande.CommandeResponseDTO;

public interface CommandeService {
    CommandeResponseDTO ajouter(Commande commande) throws CommandeException;
}
