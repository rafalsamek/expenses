package com.smartvizz.expenses.backend.web.models;

import com.smartvizz.expenses.backend.data.entities.ExpenseEntity.Currency;

public record ExpenseRequest(String title, String description, Long amount, Currency currency) {
}
