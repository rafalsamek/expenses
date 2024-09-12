package com.smartvizz.expenses.backend.services;

import com.smartvizz.expenses.backend.data.entities.WalletEntity;
import com.smartvizz.expenses.backend.data.entities.UserEntity;
import com.smartvizz.expenses.backend.data.repositories.WalletRepository;
import com.smartvizz.expenses.backend.data.repositories.UserRepository;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);

    public WalletService(WalletRepository walletRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    public PageDTO<WalletResponse> fetchAll(
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

        // Fetch wallets filtered by user and map entities to DTOs
        Page<WalletEntity> walletPage = walletRepository.findAllByUser(userEntity, pageable);
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

    public WalletResponse fetchOne(int id, User user) {
        // Fetch UserEntity based on the authenticated user
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + user.getUsername()));

        // Fetch the wallet filtered by user
        return walletRepository.findByIdAndUser(id, userEntity)
                .map(WalletResponse::new)
                .orElseThrow(() -> new NotFoundException("Wallet not found with id: " + id));
    }

    public WalletResponse create(WalletRequest request, User user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + user.getUsername()));

        WalletEntity walletEntity = new WalletEntity(
                request.name(),
                request.description(),
                request.currency(),
                userEntity
        );

        WalletEntity savedWallet = walletRepository.save(walletEntity);
        return new WalletResponse(savedWallet);
    }

    public WalletResponse update(int id, WalletRequest request, User user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + user.getUsername()));

        WalletEntity walletEntity = walletRepository.findByIdAndUser(id, userEntity)
                .orElseThrow(() -> new NotFoundException("Wallet not found with id: " + id));

        walletEntity.setName(request.name());
        walletEntity.setDescription(request.description());
        walletEntity.setCurrency(request.currency());
        walletEntity.setUser(userEntity); // Update user association

        WalletEntity updatedWallet = walletRepository.save(walletEntity);
        return new WalletResponse(updatedWallet);
    }

    public void delete(int id, User user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + user.getUsername()));

        WalletEntity walletEntity = walletRepository.findByIdAndUser(id, userEntity)
                .orElseThrow(() -> new NotFoundException("Wallet not found with id: " + id));

        walletRepository.delete(walletEntity);
    }
}
