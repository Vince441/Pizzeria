package com.accenture.service.pizza;

import com.accenture.exception.PizzaException;
import com.accenture.repository.dao.pizza.PizzaDao;
import com.accenture.repository.entity.pizza.Pizza;
import com.accenture.service.dto.pizza.PizzaRequestDTO;
import com.accenture.service.dto.pizza.PizzaResponseDTO;
import com.accenture.service.mapper.pizza.PizzaMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PizzaServiceImpl implements PizzaService {

    private final PizzaDao pizzaDao;
    private final PizzaMapper pizzaMapper;


    public PizzaServiceImpl(PizzaDao pizzaDao, PizzaMapper pizzaMapper) {
        this.pizzaDao = pizzaDao;
        this.pizzaMapper = pizzaMapper;
    }


    @Override
    public PizzaResponseDTO ajouter(PizzaRequestDTO pizzaRequestDto) throws PizzaException {
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
    public PizzaResponseDTO modifierPartiellement(int id, PizzaRequestDTO pizzaRequestDto) throws PizzaException {
        Optional<Pizza> optPizza = pizzaDao.findById(id);
        if (optPizza.isEmpty())
            throw new PizzaException("Je n'ai pas trouvé la pizza");

        Pizza pizzaExistante = optPizza.get();
        Pizza pizzaEnreg = pizzaMapper.toPizza(pizzaRequestDto);

        remplacer(pizzaExistante, pizzaEnreg);
        Pizza modifPizza = pizzaDao.save(pizzaExistante);
        return pizzaMapper.toPizzaResponseDto(modifPizza);


    }

    private void remplacer(Pizza pizzaExistante, Pizza pizzaEnreg) {
        if (pizzaExistante.getNom()== null)
            pizzaExistante.setNom(pizzaEnreg.getNom());
    }


//Methode privée //

    private static void verificationPizza(PizzaRequestDTO pizzaRequestDto) {
        if (pizzaRequestDto == null)
            throw new PizzaException("La pizza doit exister");
        if (pizzaRequestDto.nom() == null)
            throw new PizzaException("Le nom est obligatoire");
        if (pizzaRequestDto.nom().isBlank())
            throw new PizzaException("Le nom est obligatoire");
        if(pizzaRequestDto.tarifTaille() == null || pizzaRequestDto.tarifTaille().containsKey(null))
            throw new PizzaException("La taille est obligatoire");
    }


}
