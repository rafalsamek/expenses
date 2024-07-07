package com.smartvizz.expenses.backend.data.repositories;

import com.smartvizz.expenses.backend.data.entities.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
}
