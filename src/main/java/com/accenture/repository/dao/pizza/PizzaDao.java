package com.accenture.repository.dao.pizza;

import com.accenture.repository.entity.pizza.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PizzaDao extends JpaRepository<Pizza, Integer> {
    Optional<Pizza> findByNom(String nom);
}
