package com.accenture.service.pizza;

import com.accenture.exception.PizzaException;
import com.accenture.repository.dao.pizza.PizzaDao;
import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.service.dto.pizza.PizzaRequestDto;
import com.accenture.service.dto.pizza.PizzaResponseDto;
import com.accenture.service.mapper.PizzaMapper;
import jakarta.persistence.EntityNotFoundException;

public class PizzaServiceImpl implements PizzaService{

    private final PizzaDao pizzaDao;
    private final PizzaMapper pizzaMapper;



    public PizzaServiceImpl(PizzaDao pizzaDao, PizzaMapper pizzaMapper) {
        this.pizzaDao = pizzaDao;
        this.pizzaMapper = pizzaMapper;
    }


    @Override
    public PizzaResponseDto ajouter(PizzaRequestDto pizzaRequestDto) throws PizzaException {
        verificationPizza(pizzaRequestDto);

        Pizza pizza = pizzaMapper.toPizza(pizzaRequestDto);
        Pizza pizzaEnreg = pizzaDao.save(pizza);
        return pizzaMapper.toPizzaResponseDto(pizzaEnreg);
    }

    @Override
    public void supprimer(Integer id) throws PizzaException {
        Pizza pizza = pizzaDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Pizza non trouvé"));
        pizzaDao.delete(pizza);
    }


//Methode privée //

    private static void verificationPizza(PizzaRequestDto pizzaRequestDto) {
        if(pizzaRequestDto == null)
            throw new PizzaException("La pizza doit exister");
        if(pizzaRequestDto.nom() == null)
            throw new PizzaException("Le nom est obligatoire");
        if(pizzaRequestDto.nom().isBlank())
            throw new PizzaException("Le nom est obligatoire");
    }




}
