package com.accenture.service.mapper.client;

import com.accenture.repository.entity.client.Client;
import com.accenture.service.dto.client.ClientRequestDTO;
import com.accenture.service.dto.client.ClientResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toClient(ClientRequestDTO clientRequestDTO);
    ClientResponseDTO toClientResponseDTO(Client client);
    ClientRequestDTO toClientRequestDTO(Client client);
}
