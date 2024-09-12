package com.smartvizz.expenses.backend.data.repositories;

import com.smartvizz.expenses.backend.data.entities.UserEntity;
import com.smartvizz.expenses.backend.data.entities.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletEntity, Integer>, JpaSpecificationExecutor<WalletEntity> {
    Optional<WalletEntity> findByIdAndUser(Integer id, UserEntity user);
}
