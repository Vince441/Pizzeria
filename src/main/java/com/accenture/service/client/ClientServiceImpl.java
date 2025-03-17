package com.accenture.service.client;

import com.accenture.exception.ClientException;
import com.accenture.repository.dao.client.ClientDAO;
import com.accenture.repository.entity.client.Client;
import com.accenture.service.dto.client.ClientRequestDTO;
import com.accenture.service.dto.client.ClientResponseDTO;
import com.accenture.service.mapper.client.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientDAO clientDAO;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientDAO clientDAO, ClientMapper clientMapper) {
        this.clientDAO = clientDAO;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientResponseDTO ajouter(ClientRequestDTO clientRequestDTO) throws ClientException {
        validerClient(clientRequestDTO);
        return retourneClientResponseApresAjout(clientRequestDTO);
    }

    @Override
    public void supprimer(int id) throws EntityNotFoundException {
        if (clientDAO.existsById(id))
            clientDAO.deleteById(id);
        else
            throw new EntityNotFoundException("Le client n'existe pas.");
    }

    /*
     * METHODES PRIVEES
     */

    private static void validerClient(ClientRequestDTO clientRequestDTO) {
        if (clientRequestDTO == null)
            throw new ClientException("Le client doit exister.");
        if (clientRequestDTO.nom() == null
                || clientRequestDTO.nom().isBlank())
            throw new ClientException("Le nom doit être renseigné.");
        if (clientRequestDTO.prenom() == null
                || clientRequestDTO.prenom().isBlank())
            throw new ClientException("Le prénom doit être renseigné.");
        if (clientRequestDTO.email() == null
                || clientRequestDTO.email().isBlank())
            throw new ClientException("L'email doit être renseigné.");
    }

    private ClientResponseDTO retourneClientResponseApresAjout(ClientRequestDTO clientRequestDTO) {
        Client client = clientMapper.toClient(clientRequestDTO);
        Client clientAjoute = clientDAO.save(client);
        return clientMapper.toClientResponseDTO(clientAjoute);
    }
}
