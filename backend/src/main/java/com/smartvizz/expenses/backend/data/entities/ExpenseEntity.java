package com.smartvizz.expenses.backend.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "expeneses")
public class ExpenseEntity {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;
}
