package com.smartvizz.expenses.backend.services;

import com.smartvizz.expenses.backend.data.entities.ExpenseEntity;
import com.smartvizz.expenses.backend.data.entities.WalletEntity;
import com.smartvizz.expenses.backend.data.repositories.ExpenseRepository;
import com.smartvizz.expenses.backend.data.repositories.WalletRepository;
import com.smartvizz.expenses.backend.data.specifications.ExpenseSpecifications;
import com.smartvizz.expenses.backend.web.models.ExpenseRequest;
import com.smartvizz.expenses.backend.web.models.ExpenseResponse;
import com.smartvizz.expenses.backend.web.models.PageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final WalletRepository walletRepository;
    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);

    public ExpenseService(ExpenseRepository expenseRepository, WalletRepository walletRepository) {
        this.expenseRepository = expenseRepository;
        this.walletRepository = walletRepository;
    }

    public PageDTO<ExpenseResponse> fetchAll(
            int page,
            int size,
            String[] sortColumns,
            String[] sortDirections,
            String searchBy
    ) {
        // Validate page and size inputs
        page = Math.max(page, 0);
        size = Math.max(size, 1);

        // Create sort orders from the provided columns and directions
        List<Sort.Order> sortOrders = new ArrayList<>();
        for (int i = 0; i < sortColumns.length; i++) {
            String sortColumn = sortColumns[i];
            Sort.Direction sortDirection = (sortDirections.length > i && sortDirections[i].equalsIgnoreCase("desc"))
                    ? Sort.Direction.DESC : Sort.Direction.ASC;
            sortOrders.add(new Sort.Order(sortDirection, sortColumn));
        }

        // Create Pageable instance
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrders));

        // Fetch and map entities to DTOs
        Page<ExpenseEntity> expensePage = expenseRepository.findAll(ExpenseSpecifications.searchExpense(searchBy), pageable);
        List<ExpenseResponse> expenseResponses = expensePage.map(ExpenseResponse::new).getContent();

        // Create and return PageDTO
        return new PageDTO<>(
                expenseResponses,
                expensePage.getNumber(),
                expensePage.getSize(),
                expensePage.getTotalElements(),
                expensePage.getTotalPages()
        );
    }

    public ExpenseResponse fetchOne(long id) {
        return expenseRepository.findById(id)
                .map(ExpenseResponse::new)
                .orElseThrow(() -> new NotFoundException("Expense not found with id: " + id));
    }

    public ExpenseResponse create(ExpenseRequest request) {
        WalletEntity walletEntity = walletRepository.findById(request.walletId())
                .orElseThrow(() -> new NotFoundException("Wallet not found with id: " + request.walletId()));

        ExpenseEntity expenseEntity = new ExpenseEntity(
                request.title(),
                request.description(),
                request.amount(),
                request.currency(),
                walletEntity
        );

        ExpenseEntity savedExpense = expenseRepository.save(expenseEntity);
        return new ExpenseResponse(savedExpense);
    }

    public ExpenseResponse update(long id, ExpenseRequest request) {
        ExpenseEntity expenseEntity = expenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Expense not found with id: " + id));

        WalletEntity walletEntity = walletRepository.findById(request.walletId())
                .orElseThrow(() -> new NotFoundException("Wallet not found with id: " + request.walletId()));

        expenseEntity.setTitle(request.title());
        expenseEntity.setDescription(request.description());
        expenseEntity.setAmount(request.amount());
        expenseEntity.setCurrency(request.currency());
        expenseEntity.setWallet(walletEntity);

        ExpenseEntity updatedExpense = expenseRepository.save(expenseEntity);
        return new ExpenseResponse(updatedExpense);
    }

    public void delete(long id) {
        if (!expenseRepository.existsById(id)) {
            throw new NotFoundException("Expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);
    }
}
