package com.accenture.service.pizza;

import com.accenture.exception.PizzaException;
import com.accenture.repository.entity.pizza.Pizza;

public interface PizzaService {

    Pizza ajouter(Pizza pizza) throws PizzaException;



}
