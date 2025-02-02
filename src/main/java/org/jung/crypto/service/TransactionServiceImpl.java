package org.jung.crypto.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jung.crypto.binance.BinanceApiClient;
import org.jung.crypto.domain.Hold;
import org.jung.crypto.domain.Transaction;
import org.jung.crypto.domain.TransactionType;
import org.jung.crypto.domain.User;
import org.jung.crypto.dto.CustomUserDetails;
import org.jung.crypto.dto.TransactionDTO;
import org.jung.crypto.dto.TransactionRequestDTO;
import org.jung.crypto.exception.NotEnoughBalanceException;
import org.jung.crypto.exception.NotEnoughHoldException;
import org.jung.crypto.repository.HoldRepository;
import org.jung.crypto.repository.TransactionRepository;
import org.jung.crypto.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final ModelMapper modelMapper;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final HoldRepository holdRepository;
    private final BinanceApiClient binanceApiClient;

    public void createTransaction(TransactionRequestDTO transactionRequestDTO) {
        String symbol = transactionRequestDTO.getSymbol();
        Double amount = transactionRequestDTO.getAmount();

        if(transactionRequestDTO.getType()== TransactionType.BUY){
            buyCrypto(symbol,amount);
        }
        if(transactionRequestDTO.getType()== TransactionType.SELL){
            sellCrypto(symbol,amount);
        }
    }

    @Override
    public List<TransactionDTO> fetchTransactions(Pageable pageable, TransactionType transactionType) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(customUserDetails.getUsername()).orElseThrow();
        List<Transaction> transactionList = transactionRepository.search(user, transactionType, pageable);
        List<TransactionDTO> transactionDTOList = transactionList.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());
        log.info(transactionDTOList);
        return transactionDTOList;
    }

    public void buyCrypto(String symbol, Double amount) {
        log.info("Buy crypto");
        //가격조회
        Double price = binanceApiClient.getPrice(symbol).getDouble("price");
        Double totalPrice = price * amount;

        //유저 잔액조회
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(customUserDetails.getUsername()).orElseThrow();

//        User user = userRepository.findByUsername("username").orElseThrow();
        Double userBalance = user.getBalance();

        log.info("User Balance: " + userBalance);

        //조건확인
        if(userBalance < totalPrice){
            throw new NotEnoughBalanceException("Not enough balance");
        }
        Hold hold;
        //이행
        Optional<Hold> holdWrapper = holdRepository.findByUserAndSymbol(user, symbol);
        if(holdWrapper.isEmpty()){
            hold = Hold.builder()
                    .symbol(symbol)
                    .amount(amount)
                    .user(user)
                    .build();
        } else{
            hold = holdWrapper.orElseThrow();
            hold.increaseAmount(amount);
        }
        holdRepository.save(hold);

        user.decreaseBalance(totalPrice);
        userRepository.save(user);

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .symbol(symbol)
                .user(user)
                .type(TransactionType.BUY)
                .build();

        transactionRepository.save(transaction);
        log.info(user);
        log.info(hold);
        log.info(transaction);
    }

    public void sellCrypto(String symbol, Double amount) {
        log.info("sellCrypto");
        //가격조회
        Double price = binanceApiClient.getPrice(symbol).getDouble("price");
        Double totalPrice = price * amount;

        //유저조회
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(customUserDetails.getUsername()).orElseThrow();

//        User user = userRepository.findByUsername("username").orElseThrow();

        Hold hold = holdRepository.findByUserAndSymbol(user, symbol).orElseThrow(() -> new NotEnoughHoldException("You do not have hold"));

        //조건 확인
        if(hold.getAmount() < amount){
            throw new NotEnoughHoldException("Not enough hold");
        }

        //이행
        hold.decreaseAmount(amount);
        if(hold.getAmount() <= amount){
            holdRepository.delete(hold);
        } else {
            holdRepository.save(hold);
        }


        user.increaseBalance(totalPrice);
        userRepository.save(user);

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .symbol(symbol)
                .user(user)
                .type(TransactionType.SELL)
                .build();

        transactionRepository.save(transaction);

        log.info(user);
        log.info(hold);
        log.info(transaction);
    }

}
