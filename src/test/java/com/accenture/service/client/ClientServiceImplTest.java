package com.accenture.service.client;

import com.accenture.exception.ClientException;
import com.accenture.repository.dao.client.ClientDAO;
import com.accenture.repository.entity.client.Client;
import com.accenture.service.dto.client.ClientRequestDTO;
import com.accenture.service.dto.client.ClientResponseDTO;
import com.accenture.service.mapper.client.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
    @Mock
    private ClientDAO clientDAO;
    @Mock
    private ClientMapper clientMapper;
    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void testClientNull() {
        ClientException clientException = assertThrows(ClientException.class, () -> clientService.ajouter(null));
        assertEquals("Le client doit exister.", clientException.getMessage());
    }

    @ParameterizedTest
    @CsvSource(value = {
            ", Elian, elian@mail.fr, 0, Le nom doit être renseigné.",
            "'', Elian, elian@mail.fr, 0, Le nom doit être renseigné.",
            "THEBAULT, , elian@mail.fr, 0, Le prénom doit être renseigné.",
            "THEBAULT, '', elian@mail.fr, 0, Le prénom doit être renseigné.",
            "THEBAULT, Elian, , 0, L'email doit être renseigné.",
            "THEBAULT, Elian, '', 0, L'email doit être renseigné.",
            "THEBAULT, Elian, elian@mail.fr, , Le total des achats doit être renseigné.",
            "THEBAULT, Elian, elian@mail.fr, -5, Le total des achats ne peut être négatif."
    })
    void ajouterFail(String nom, String prenom, String email, Integer totalAchat, String expected) {
        ClientRequestDTO clientRequestDTO = new ClientRequestDTO(nom, prenom, email, totalAchat);
        ClientException clientException = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDTO));
        assertEquals(expected, clientException.getMessage());
    }

    @Test
    void ajouterSuccess() {
        ClientRequestDTO clientRequestDTO = creerClientRequestDTO();
        Client client = creerClient();
        Client returnedClient = creerClient();
        returnedClient.setId(1);
        ClientResponseDTO clientResponseDTO = creerClientResponseDTO();
        when(clientMapper.toClient(clientRequestDTO)).thenReturn(client);
        when(clientDAO.save(client)).thenReturn(returnedClient);
        when(clientMapper.toClientResponseDTO(returnedClient)).thenReturn(clientResponseDTO);
        assertEquals(clientResponseDTO, clientService.ajouter(clientRequestDTO));
        verify(clientDAO).save(client);
    }

    @Test
    void supprimerFail() {
        when(clientDAO.existsById(44)).thenReturn(false);
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> clientService.supprimer(44));
        assertEquals("Le client n'existe pas.", ex.getMessage());
        verify(clientDAO).existsById(44);
    }

    @Test
    void supprimerSuccess() {
        Mockito.when(clientDAO.existsById(44)).thenReturn(true);
        assertDoesNotThrow(() -> clientService.supprimer(44));
        Mockito.verify(clientDAO).existsById(44);
        Mockito.verify(clientDAO).deleteById(44);
    }

    @Test
    void testTrouverFail() {
        when(clientDAO.existsById(44)).thenReturn(false);
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> clientService.trouver(44));
        assertEquals("Le client n'existe pas.", ex.getMessage());
    }

    @Test
    void testTrouverSuccess() {
        ClientResponseDTO clientResponseDTO = creerClientResponseDTO();
        Optional<Client> optionalClient = Optional.of(creerClient());
        when(clientDAO.existsById(1)).thenReturn(true);
        when(clientDAO.findById(1)).thenReturn(optionalClient);
        when(clientService.trouver(1)).thenReturn(clientResponseDTO);
        assertEquals(clientService.trouver(1), clientResponseDTO);
    }

    /*
     * METHODES PRIVEES
     */

    private ClientResponseDTO creerClientResponseDTO() {
        return new ClientResponseDTO(
                1,
                "THEBAULT",
                "Elian",
                "elian@mail.fr",
                0
        );
    }

    private Client creerClient() {
        return new Client(
                "THEBAULT",
                "Elian",
                "elian@mail.com",
                0
        );
    }

    private ClientRequestDTO creerClientRequestDTO() {
        return new ClientRequestDTO("THEBAULT", "Elian", "elian@mail.fr", 0);
    }

}
