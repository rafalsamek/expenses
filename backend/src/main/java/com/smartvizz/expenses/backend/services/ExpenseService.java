package com.smartvizz.expenses.backend.services;

import com.smartvizz.expenses.backend.data.entities.ExpenseEntity;
import com.smartvizz.expenses.backend.data.repositories.ExpenseRepository;
import com.smartvizz.expenses.backend.data.specifications.ExpenseSpecifications;
import com.smartvizz.expenses.backend.web.models.ExpenseRequest;
import com.smartvizz.expenses.backend.web.models.ExpenseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Page<ExpenseResponse> fetchAll(
            int page,
            int size,
            String[] sortColumns,
            String[] sortDirections,
            String searchBy
    ) {
        ArrayList<Sort.Order> sortOrders = new ArrayList<>();

        for (int i = 0; i < sortColumns.length; i++) {

            String sortColumn = sortColumns[i];
            Sort.Direction sortDirection =
                    sortDirections.length > i && sortDirections[i].equalsIgnoreCase("desc")
                            ? Sort.Direction.DESC
                            : Sort.Direction.ASC;


            sortOrders.add(new Sort.Order(sortDirection, sortColumn));
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrders));

        return expenseRepository.findAll(ExpenseSpecifications.searchExpense(searchBy), pageable)
                .map(ExpenseResponse::new);
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
