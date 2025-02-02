package org.jung.crypto.binance;




import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@Log4j2
public class BinanceApiClient {
    private final String BASE_URL = "https://api.binance.com/api/v3";

    public JSONObject getPrice(String symbol) {
        String url = BASE_URL + "/ticker/price?symbol=" + symbol;
        String response = getResponse(url);
        if (response != null) {
            return new JSONObject(response);
        }else{
            return null;
        }
    }

    public JSONArray getHistoricalData(String symbol, String interval, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Long startTime = startDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        Long endTime = endDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        String url = BASE_URL + "/klines?symbol=" + symbol
                + "&interval=" + interval
                + "&startTime=" + startTime
                + "&endTime=" + endTime
                + "&limit=1000";
        String response = getResponse(url);
        if(response != null) {
            return new JSONArray(response);
        } else {
            return null;
        }

    }

    private String getResponse(String url){
        try {

            // HTTP 연결 설정
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            log.info("fetching: " + url);
            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 응답 읽기
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();


                return response.toString();
            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
