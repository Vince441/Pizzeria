package com.accenture.service.client;

import com.accenture.exception.ClientException;
import com.accenture.service.dto.client.ClientRequestDTO;
import com.accenture.service.dto.client.ClientResponseDTO;
import jakarta.persistence.EntityNotFoundException;

public interface ClientService {
    ClientResponseDTO ajouter(ClientRequestDTO clientRequestDTO) throws ClientException;
    void supprimer(int id) throws EntityNotFoundException;
}
