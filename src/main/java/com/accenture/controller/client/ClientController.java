package com.accenture.controller.client;

import com.accenture.service.client.ClientService;
import com.accenture.service.dto.client.ClientRequestDTO;
import com.accenture.service.dto.client.ClientResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    ResponseEntity<List<ClientResponseDTO>> trouverTous(){
        return ResponseEntity.ok(clientService.trouverTous());
    }

    @GetMapping("/{id}")
    ResponseEntity<ClientResponseDTO> trouver(@PathVariable("id") int id) {
        ClientResponseDTO clientResponseDTO = clientService.trouver(id);
        return ResponseEntity.ok(clientResponseDTO);
    }




}
