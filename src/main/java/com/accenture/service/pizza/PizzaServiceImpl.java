package com.accenture.service.pizza;

import com.accenture.exception.PizzaException;
import com.accenture.repository.dao.pizza.PizzaDao;
import com.accenture.repository.entity.pizza.Pizza;

public class PizzaServiceImpl implements PizzaService{

    private final PizzaDao pizzaDao;

    public PizzaServiceImpl(PizzaDao pizzaDao) {
        this.pizzaDao = pizzaDao;
    }


    @Override
    public Pizza ajouter(Pizza pizza) throws PizzaException {
        if(pizza == null)
            throw new PizzaException("La pizza doit exister");
        return pizzaDao.save(pizza);
    }
}
