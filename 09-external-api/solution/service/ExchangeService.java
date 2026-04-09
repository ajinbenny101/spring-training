package com.training.banking.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ExchangeService {

    private final RestTemplate restTemplate;

    public ExchangeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double getRate(String from, String to) {
        String url = "https://open.er-api.com/v6/latest/" + from;

        Map response = restTemplate.getForObject(url, Map.class);

        if (response == null) {
            throw new RuntimeException("No response from API");
        }

        Map<String, Object> rates = (Map<String, Object>) response.get("rates");

        if (rates == null || !rates.containsKey(to)) {
            throw new RuntimeException("Currency not found: " + to);
        }

        return ((Number) rates.get(to)).doubleValue();
    }
}
