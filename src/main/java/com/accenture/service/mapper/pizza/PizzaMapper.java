package com.accenture.service.mapper.pizza;



import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.repository.entity.pizza.PizzaCommande;
import com.accenture.service.dto.pizza.PizzaCommandeRequestDTO;
import com.accenture.service.dto.pizza.PizzaRequestDto;
import com.accenture.service.dto.pizza.PizzaResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PizzaMapper {


    @Mapping(target = "listeIngredients" , ignore = true)
    Pizza toPizza(PizzaRequestDto pizzaRequestDto);
    @Mapping(target = "listeIngredients" , ignore = true)
    PizzaResponseDto toPizzaResponseDto(Pizza pizza);
    @Mapping(target = "listeIngredients" , ignore = true)
    Pizza toPizzaFromResponse(PizzaResponseDto pizzaResponseDto);

    PizzaCommande toPizzaCommande(PizzaCommandeRequestDTO pizzaCommandeRequestDTO);

}
