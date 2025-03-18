package com.accenture.service.pizza;

import com.accenture.exception.PizzaException;
import com.accenture.repository.dao.pizza.PizzaDao;
import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.service.dto.pizza.PizzaRequestDto;
import com.accenture.service.dto.pizza.PizzaResponseDto;
import com.accenture.service.mapper.pizza.PizzaMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaServiceImpl implements PizzaService {

    public static final String ID_NON_CONNU = "Id non connu";
    public static final String NOM_INTROUVABLE = "Nom introuvable";
    private final PizzaDao pizzaDao;
    private final PizzaMapper pizzaMapper;


    public PizzaServiceImpl(PizzaDao pizzaDao, PizzaMapper pizzaMapper) {
        this.pizzaDao = pizzaDao;
        this.pizzaMapper = pizzaMapper;
    }


    @Override
    public PizzaResponseDto ajouter(PizzaRequestDto pizzaRequestDto) throws PizzaException {
        verificationPizza(pizzaRequestDto);

        Pizza pizza = pizzaMapper.toPizza(pizzaRequestDto);
        Pizza pizzaEnreg = pizzaDao.save(pizza);
        return pizzaMapper.toPizzaResponseDto(pizzaEnreg);
    }

    @Override
    public void supprimer(Integer id) throws PizzaException {
        Pizza pizza = pizzaDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Pizza non trouvé"));
        pizzaDao.delete(pizza);
    }


    @Override
    public PizzaResponseDto modifierPartiellement(int id, PizzaRequestDto pizzaRequestDto) throws PizzaException {
        Optional<Pizza> optPizza = pizzaDao.findById(id);
        if (optPizza.isEmpty())
            throw new PizzaException("Je n'ai pas trouvé la pizza");

        Pizza pizzaExistante = optPizza.get();
        Pizza pizzaEnreg = pizzaMapper.toPizza(pizzaRequestDto);

        remplacer(pizzaExistante, pizzaEnreg);
        Pizza modifPizza = pizzaDao.save(pizzaExistante);
        return pizzaMapper.toPizzaResponseDto(modifPizza);


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
        return pizzaMapper.toPizzaResponseDto((pizza));
    }

    @Override
    public PizzaResponseDto findByNom(String nom) {
        Optional<Pizza> optPizza = pizzaDao.findByNom(nom);
        if (optPizza.isEmpty())
            throw new EntityNotFoundException("Je n'ai pas trouver le nom");
        Pizza pizza = optPizza.get();
        return pizzaMapper.toPizzaResponseDto((pizza));
    }

    private void remplacer(Pizza pizzaExistante, Pizza pizzaEnreg) {
        if (pizzaExistante.getNom() == null)
            pizzaExistante.setNom(pizzaEnreg.getNom());
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
