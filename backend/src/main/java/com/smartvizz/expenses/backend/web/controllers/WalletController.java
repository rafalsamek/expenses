package com.smartvizz.expenses.backend.web.controllers;

import com.smartvizz.expenses.backend.services.WalletService;
import com.smartvizz.expenses.backend.web.models.WalletRequest;
import com.smartvizz.expenses.backend.web.models.WalletResponse;
import com.smartvizz.expenses.backend.web.models.PageDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallets")
@CrossOrigin(origins = {"http://localhost:8888", "http://localhost:4200"})
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public ResponseEntity<PageDTO<WalletResponse>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "createdAt,title") String[] sortColumns,
            @RequestParam(defaultValue = "asc,asc") String[] sortDirections,
            @RequestParam(defaultValue = "") String searchBy
    ) {
        PageDTO<WalletResponse> wallets = walletService.fetchAll(page, size, sortColumns, sortDirections, searchBy);
        return ResponseEntity.ok(wallets);
    }

    @GetMapping("{id}")
    public ResponseEntity<WalletResponse> get(@PathVariable int id) {
        WalletResponse wallet = walletService.fetchOne(id);
        return ResponseEntity.ok(wallet);
    }

    @PostMapping
    public ResponseEntity<WalletResponse> create(@Valid @RequestBody WalletRequest request) {
        WalletResponse createdWallet = walletService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWallet);
    }

    @PutMapping("{id}")
    public ResponseEntity<WalletResponse> update(@PathVariable int id, @Valid @RequestBody WalletRequest request) {
        WalletResponse updatedWallet = walletService.update(id, request);
        return ResponseEntity.ok(updatedWallet);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        walletService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
