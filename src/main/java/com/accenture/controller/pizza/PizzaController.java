package com.accenture.controller.pizza;

import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.service.dto.ingredient.IngredientRequestDTO;
import com.accenture.service.dto.pizza.PizzaRequestDto;
import com.accenture.service.dto.pizza.PizzaResponseDto;
import com.accenture.service.mapper.pizza.PizzaMapper;
import com.accenture.service.pizza.PizzaService;
import com.accenture.shared.Taille;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pizzas")
public class PizzaController {

    private final PizzaService pizzaService;


    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @PostMapping
    ResponseEntity<PizzaResponseDto> ajouter(

            @Parameter(description = "Nom de la pizza") @RequestParam(required = true) String nom,
            @Parameter(description = "Tarif de la pizza pour PETITE taille") @RequestParam(required = true) Double petiteTarif,
            @Parameter(description = "Tarif de la pizza pour MOYENNE taille") @RequestParam(required = true) Double moyenneTarif,
            @Parameter(description = "Tarif de la pizza pour GRANDE taille") @RequestParam(required = true) Double grandeTarif,
            @Parameter(description = "Liste des ingrédients de la pizza") @RequestParam(required = true) List<Integer> list
    ) {

        // Créez la carte tarif
        Map<Taille, Double> tarif = new HashMap<>();

        tarif.put(Taille.PETITE, petiteTarif);
        tarif.put(Taille.MOYENNE, moyenneTarif);
        tarif.put(Taille.GRANDE, grandeTarif);
        PizzaRequestDto pizzaRequestDto = new PizzaRequestDto(nom, tarif, list);

        pizzaService.ajouter(pizzaRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Pizza> trouverTous() {
        return pizzaService.trouverTous();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PizzaResponseDto> trouverParId(@PathVariable int id) {
        PizzaResponseDto trouve = pizzaService.findById(id);
        return ResponseEntity.ok(trouve);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<PizzaResponseDto> supprimerPizza(@PathVariable("id") int id) {
        pizzaService.supprimer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<PizzaResponseDto> trouverParNom(@PathVariable("nom") String nom) {
        PizzaResponseDto trouve = pizzaService.findByNom(nom);
        return ResponseEntity.ok(trouve);
    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<PizzaResponseDto> modifierPartiellement(
            @PathVariable("id") int id,
            @Parameter(description = "Liste des ingrédients de la pizza")
            @RequestParam(required = true) List<Integer> list) {  // Ajouter l'annotation @RequestBody

        // Appel du service pour modifier partiellement la pizza
        PizzaResponseDto pizzaModifiee = pizzaService.modifierPartiellement(id, list);

        // Retourne la pizza modifiée dans la réponse HTTP
        return ResponseEntity.ok(pizzaModifiee);
    }

}



