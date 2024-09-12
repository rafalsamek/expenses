package com.smartvizz.expenses.backend.web.models;

import com.smartvizz.expenses.backend.data.entities.CategoryEntity;

import java.time.Instant;

public record CategoryResponse(
        long id,
        String name,
        String description,
        UserResponse user,
        Instant createdAt,
        Instant updatedAt
) {
    public CategoryResponse(CategoryEntity entity) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                new UserResponse(entity.getUser()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
