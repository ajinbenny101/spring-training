package com.training.banking.controller;

import com.training.banking.dto.ConversionResponse;
import com.training.banking.dto.ErrorResponse;
import com.training.banking.service.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConversionController {

    private final ExchangeService exchangeService;

    public ConversionController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/api/convert")
    public ResponseEntity<?> convert(@RequestParam String from,
                                     @RequestParam String to,
                                     @RequestParam double amount) {

        try {
            double rate = exchangeService.getRate(from, to);
            double convertedAmount = amount * rate;

            ConversionResponse response = new ConversionResponse(
                    from, to, amount, rate, convertedAmount
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("CONVERSION_ERROR",
                            "Could not convert from " + from + " to " + to));
        }
    }
}
