package com.accenture.service.mapper.ingredient;

import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.service.dto.ingredient.IngredientRequestDTO;
import com.accenture.service.dto.ingredient.IngredientResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IngredientMapper {


    Ingredient toIngredient(IngredientRequestDTO ingredientRequestDTO);
    IngredientResponseDTO toIngredientResponseDTO(Ingredient ingredient);
    IngredientRequestDTO toIngredientRequestDTO(Ingredient ingredient);





}
