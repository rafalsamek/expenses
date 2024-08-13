package com.smartvizz.expenses.backend.services;

import com.smartvizz.expenses.backend.data.entities.CategoryEntity;
import com.smartvizz.expenses.backend.data.repositories.CategoryRepository;
import com.smartvizz.expenses.backend.data.specifications.CategorySpecifications;
import com.smartvizz.expenses.backend.web.models.CategoryRequest;
import com.smartvizz.expenses.backend.web.models.CategoryResponse;
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
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public PageDTO<CategoryResponse> fetchAll(
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
        Page<CategoryEntity> categoryPage = categoryRepository.findAll(CategorySpecifications.searchCategory(searchBy), pageable);
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

    public CategoryResponse fetchOne(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryResponse::new)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
    }

    public CategoryResponse create(CategoryRequest request) {
        CategoryEntity categoryEntity = new CategoryEntity(
                request.name(),
                request.description()
        );

        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return new CategoryResponse(savedCategory);
    }

    public CategoryResponse update(Long id, CategoryRequest request) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));

        categoryEntity.setName(request.name());
        categoryEntity.setDescription(request.description());

        CategoryEntity updatedCategory = categoryRepository.save(categoryEntity);
        return new CategoryResponse(updatedCategory);
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
