package com.accenture.service.mapper.pizza;


import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.service.dto.pizza.PizzaRequestDTO;
import com.accenture.service.dto.pizza.PizzaResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PizzaMapper {

    Pizza toPizza(PizzaRequestDTO pizzaRequestDto);

    PizzaResponseDTO toPizzaResponseDto(Pizza pizza);

}
