package org.jung.crypto.service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;


public interface CryptoService {

    public JSONObject fetchCurrentPrice(String symbol);

    public JSONArray fetchCandles(String symbol, String interval, LocalDateTime start, LocalDateTime end);
}
