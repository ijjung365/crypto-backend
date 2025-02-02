package org.jung.crypto.repository.search;

import org.jung.crypto.domain.Transaction;
import org.jung.crypto.domain.TransactionType;
import org.jung.crypto.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionSearch {
    List<Transaction> search(User user, TransactionType transactionType, Pageable pageable);
}
