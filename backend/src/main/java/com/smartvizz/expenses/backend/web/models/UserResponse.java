package com.smartvizz.expenses.backend.web.models;

import com.smartvizz.expenses.backend.data.entities.UserEntity;

public record UserResponse(
        long id,
        String username,
        String email
) {
    public UserResponse(UserEntity user) {
        this(user.getId(), user.getUsername(), user.getEmail());
    }
}
