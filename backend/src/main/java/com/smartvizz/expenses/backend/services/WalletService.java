package com.smartvizz.expenses.backend.services;

import com.smartvizz.expenses.backend.data.entities.WalletEntity;
import com.smartvizz.expenses.backend.data.repositories.WalletRepository;
import com.smartvizz.expenses.backend.data.specifications.WalletSpecifications;
import com.smartvizz.expenses.backend.web.models.WalletRequest;
import com.smartvizz.expenses.backend.web.models.WalletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Page<WalletResponse> fetchAll(
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

        return walletRepository.findAll(WalletSpecifications.searchWallet(searchBy), pageable)
                .map(WalletResponse::new);
    }

    public WalletResponse fetchOne(Long id) {
        return walletRepository.findById(id)
                .map(WalletResponse::new)
                .orElseThrow(NotFoundException::new);
    }

    public WalletResponse create(WalletRequest request) {
        WalletEntity walletEntity = new WalletEntity(request.name(), request.description(), request.currency());

        WalletEntity savedWallet = walletRepository.save(walletEntity);
        return new WalletResponse(savedWallet);
    }

    public WalletResponse update(Long id, WalletRequest request) {
        WalletEntity walletEntity = walletRepository.getReferenceById(id);
        walletEntity.setName(request.name());
        walletEntity.setDescription(request.description());
        walletEntity.setCurrency(request.currency());
        WalletEntity updatedWallet = walletRepository.save(walletEntity);

        return new WalletResponse(updatedWallet);
    }

    public void delete(Long id) {
        walletRepository.deleteById(id);
    }
}
