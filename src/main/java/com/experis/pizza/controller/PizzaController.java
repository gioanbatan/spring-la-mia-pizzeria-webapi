package com.experis.pizza.controller;

import com.experis.pizza.exceptions.PizzaNotFoundException;
import com.experis.pizza.model.AlertMessage;
import com.experis.pizza.model.Pizza;
import com.experis.pizza.repository.PizzaRepository;
import com.experis.pizza.service.IngredientService;
import com.experis.pizza.service.PizzaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public String search(Model model, @RequestParam(name = "search-query") Optional<String> keyword) {
        List<Pizza> pizzas;
        if (keyword.isEmpty()) {
            pizzas = pizzaService.getAllPizzas();
        } else {
            pizzas = pizzaService.getFilteredPizzas(keyword.get());
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("list", pizzas);
        return ("/pizzas/index");
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) throws PizzaNotFoundException {
        try {
            Pizza pizza = pizzaService.getById(id);
            model.addAttribute("pizza", pizza);
            return "/pizzas/show";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con id " + id + " non trovata.");
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredients", ingredientService.getAll());
        return "/pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientService.getAll());
            return "/pizzas/create";
        }
        pizzaService.createPizza(formPizza);
        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) throws PizzaNotFoundException {
        try {
            model.addAttribute("pizza", pizzaService.getById(id));
            model.addAttribute("ingredients", ingredientService.getAll());
            return "/pizzas/edit";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con id " + id + " non trovata.");
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) throws PizzaNotFoundException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientService.getAll());
            return "/pizzas/edit";
        }
        try {
            Pizza updatedPizza = pizzaService.updatePizza(formPizza, id);

            pizzaRepository.save(formPizza);
            return "redirect:/pizzas/" + Integer.toString(updatedPizza.getId());
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con id " + id + " non trovata.");
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Pizza> result = pizzaRepository.findById(id);
            boolean success = pizzaService.deleteById(id);
            if (success) {
                redirectAttributes.addFlashAttribute("message",
                        new AlertMessage(AlertMessage.AlertMessagesType.SUCCESS, "Pizza '" + result.get().getName() + "' cancellata."));
            } else {
                redirectAttributes.addFlashAttribute("message",
                        new AlertMessage(AlertMessage.AlertMessagesType.ERROR, "Impossibile eliminare la pizza " + result.get().getName() + "."));
            }
        } catch (PizzaNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",
                    new AlertMessage(AlertMessage.AlertMessagesType.ERROR, "Pizza non trovata."));
        }
        return "redirect:/pizzas";
    }
}
