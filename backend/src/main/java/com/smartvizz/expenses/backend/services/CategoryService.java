package com.smartvizz.expenses.backend.services;

import com.smartvizz.expenses.backend.data.entities.CategoryEntity;
import com.smartvizz.expenses.backend.data.repositories.CategoryRepository;
import com.smartvizz.expenses.backend.data.specifications.CategorySpecifications;
import com.smartvizz.expenses.backend.web.models.CategoryRequest;
import com.smartvizz.expenses.backend.web.models.CategoryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<CategoryResponse> fetchAll(
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

        return categoryRepository.findAll(CategorySpecifications.searchCategory(searchBy), pageable)
                .map(CategoryResponse::new);
    }

    public CategoryResponse fetchOne(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryResponse::new)
                .orElseThrow(NotFoundException::new);
    }

    public CategoryResponse create(CategoryRequest request) {
        CategoryEntity categoryEntity = new CategoryEntity(request.name(), request.description());

        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return new CategoryResponse(savedCategory);
    }

    public CategoryResponse update(Long id, CategoryRequest request) {
        CategoryEntity categoryEntity = categoryRepository.getReferenceById(id);
        categoryEntity.setName(request.name());
        categoryEntity.setDescription(request.description());
        CategoryEntity updatedCategory = categoryRepository.save(categoryEntity);

        return new CategoryResponse(updatedCategory);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
