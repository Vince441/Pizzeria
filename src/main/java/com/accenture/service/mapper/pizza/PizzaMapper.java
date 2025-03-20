package com.accenture.service.mapper.pizza;


import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.service.dto.pizza.PizzaRequestDto;
import com.accenture.service.dto.pizza.PizzaResponseDto;
import com.accenture.shared.Taille;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface PizzaMapper {

    Pizza toPizza(PizzaRequestDto pizzaRequestDto);
    PizzaResponseDto toPizzaResponseDto(Pizza pizza);
    Pizza toPizzaFromResponse(PizzaResponseDto pizzaResponseDto);


}
