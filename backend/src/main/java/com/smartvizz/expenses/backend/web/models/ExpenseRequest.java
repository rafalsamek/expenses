package com.smartvizz.expenses.backend.web.models;

import com.smartvizz.expenses.backend.data.entities.ExpenseEntity.Currency;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ExpenseRequest(
        @NotNull(message = "Title is required")
        @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
        String title,

        @Size(max = 1000, message = "Description must be up to 1000 characters")
        String description,

        @NotNull(message = "Amount is required")
        @Min(value = 1, message = "Amount must be greater than 0")
        long amount,

        @NotNull(message = "Currency is required")
        Currency currency,

        @NotNull(message = "Wallet ID is required")
        @Min(value = 1, message = "Wallet ID must be greater than 0")
        int walletId,

        List<Long> categoryIds
) {
}
