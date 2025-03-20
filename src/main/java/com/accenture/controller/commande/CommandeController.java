package com.accenture.controller.commande;

import com.accenture.service.commande.CommandeService;
import com.accenture.service.dto.commande.CommandeRequestDTO;
import com.accenture.service.dto.commande.CommandeResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commandes")
public class CommandeController {
    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @PostMapping
    ResponseEntity<CommandeResponseDTO> ajouter(@RequestBody CommandeRequestDTO commandeRequestDTO) {
        System.out.println("CommandeRequestDTO = " + commandeRequestDTO);
        CommandeResponseDTO commandeResponseDTO = commandeService.ajouter(commandeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(commandeResponseDTO);
    }
}
