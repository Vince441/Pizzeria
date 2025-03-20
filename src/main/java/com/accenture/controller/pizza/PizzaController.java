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
@RequestMapping("/pizzas")
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

    @GetMapping
    public List<Pizza> trouverTous() {
        return pizzaService.trouverTous();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PizzaResponseDto> trouverParId(@PathVariable int id){
        PizzaResponseDto trouve = pizzaService.findById(id);
        return ResponseEntity.ok(trouve);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<PizzaResponseDto> supprimerPizza(@PathVariable("id") int id) {
        pizzaService.supprimer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<PizzaResponseDto> trouverParNom(@PathVariable("nom") String nom){
       PizzaResponseDto trouve = pizzaService.findByNom(nom);
       return ResponseEntity.ok(trouve);
    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<PizzaResponseDto> modifierPartiellement(@PathVariable("id") int id, PizzaRequestDto pizzaRequestDto){
        PizzaResponseDto trouve = pizzaService.modifierPartiellement(id, pizzaRequestDto);
        return ResponseEntity.ok(trouve);
    }

}



