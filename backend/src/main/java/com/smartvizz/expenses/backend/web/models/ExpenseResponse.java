package com.smartvizz.expenses.backend.web.models;

import com.smartvizz.expenses.backend.data.entities.ExpenseEntity;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public record ExpenseResponse(
        long id,
        String title,
        String description,
        long amount,
        ExpenseEntity.Currency currency,
        WalletResponse wallet,
        List<CategoryResponse> categories,
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
                entity.getCategories() != null ? entity.getCategories().stream()
                        .map(CategoryResponse::new)
                        .collect(Collectors.toList()) : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
