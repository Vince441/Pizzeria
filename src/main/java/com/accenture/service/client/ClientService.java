package com.accenture.service.client;

import com.accenture.exception.ClientException;

public interface ServiceClient {
    ClientResponseDTO ajouter(ClientRequestDTO clientRequestDTO) throws ClientException;
}
