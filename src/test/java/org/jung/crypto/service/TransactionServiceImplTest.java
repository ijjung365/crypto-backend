package org.jung.crypto.service;

import jakarta.transaction.Transactional;
import org.jung.crypto.domain.TransactionType;
import org.jung.crypto.dto.TransactionRequestDTO;
import org.jung.crypto.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class TransactionServiceImplTest {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @Test
    void testCreateTransaction() {
        UserDTO userDTO = UserDTO.builder()
                .username("username")
                .password("password")
                .role("user")
                .balance(10000000D)
                .build();
        userService.createUser(userDTO);

        TransactionRequestDTO transactionRequestDTO = TransactionRequestDTO.builder()
                .amount(1D)
                .symbol("BTCUSDT")
                .type(TransactionType.SELL)
                .build();
        transactionService.createTransaction(transactionRequestDTO);


    }

}