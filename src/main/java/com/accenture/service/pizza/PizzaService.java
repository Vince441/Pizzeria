package com.accenture.service.pizza;

import com.accenture.exception.PizzaException;
import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.service.dto.pizza.PizzaRequestDto;
import com.accenture.service.dto.pizza.PizzaResponseDto;

import java.util.List;

public interface PizzaService {
    PizzaResponseDto ajouter(PizzaRequestDto pizzaRequestDto) throws PizzaException;
    void supprimer(Integer id) throws PizzaException;
    PizzaResponseDto modifierPartiellement(int id, PizzaRequestDto pizzaRequestDto) throws PizzaException;
    List<Pizza> trouverTous() throws PizzaException;
    PizzaResponseDto findById(int id) throws PizzaException;
    PizzaResponseDto findByNom(String nom) throws PizzaException;
}

