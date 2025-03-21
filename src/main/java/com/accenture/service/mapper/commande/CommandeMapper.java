package com.accenture.service.mapper.commande;

import com.accenture.repository.entity.commande.Commande;
import com.accenture.service.dto.commande.CommandeRequestDTO;
import com.accenture.service.dto.commande.CommandeResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommandeMapper {
    Commande toCommande(CommandeRequestDTO commandeRequestDTO);
    CommandeResponseDTO toCommandeResponseDTO(Commande commande);
    CommandeRequestDTO toCommandeRequestDTO(Commande commande);
}
