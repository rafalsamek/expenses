package com.smartvizz.expenses.backend.web.models;

import com.smartvizz.expenses.backend.data.entities.ExpenseEntity;

import java.time.Instant;

public record ExpenseResponse(
        long id,
        String title,
        String description,
        long amount,
        ExpenseEntity.Currency currency,
        WalletResponse wallet,
        Instant createdAt,
        Instant updatedAt
) {
    public ExpenseResponse(ExpenseEntity entity) {
        this(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getAmount(),
                entity.getCurrency(),
                new WalletResponse(entity.getWallet()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
