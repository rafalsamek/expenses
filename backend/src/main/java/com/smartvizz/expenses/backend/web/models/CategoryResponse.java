package com.smartvizz.expenses.backend.web.models;

import com.smartvizz.expenses.backend.data.entities.CategoryEntity;

import java.time.Instant;

public record CategoryResponse(
                                Long id,
                                String name,
                                String description,
                                Instant createdAt,
                                Instant updatedAt
) {
    public CategoryResponse(CategoryEntity entity) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
