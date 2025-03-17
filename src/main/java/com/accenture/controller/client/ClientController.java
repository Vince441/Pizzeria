package com.accenture.controller.client;

import com.accenture.repository.entity.client.Client;
import com.accenture.service.client.ClientService;
import com.accenture.service.dto.client.ClientRequestDTO;
import com.accenture.service.dto.client.ClientResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    ResponseEntity<ClientResponseDTO> ajouter(@RequestBody ClientRequestDTO clientRequestDTO){
        ClientResponseDTO clientResponseDTO = clientService.ajouter(clientRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientResponseDTO);
    }
}
