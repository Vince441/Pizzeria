package com.accenture.service.commande;

import com.accenture.exception.CommandeException;
import com.accenture.service.dto.commande.CommandeRequestDTO;
import com.accenture.service.dto.commande.CommandeResponseDTO;
import com.accenture.shared.Statut;
import jakarta.persistence.EntityNotFoundException;

public interface CommandeService {
    CommandeResponseDTO ajouter(CommandeRequestDTO commandeRequestDTO) throws CommandeException;
    CommandeResponseDTO modifier(int id, Statut statut) throws EntityNotFoundException, CommandeException;
}
