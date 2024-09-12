package com.smartvizz.expenses.backend.services;

import com.smartvizz.expenses.backend.data.entities.CategoryEntity;
import com.smartvizz.expenses.backend.data.entities.ExpenseEntity;
import com.smartvizz.expenses.backend.data.entities.UserEntity;
import com.smartvizz.expenses.backend.data.entities.WalletEntity;
import com.smartvizz.expenses.backend.data.repositories.ExpenseRepository;
import com.smartvizz.expenses.backend.data.repositories.CategoryRepository;
import com.smartvizz.expenses.backend.data.repositories.UserRepository;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final WalletRepository walletRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);

    public ExpenseService(
            ExpenseRepository expenseRepository,
            WalletRepository walletRepository,
            CategoryRepository categoryRepository,
            UserRepository userRepository
    ) {
        this.expenseRepository = expenseRepository;
        this.walletRepository = walletRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public PageDTO<ExpenseResponse> fetchAll(
            int page,
            int size,
            String[] sortColumns,
            String[] sortDirections,
            String searchBy,
            User user
    ) {
        // Fetch UserEntity based on the authenticated user
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + user.getUsername()));

        // Validate page and size inputs
        page = Math.max(page, 0);
        size = Math.max(size, 1);

        // Create sort orders from the provided columns and directions
        List<Sort.Order> sortOrders = new ArrayList<>();
        for (int i = 0; i < sortColumns.length; i++) {
            String sortColumn = sortColumns[i];
            Sort.Direction sortDirection =
                    (sortDirections.length > i && sortDirections[i].equalsIgnoreCase("desc"))
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;
            sortOrders.add(new Sort.Order(sortDirection, sortColumn));
        }

        // Create Pageable instance
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrders));

        // Combine user filtering and search specification
        Specification<ExpenseEntity> spec = Specification.where(ExpenseSpecifications.searchExpense(searchBy))
                .and(ExpenseSpecifications.byUser(userEntity));

        // Fetch and map entities to DTOs
        Page<ExpenseEntity> expensePage = expenseRepository.findAll(spec, pageable);
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

    public ExpenseResponse fetchOne(long id, User user) {
        // Fetch UserEntity based on the authenticated user
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + user.getUsername()));

        // Find the expense by ID and user
        return expenseRepository.findByIdAndUser(id, userEntity)
                .map(ExpenseResponse::new)
                .orElseThrow(() -> new NotFoundException("Expense not found with id: " + id));
    }

    public ExpenseResponse create(ExpenseRequest request, User user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + user.getUsername()));

        WalletEntity walletEntity = walletRepository.findById(request.walletId())
                .orElseThrow(() -> new NotFoundException("Wallet not found with id: " + request.walletId()));

        ExpenseEntity expenseEntity = new ExpenseEntity(
                request.title(),
                request.description(),
                request.amount(),
                request.currency(),
                walletEntity,
                userEntity
        );

        return getExpenseResponse(request, expenseEntity);
    }

    public ExpenseResponse update(long id, ExpenseRequest request, User user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + user.getUsername()));

        ExpenseEntity expenseEntity = expenseRepository.findByIdAndUser(id, userEntity)
                .orElseThrow(() -> new NotFoundException("Expense not found with id: " + id));

        WalletEntity walletEntity = walletRepository.findById(request.walletId())
                .orElseThrow(() -> new NotFoundException("Wallet not found with id: " + request.walletId()));

        expenseEntity.setTitle(request.title());
        expenseEntity.setDescription(request.description());
        expenseEntity.setAmount(request.amount());
        expenseEntity.setCurrency(request.currency());
        expenseEntity.setWallet(walletEntity);
        expenseEntity.setUser(userEntity);

        return getExpenseResponse(request, expenseEntity);
    }

    private ExpenseResponse getExpenseResponse(ExpenseRequest request, ExpenseEntity expenseEntity) {
        if (request.categoryIds() != null) {
            List<CategoryEntity> categories = request.categoryIds().stream()
                    .map(categoryId -> categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new NotFoundException("Category not found with id: " + categoryId)))
                    .toList();
            expenseEntity.setCategories(new ArrayList<>(categories));
        }

        ExpenseEntity updatedExpense = expenseRepository.save(expenseEntity);

        return new ExpenseResponse(updatedExpense);
    }

    public void delete(long id, User user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + user.getUsername()));

        if (!expenseRepository.existsById(id)) {
            throw new NotFoundException("Expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);
    }
}
