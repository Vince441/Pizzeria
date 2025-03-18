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
            ", Elian, elian@mail.fr, Le nom doit être renseigné.",
            "'', Elian, elian@mail.fr, Le nom doit être renseigné.",
            "THEBAULT, , elian@mail.fr, Le prénom doit être renseigné.",
            "THEBAULT, '', elian@mail.fr, Le prénom doit être renseigné.",
            "THEBAULT, Elian, , L'email doit être renseigné.",
            "THEBAULT, Elian, '', L'email doit être renseigné."
    })
    void ajouterFail(String nom, String prenom, String email, String expected) {
        ClientRequestDTO clientRequestDTO = new ClientRequestDTO(nom, prenom, email);
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
    void testTrouver() {
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> clientService.trouver(44));
        assertEquals("Le client n'existe pas.", ex.getMessage());
    }

    /*
     * METHODES PRIVEES
     */

    private ClientResponseDTO creerClientResponseDTO() {
        return new ClientResponseDTO(
                1,
                "THEBAULT",
                "Elian",
                "elian@mail.fr"
        );
    }

    private Client creerClient() {
        return new Client(
                "THEBAULT",
                "Elian",
                "elian@mail.com"
        );
    }

    private ClientRequestDTO creerClientRequestDTO() {
        return new ClientRequestDTO("THEBAULT", "Elian", "elian@mail.fr");
    }

}
