package com.accenture.service.dto.pizza;

import com.accenture.shared.Taille;

import java.util.HashMap;
import java.util.Map;

public record  PizzaResponseDto(

        String nom,
        Map<Taille, Double> tarifTaille


) {
}
