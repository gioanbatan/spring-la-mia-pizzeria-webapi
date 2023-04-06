package com.experis.pizza.repository;

import com.experis.pizza.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
