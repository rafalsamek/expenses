package com.smartvizz.expenses.backend.web.controllers;

import com.smartvizz.expenses.backend.services.ExpenseService;
import com.smartvizz.expenses.backend.web.models.ExpenseRequest;
import com.smartvizz.expenses.backend.web.models.ExpenseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> list() {
        List<ExpenseResponse> categories = expenseService.fetchAll();

        return ResponseEntity.ok(categories);
    }

    @GetMapping("{id}")
    public ResponseEntity<ExpenseResponse> get(@PathVariable Long id) {
        ExpenseResponse expense = expenseService.fetchOne(id);

        return ResponseEntity.ok(expense);
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> create(@RequestBody ExpenseRequest request) {
        ExpenseResponse createdExpense = expenseService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }

    @PutMapping("{id}")
    public ResponseEntity<ExpenseResponse> update(@PathVariable Long id, @RequestBody ExpenseRequest request) {
        ExpenseResponse updatedExpense = expenseService.update(id, request);

        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expenseService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
