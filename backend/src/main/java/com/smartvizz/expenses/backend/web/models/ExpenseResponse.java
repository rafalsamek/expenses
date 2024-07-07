package com.smartvizz.expenses.backend.web.models;

import com.smartvizz.expenses.backend.data.entities.ExpenseEntity;

import java.time.Instant;

public record ExpenseResponse(
                                Long id,
                                String title,
                                String description,
                                Long amount,
                                ExpenseEntity.Currency currency,
                                Instant createdAt,
                                Instant updatedAt
) {
    ExpenseResponse(ExpenseEntity entity) {
        this(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getAmount(),
                entity.getCurrency(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
