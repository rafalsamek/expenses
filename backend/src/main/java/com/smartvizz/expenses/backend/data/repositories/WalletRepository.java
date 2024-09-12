package com.smartvizz.expenses.backend.data.repositories;

import com.smartvizz.expenses.backend.data.entities.UserEntity;
import com.smartvizz.expenses.backend.data.entities.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletEntity, Integer>, JpaSpecificationExecutor<WalletEntity> {

    // Fetch all wallets for a specific user with pagination
    Page<WalletEntity> findAllByUser(UserEntity user, Pageable pageable);

    // Fetch a specific wallet by its ID and user
    Optional<WalletEntity> findByIdAndUser(Integer id, UserEntity user);
}
