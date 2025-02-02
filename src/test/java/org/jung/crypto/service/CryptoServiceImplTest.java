package org.jung.crypto.service;

import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jung.crypto.binance.BinanceApiClient;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Log4j2
class CryptoServiceImplTest {
    CryptoService cryptoService = new CryptoServiceImpl(new BinanceApiClient());
    @Test
    void testFetchCurrentPrice(){
        JSONObject result = cryptoService.fetchCurrentPrice("BTCUSDT");
        log.info(result);
        assertNotNull(result);
    }

    @Test
    void testFetchCandles(){
        JSONArray candles = cryptoService.fetchCandles("BTCUSDT","1h", LocalDateTime.now().minusDays(1), LocalDateTime.now());
        log.info(candles);
        assertNotNull(candles);
    }
}