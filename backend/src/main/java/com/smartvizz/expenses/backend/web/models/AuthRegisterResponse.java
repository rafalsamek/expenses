package com.smartvizz.expenses.backend.web.models;

public record AuthRegisterResponse(
        Long id,
        String username,
        String email,
        boolean enabled
) {}
