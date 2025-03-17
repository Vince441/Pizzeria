package com.accenture.service.pizza;

import com.accenture.exception.PizzaException;
import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.service.dto.pizza.PizzaRequestDto;
import com.accenture.service.dto.pizza.PizzaResponseDto;

public interface PizzaService {

    PizzaResponseDto ajouter(PizzaRequestDto pizzaRequestDto) throws PizzaException;


    void supprimer(Integer id) throws PizzaException;

    PizzaResponseDto modifierPartiellement(int id, PizzaRequestDto pizzaRequestDto) throws PizzaException;


}
