package com.smartvizz.expenses.backend.web.models;

import com.smartvizz.expenses.backend.data.entities.WalletEntity;

import java.time.Instant;

public record WalletResponse(
                                int id,
                                String name,
                                String description,
                                WalletEntity.Currency currency,
                                Instant createdAt,
                                Instant updatedAt
) {
    public WalletResponse(WalletEntity entity) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCurrency(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
