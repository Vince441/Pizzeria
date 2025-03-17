package com.accenture.controller.client;

import com.accenture.service.client.ClientService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {
    @Mock
    ClientService clientService;
    @InjectMocks
    ClientController clientController;
}
