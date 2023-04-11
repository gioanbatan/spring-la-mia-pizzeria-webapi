package com.experis.pizza.api;

import com.experis.pizza.model.Pizza;
import com.experis.pizza.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
