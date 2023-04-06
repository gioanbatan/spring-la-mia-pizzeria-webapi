package com.experis.pizza.service;

import com.experis.pizza.model.Pizza;
import com.experis.pizza.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PizzaService {

    @Autowired
    PizzaRepository pizzaRepository;

    public Pizza createPizza(Pizza formPizza) {
        Pizza pizzaToPersist = setAllData(formPizza);

        return pizzaRepository.save(pizzaToPersist);
    }

    public Pizza updatePizza(Pizza formPizza, Integer id) {
        Pizza pizzaToUpdate = getById(id);

        pizzaToUpdate = setAllData(formPizza);

        return pizzaRepository.save(pizzaToUpdate);
    }

    public Pizza getById(Integer id) throws RuntimeException {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RuntimeException(); // ADD CUSTOM EXCEPTIONS
        }
    }

    private Pizza setAllData(Pizza dataPizza) {
        Pizza pizzaReturn = new Pizza();
        if (dataPizza.getId() != null) {
            pizzaReturn.setId(dataPizza.getId());
        }
        pizzaReturn.setName(dataPizza.getName());
        pizzaReturn.setPrice(dataPizza.getPrice());
        pizzaReturn.setDescription(dataPizza.getDescription());
        pizzaReturn.setIngredients(dataPizza.getIngredients());

        return pizzaReturn;
    }
}
