package com.accenture.controller.pizza;

import com.accenture.service.dto.pizza.PizzaRequestDto;
import com.accenture.service.dto.pizza.PizzaResponseDto;
import com.accenture.service.pizza.PizzaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pizza")
public class PizzaController {

    private final PizzaService pizzaService;


    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }
        @PostMapping
        ResponseEntity<PizzaResponseDto> ajouter(@RequestBody PizzaRequestDto pizzaRequestDto){
            PizzaResponseDto pizzaResponseDto = pizzaService.ajouter(pizzaRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(pizzaResponseDto);

        }


    }



