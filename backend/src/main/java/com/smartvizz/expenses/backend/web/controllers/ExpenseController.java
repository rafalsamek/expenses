package com.smartvizz.expenses.backend.web.controllers;

import com.smartvizz.expenses.backend.services.ExpenseService;
import com.smartvizz.expenses.backend.web.models.ExpenseRequest;
import com.smartvizz.expenses.backend.web.models.ExpenseResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<Page<ExpenseResponse>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "createdAt") String[] sortColumns,
            @RequestParam(defaultValue = "asc") String[] sortDirections,
            @RequestParam String searchBy
    ) {
        Page<ExpenseResponse> expenses = expenseService.fetchAll(page, size, sortColumns, sortDirections, searchBy);

        return ResponseEntity.ok(expenses);
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
