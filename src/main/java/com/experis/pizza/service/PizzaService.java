package com.experis.pizza.service;

import com.experis.pizza.exceptions.PizzaNotFoundException;
import com.experis.pizza.model.Pizza;
import com.experis.pizza.repository.PizzaRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {

    @Autowired
    PizzaRepository pizzaRepository;

    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll(Sort.by("name"));
    }

    public List<Pizza> getFilteredPizzas(String keyword) {
        return pizzaRepository.findByNameContainingIgnoreCase(keyword);
    }

    public Pizza createPizza(Pizza formPizza) {
        Pizza pizzaToPersist = setAllData(formPizza);

        return pizzaRepository.save(pizzaToPersist);
    }

    public Pizza updatePizza(Pizza formPizza, Integer id) throws PizzaNotFoundException {
        Pizza pizzaToUpdate = getById(id);

        pizzaToUpdate = setAllData(formPizza);

        return pizzaRepository.save(pizzaToUpdate);
    }

    public Pizza getById(Integer id) throws PizzaNotFoundException {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new PizzaNotFoundException("Pizza non esistente.");
        }
    }

    public boolean deleteById(Integer id) throws PizzaNotFoundException {
        pizzaRepository.findById(id).orElseThrow(() -> new PizzaNotFoundException(Integer.toString(id)));
        try {
            pizzaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
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
