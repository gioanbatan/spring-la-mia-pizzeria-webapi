package com.experis.pizza.api;

import com.experis.pizza.exceptions.PizzaNotFoundException;
import com.experis.pizza.model.Pizza;
import com.experis.pizza.service.PizzaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/pizzas")
public class PizzaRestController {

    @Autowired
    private PizzaService pizzaService;

    @GetMapping
    public List<Pizza> pizzas(@RequestParam(name = "search-query") Optional<String> search) {
        if (search.isPresent()) {
            return pizzaService.getFilteredPizzas(search.get());
        }
        return pizzaService.getAllPizzas();
    }

    @GetMapping("/{id}")
    public Pizza getById(@PathVariable Integer id) throws PizzaNotFoundException {
        try {
            return pizzaService.getById(id);
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public Pizza create(@Valid @RequestBody Pizza pizza) {
        return pizzaService.createPizza(pizza);
    }

    @PutMapping("/{id}")
    public Pizza update(@PathVariable Integer id, @Valid @RequestBody Pizza pizza) {
        try {
            return pizzaService.updatePizza(pizza, id);
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza non trovata.");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        try {
            boolean success = pizzaService.deleteById(id);
            if (!success) {
                // ADD EXCEPTION FOR RELATIONSHIPS
            }
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
