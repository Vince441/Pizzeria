package com.accenture.service.pizza;

import com.accenture.exception.PizzaException;
import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.service.dto.pizza.PizzaRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class testPizzaServiceImplTest {

    @InjectMocks
    private PizzaServiceImpl service;



@Test
    void testAjouterPizzaNull(){
    PizzaException pe = assertThrows(PizzaException.class, () -> service.ajouter(null));
    Assertions.assertEquals("La pizza doit exister", pe.getMessage());
}

@Test
    void testAjouterPizzaNomNull() {
    PizzaRequestDto dto = getPizzaRequestDto();
    PizzaException pe = assertThrows(PizzaException.class, () -> service.ajouter(dto));
Assertions.assertEquals("Le nom est obligatoire", pe.getMessage());
}

@Test






private static PizzaRequestDto getPizzaRequestDto() {
        PizzaRequestDto dto = new PizzaRequestDto(1, null);
        return dto;
    }


}
