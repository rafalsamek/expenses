package com.smartvizz.expenses.backend.web.models;

import com.smartvizz.expenses.backend.data.entities.WalletEntity.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record WalletRequest(
        @NotNull(message = "Name is required")
        @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
        String name,

        @Size(max = 1000, message = "Description must be up to 1000 characters")
        String description,

        @NotNull(message = "Currency is required")
        Currency currency
) {
}
