package com.accenture.repository.dao.ingredient;

import com.accenture.repository.entity.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientDAO extends JpaRepository<Ingredient, Integer> {

}
