package com.smartvizz.expenses.backend.services;

import com.smartvizz.expenses.backend.data.entities.WalletEntity;
import com.smartvizz.expenses.backend.data.repositories.WalletRepository;
import com.smartvizz.expenses.backend.data.specifications.WalletSpecifications;
import com.smartvizz.expenses.backend.web.models.WalletRequest;
import com.smartvizz.expenses.backend.web.models.WalletResponse;
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
public class WalletService {

    private final WalletRepository walletRepository;
    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public PageDTO<WalletResponse> fetchAll(
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
        Page<WalletEntity> walletPage = walletRepository.findAll(WalletSpecifications.searchWallet(searchBy), pageable);
        List<WalletResponse> walletResponses = walletPage.map(WalletResponse::new).getContent();

        // Create and return PageDTO
        return new PageDTO<>(
                walletResponses,
                walletPage.getNumber(),
                walletPage.getSize(),
                walletPage.getTotalElements(),
                walletPage.getTotalPages()
        );
    }

    public WalletResponse fetchOne(Long id) {
        return walletRepository.findById(id)
                .map(WalletResponse::new)
                .orElseThrow(() -> new NotFoundException("Wallet not found with id: " + id));
    }

    public WalletResponse create(WalletRequest request) {
        WalletEntity walletEntity = new WalletEntity(
                request.name(),
                request.description(),
                request.currency()
        );

        WalletEntity savedWallet = walletRepository.save(walletEntity);
        return new WalletResponse(savedWallet);
    }

    public WalletResponse update(Long id, WalletRequest request) {
        WalletEntity walletEntity = walletRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Wallet not found with id: " + id));

        walletEntity.setName(request.name());
        walletEntity.setDescription(request.description());
        walletEntity.setCurrency(request.currency());

        WalletEntity updatedWallet = walletRepository.save(walletEntity);
        return new WalletResponse(updatedWallet);
    }

    public void delete(Long id) {
        if (!walletRepository.existsById(id)) {
            throw new NotFoundException("Wallet not found with id: " + id);
        }
        walletRepository.deleteById(id);
    }
}
