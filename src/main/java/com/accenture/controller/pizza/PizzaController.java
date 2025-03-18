package com.accenture.controller.pizza;

import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.service.dto.pizza.PizzaRequestDto;
import com.accenture.service.dto.pizza.PizzaResponseDto;
import com.accenture.service.pizza.PizzaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pizza")
public class PizzaController {

    private final PizzaService pizzaService;


    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @PostMapping
    ResponseEntity<PizzaResponseDto> ajouter(@RequestBody PizzaRequestDto pizzaRequestDto) {
        PizzaResponseDto pizzaResponseDto = pizzaService.ajouter(pizzaRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pizzaResponseDto);

    }

    @GetMapping("/liste")
    public List<Pizza> trouverTous() {
        return pizzaService.trouverTous();
    }

}



