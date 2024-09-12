package com.smartvizz.expenses.backend.data.repositories;

import com.smartvizz.expenses.backend.data.entities.ExpenseEntity;
import com.smartvizz.expenses.backend.data.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long>, JpaSpecificationExecutor<ExpenseEntity> {

    // Fetch all expenses for a specific user with pagination
    Page<ExpenseEntity> findAllByUser(UserEntity user, Pageable pageable);

    // Find a specific expense by ID for a specific user
    Optional<ExpenseEntity> findByIdAndUser(Long id, UserEntity user);
}
