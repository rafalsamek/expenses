package com.smartvizz.expenses.backend.services;

import com.smartvizz.expenses.backend.web.models.ExpenseRequest;
import com.smartvizz.expenses.backend.web.models.ExpenseResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    public List<ExpenseResponse> fetchAll() {
        List<ExpenseResponse> expenseResponses = new ArrayList<>();

        return expenseResponses;
    }

    public ExpenseResponse fetchOne(Long id) {
        ExpenseResponse expenseResponse = new ExpenseResponse();

        return expenseResponse;
    }


    public ExpenseResponse create(ExpenseRequest request) {
        ExpenseResponse expenseResponse = new ExpenseResponse();

        return expenseResponse;
    }

    public ExpenseResponse update(ExpenseRequest request) {
        ExpenseResponse expenseResponse = new ExpenseResponse();

        return expenseResponse;
    }


    public void delete(Long id) {

    }
}
