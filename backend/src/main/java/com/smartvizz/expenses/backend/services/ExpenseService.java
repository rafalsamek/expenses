package com.smartvizz.expenses.backend.services;

import com.smartvizz.expenses.backend.data.repositories.ExpenseRepository;
import com.smartvizz.expenses.backend.web.models.ExpenseRequest;
import com.smartvizz.expenses.backend.web.models.ExpenseResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<ExpenseResponse> fetchAll() {
        return null;
    }

    public ExpenseResponse fetchOne(Long id) {
        return null;
    }

    public ExpenseResponse create(ExpenseRequest request) {
        return null;
    }

    public ExpenseResponse update(ExpenseRequest request) {
        return null;
    }

    public void delete(Long id) {

    }
}
