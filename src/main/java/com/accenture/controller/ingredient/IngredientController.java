package com.accenture.controller.ingredient;

import com.accenture.service.dto.ingredient.IngredientRequestDTO;
import com.accenture.service.dto.ingredient.IngredientResponseDTO;
import com.accenture.service.ingredient.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    ResponseEntity<IngredientResponseDTO> ajouter(@RequestBody IngredientRequestDTO ingredientRequestDTO){
        IngredientResponseDTO ingredientResponseDTO = ingredientService.ajouter(ingredientRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientResponseDTO);
    }
}
