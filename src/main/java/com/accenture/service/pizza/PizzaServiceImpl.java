package com.accenture.service.pizza;

import com.accenture.exception.PizzaException;
import com.accenture.repository.dao.ingredient.IngredientDAO;
import com.accenture.repository.dao.pizza.PizzaDao;
import com.accenture.repository.entity.ingredient.Ingredient;
import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.service.dto.pizza.PizzaRequestDto;
import com.accenture.service.dto.pizza.PizzaResponseDto;
import com.accenture.service.mapper.pizza.PizzaMapper;
import com.accenture.shared.Taille;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PizzaServiceImpl implements PizzaService {

    public static final String ID_NON_CONNU = "Id non connu";
    public static final String NOM_INTROUVABLE = "Nom introuvable";
    private final PizzaDao pizzaDao;
    private final PizzaMapper pizzaMapper;
    private final IngredientDAO ingredientDAO;

    public PizzaServiceImpl(PizzaDao pizzaDao, PizzaMapper pizzaMapper, IngredientDAO ingredientDAO) {
        this.pizzaDao = pizzaDao;
        this.pizzaMapper = pizzaMapper;
        this.ingredientDAO = ingredientDAO;
    }


    @Override
    public PizzaResponseDto ajouter(PizzaRequestDto pizzaRequestDto) throws PizzaException {
        verificationPizza(pizzaRequestDto);

        String nom = pizzaRequestDto.nom();
        List<Ingredient> ingredients = ingredientDAO.findAllById(pizzaRequestDto.listeIngredients());

        // Créer la carte des tarifs
        Map<Taille, Double> tarifMap = pizzaRequestDto.tarifTaille();

        // Créer la pizza
        Pizza pizza = new Pizza(nom, tarifMap, ingredients);

        // Sauvegarder la pizza dans la base de données
        Pizza pizzaEnreg = pizzaDao.save(pizza);

        // Convertir les ingrédients en noms
        List<String> ingredientNames = pizzaEnreg.getListeIngredients()
                .stream()
                .map(Ingredient::getNom)  // Extraire les noms des ingrédients
                .collect(Collectors.toList());

        // Retourner une réponse avec l'ID de la pizza, son nom, les tarifs et les noms des ingrédients
        return new PizzaResponseDto(pizzaEnreg.getId(), pizzaEnreg.getNom(), tarifMap, ingredientNames);
    }


    @Override
    public void supprimer(Integer id) throws PizzaException {
        Pizza pizza = pizzaDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Pizza non trouvé"));
        pizzaDao.delete(pizza);
    }


    @Override
    public PizzaResponseDto modifierPartiellement(int id, PizzaRequestDto pizzaRequestDto) throws PizzaException {

    }

    @Override
    public List<Pizza> trouverTous() {
        return pizzaDao.findAll();
    }

    @Override
    public PizzaResponseDto findById(int id) {
        Optional<Pizza> optPizza = pizzaDao.findById(id);
        if (optPizza.isEmpty())
            throw new EntityNotFoundException(ID_NON_CONNU);
        Pizza pizza = optPizza.get();
        return pizzaMapper.toPizzaResponseDto(pizza);
    }

    @Override
    public PizzaResponseDto findByNom(String nom) {
        Optional<Pizza> optPizza = pizzaDao.findByNom(nom);
        if (optPizza.isEmpty())
            throw new EntityNotFoundException("Je n'ai pas trouver le nom");
        Pizza pizza = optPizza.get();
        return pizzaMapper.toPizzaResponseDto(pizza);
    }




//Methode privée //

    private static void verificationPizza(PizzaRequestDto pizzaRequestDto) {
        if (pizzaRequestDto == null)
            throw new PizzaException("La pizza doit exister");
        if (pizzaRequestDto.nom() == null)
            throw new PizzaException("Le nom est obligatoire");
        if (pizzaRequestDto.nom().isBlank())
            throw new PizzaException("Le nom est obligatoire");
        if (pizzaRequestDto.tarifTaille() == null || pizzaRequestDto.tarifTaille().containsKey(null))
            throw new PizzaException("La taille est obligatoire");
    }


}
