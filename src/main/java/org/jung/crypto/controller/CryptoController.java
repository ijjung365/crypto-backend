package org.jung.crypto.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jung.crypto.service.CryptoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/crypto")
@RequiredArgsConstructor
@Log4j2
public class CryptoController {

    private final CryptoService cryptoService;

    @GetMapping("/price/{symbol}")
    public ResponseEntity<String> getCurrentPrice(@PathVariable("symbol") String symbol) {
        log.info("getPrice called");
        return ResponseEntity.ok(cryptoService.fetchCurrentPrice(symbol).toString());
    }

    @GetMapping("/candles/{symbol}")
    public ResponseEntity<String> getCandles(@PathVariable("symbol") String symbol) {
        log.info("getCandles called");
        return ResponseEntity.ok(cryptoService.fetchCandles(symbol,"1h", LocalDateTime.now().minusDays(1), LocalDateTime.now()).toString());
    }
}
