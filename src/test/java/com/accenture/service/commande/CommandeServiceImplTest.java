package com.accenture.service.commande;

import com.accenture.exception.CommandeException;
import com.accenture.repository.dao.commande.CommandeDAO;
import com.accenture.repository.entity.commande.Commande;
import com.accenture.service.mapper.commande.CommandeMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CommandeServiceImplTest {
    @Mock
    private CommandeDAO commandeDAO;
    @Mock
    private CommandeMapper commandeMapper;
    @InjectMocks
    private CommandeServiceImpl commandeService;

    @Test
    void testAjouterNull() {
        CommandeException commandeException = assertThrows(CommandeException.class, () -> commandeService.ajouter(null));
        assertEquals("La commande doit exister.", commandeException.getMessage());
    }

    @Test
    void testAjouterSuccess() {
        Commande commande = creerCommande();
    }

    /*
     * METHODES PRIVEES
     */

    private Commande creerCommande() {
        return new Commande();
    }
}
