package com.smartvizz.expenses.backend.web.controllers;

import com.smartvizz.expenses.backend.services.ExpenseService;
import com.smartvizz.expenses.backend.web.models.ExpenseRequest;
import com.smartvizz.expenses.backend.web.models.ExpenseResponse;
import com.smartvizz.expenses.backend.web.models.PageDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = {
        "https://expenses.smartvizz.com"
})
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<PageDTO<ExpenseResponse>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "createdAt,title") String[] sortColumns,
            @RequestParam(defaultValue = "asc,asc") String[] sortDirections,
            @RequestParam(defaultValue = "") String searchBy,
            @AuthenticationPrincipal User user
    ) {
        PageDTO<ExpenseResponse> expenses = expenseService.fetchAll(
                page,
                size,
                sortColumns,
                sortDirections,
                searchBy,
                user
        );

        return ResponseEntity.ok(expenses);
    }

    @GetMapping("{id}")
    public ResponseEntity<ExpenseResponse> get(@PathVariable long id, @AuthenticationPrincipal User user) {
        ExpenseResponse expense = expenseService.fetchOne(id, user);

        return ResponseEntity.ok(expense);
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> create(
            @Valid @RequestBody ExpenseRequest request,
            @AuthenticationPrincipal User user
    ) {
        ExpenseResponse createdExpense = expenseService.create(request, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }

    @PutMapping("{id}")
    public ResponseEntity<ExpenseResponse> update(
            @PathVariable long id,
            @Valid @RequestBody ExpenseRequest request,
            @AuthenticationPrincipal User user
    ) {
        ExpenseResponse updatedExpense = expenseService.update(id, request, user);

        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id, @AuthenticationPrincipal User user) {
        expenseService.delete(id, user);

        return ResponseEntity.noContent().build();
    }
}
