package org.jung.crypto.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jung.crypto.domain.TransactionType;
import org.jung.crypto.dto.TransactionDTO;
import org.jung.crypto.dto.TransactionRequestDTO;
import org.jung.crypto.service.TransactionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
@Log4j2
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<Void> buy(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        transactionService.createTransaction(transactionRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get")
    public ResponseEntity<List<TransactionDTO>> getTransactions(@RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "30") int size,
                                                               TransactionType transactionType) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.ok(transactionService.fetchTransactions(pageable, transactionType));
    }

}
