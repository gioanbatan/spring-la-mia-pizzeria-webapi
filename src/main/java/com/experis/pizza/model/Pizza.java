package com.experis.pizza.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pizzas")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private BigDecimal price;
}
