package com.accenture.service.pizza;

import com.accenture.exception.PizzaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class testPizzaServiceImplTest {

    @InjectMocks
    private PizzaServiceImpl service;



@Test
    void testAjouterPizzaNull(){
    PizzaException pe = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(null));
    Assertions.assertEquals("La pizza doit exister", pe.getMessage());

}



}
