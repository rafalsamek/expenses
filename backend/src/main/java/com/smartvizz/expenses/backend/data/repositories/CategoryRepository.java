package com.smartvizz.expenses.backend.data.repositories;

import com.smartvizz.expenses.backend.data.entities.CategoryEntity;
import com.smartvizz.expenses.backend.data.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>, JpaSpecificationExecutor<CategoryEntity> {

    // Find all categories associated with a specific user and apply pagination
    Page<CategoryEntity> findAllByUser(UserEntity user, Pageable pageable);

    // Find a category by its ID and the associated user
    Optional<CategoryEntity> findByIdAndUser(Long id, UserEntity user);
}
