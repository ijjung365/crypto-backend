package org.jung.crypto.repository;

import org.jung.crypto.domain.Transaction;
import org.jung.crypto.repository.search.TransactionSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> , TransactionSearch {
}
