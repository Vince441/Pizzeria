package com.accenture.service.commande;

import com.accenture.exception.CommandeException;
import com.accenture.service.dto.commande.CommandeRequestDTO;
import com.accenture.service.dto.commande.CommandeResponseDTO;

public interface CommandeService {
    CommandeResponseDTO ajouter(CommandeRequestDTO commandeRequestDTO) throws CommandeException;
}
