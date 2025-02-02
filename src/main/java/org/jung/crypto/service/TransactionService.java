package org.jung.crypto.service;

import org.jung.crypto.domain.TransactionType;
import org.jung.crypto.dto.TransactionDTO;
import org.jung.crypto.dto.TransactionRequestDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {
    void createTransaction(TransactionRequestDTO transactionRequestDTO);
    List<TransactionDTO> fetchTransactions(Pageable pageable, TransactionType transactionType);

}
