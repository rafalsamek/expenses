package com.smartvizz.expenses.backend.data.repositories;

import com.smartvizz.expenses.backend.data.entities.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WalletRepository extends JpaRepository<WalletEntity, Integer>, JpaSpecificationExecutor<WalletEntity> {
}
