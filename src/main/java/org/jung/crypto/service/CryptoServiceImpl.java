package org.jung.crypto.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jung.crypto.binance.BinanceApiClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class CryptoServiceImpl implements CryptoService {
    private final BinanceApiClient apiClient;

    @Override
    public JSONObject fetchCurrentPrice(String symbol){
        log.info("fetchPrice called");
        return apiClient.getPrice(symbol);
    }

    @Override
    public JSONArray fetchCandles(String symbol, String interval, LocalDateTime start, LocalDateTime end){
        log.info("fetchCandles called");
        return apiClient.getHistoricalData(symbol, interval, start, end);
    }
}
