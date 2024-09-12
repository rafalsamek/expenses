package com.smartvizz.expenses.backend.services;

import com.smartvizz.expenses.backend.data.entities.CategoryEntity;
import com.smartvizz.expenses.backend.data.entities.UserEntity;
import com.smartvizz.expenses.backend.data.repositories.CategoryRepository;
import com.smartvizz.expenses.backend.data.repositories.UserRepository;
import com.smartvizz.expenses.backend.web.models.CategoryRequest;
import com.smartvizz.expenses.backend.web.models.CategoryResponse;
import com.smartvizz.expenses.backend.web.models.PageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public PageDTO<CategoryResponse> fetchAll(
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

        // Fetch and map entities to DTOs filtered by user
        Page<CategoryEntity> categoryPage = categoryRepository.findAllByUser(userEntity, pageable);
        List<CategoryResponse> categoryResponses = categoryPage.map(CategoryResponse::new).getContent();

        // Create and return PageDTO
        return new PageDTO<>(
                categoryResponses,
                categoryPage.getNumber(),
                categoryPage.getSize(),
                categoryPage.getTotalElements(),
                categoryPage.getTotalPages()
        );
    }

    public CategoryResponse fetchOne(long id, User user) {
        // Fetch UserEntity based on the authenticated user
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + user.getUsername()));

        // Fetch the category by its ID and the authenticated user
        return categoryRepository.findByIdAndUser(id, userEntity)
                .map(CategoryResponse::new)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
    }

    public CategoryResponse create(CategoryRequest request, User user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + user.getUsername()));

        CategoryEntity categoryEntity = new CategoryEntity(
                request.name(),
                request.description(),
                userEntity
        );

        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return new CategoryResponse(savedCategory);
    }

    public CategoryResponse update(long id, CategoryRequest request, User user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + user.getUsername()));

        CategoryEntity categoryEntity = categoryRepository.findByIdAndUser(id, userEntity)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));

        categoryEntity.setName(request.name());
        categoryEntity.setDescription(request.description());

        CategoryEntity updatedCategory = categoryRepository.save(categoryEntity);
        return new CategoryResponse(updatedCategory);
    }

    public void delete(long id, User user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + user.getUsername()));

        CategoryEntity categoryEntity = categoryRepository.findByIdAndUser(id, userEntity)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));

        categoryRepository.delete(categoryEntity);
    }
}
