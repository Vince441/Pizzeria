package com.accenture.service.mapper.commande;

import com.accenture.repository.entity.commande.Commande;
import com.accenture.service.dto.commande.CommandeRequestDTO;
import com.accenture.service.dto.commande.CommandeResponseDTO;
import com.accenture.service.mapper.client.ClientMapper;
import com.accenture.service.mapper.pizza.PizzaMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring)", uses = {ClientMapper.class, PizzaMapper.class})
public interface CommandeMapper {
    Commande toCommande(CommandeRequestDTO commandeRequestDTO);
    CommandeResponseDTO toCommandeResponseDTO(Commande commande);
    CommandeRequestDTO toCommandeRequestDTO(Commande commande);
}
