package com.experis.pizza.repository;

import com.experis.pizza.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    // JPQL to find pizzas with 'name' in name
    public List<Pizza> findByNameContainingIgnoreCase(String name);
}
