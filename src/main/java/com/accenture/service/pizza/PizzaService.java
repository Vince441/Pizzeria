package com.accenture.service.pizza;

import com.accenture.exception.PizzaException;
import com.accenture.service.dto.pizza.PizzaRequestDTO;
import com.accenture.service.dto.pizza.PizzaResponseDTO;

public interface PizzaService {

    PizzaResponseDTO ajouter(PizzaRequestDTO pizzaRequestDto) throws PizzaException;


    void supprimer(Integer id) throws PizzaException;

    PizzaResponseDTO modifierPartiellement(int id, PizzaRequestDTO pizzaRequestDto) throws PizzaException;


}
