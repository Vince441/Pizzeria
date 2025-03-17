package com.accenture.service.client;

import com.accenture.exception.ClientException;
import com.accenture.repository.client.ClientDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ServiceClientImplTest {
    @Mock
    private ClientDAO clientDAO;
    @InjectMocks
    private ServiceClient serviceClient;

    @Test
    void testClientNull() {
        ClientException clientException = assertThrows(ClientException.class, () -> serviceClient.ajouter(null));
        assertEquals("Le client doit exister.", clientException.getMessage());
    }
}
