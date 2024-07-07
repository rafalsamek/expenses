package com.smartvizz.expenses.backend.services;

import com.smartvizz.expenses.backend.data.entities.ExpenseEntity;
import com.smartvizz.expenses.backend.data.repositories.ExpenseRepository;
import com.smartvizz.expenses.backend.web.models.ExpenseRequest;
import com.smartvizz.expenses.backend.web.models.ExpenseResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<ExpenseResponse> fetchAll() {
        return expenseRepository.findAll()
                .stream()
                .map(ExpenseResponse::new)
                .toList();
    }

    public ExpenseResponse fetchOne(Long id) {
        return expenseRepository.findById(id)
                .map(ExpenseResponse::new)
                .orElseThrow(NotFoundException::new);
    }

    public ExpenseResponse create(ExpenseRequest request) {
        ExpenseEntity expenseEntity = new ExpenseEntity(request.title(), request.description(), request.amount(), request.currency());

        ExpenseEntity savedExpense = expenseRepository.save(expenseEntity);
        return new ExpenseResponse(savedExpense);
    }

    public ExpenseResponse update(Long id, ExpenseRequest request) {
        ExpenseEntity expenseEntity = expenseRepository.getReferenceById(id);
        expenseEntity.setTitle(request.title());
        expenseEntity.setDescription(request.description());
        expenseEntity.setAmount(request.amount());
        expenseEntity.setCurrency(request.currency());
        ExpenseEntity updatedExpense = expenseRepository.save(expenseEntity);

        return new ExpenseResponse(updatedExpense);
    }

    public void delete(Long id) {
        expenseRepository.deleteById(id);
    }
}
