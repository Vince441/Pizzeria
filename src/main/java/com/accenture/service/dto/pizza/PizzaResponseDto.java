package com.accenture.service.dto.pizza;

import com.accenture.shared.Taille;

import java.util.HashMap;

public record  PizzaResponseDto(

        String nom,
        HashMap<Taille, Double> tarifTaille


) {
}
