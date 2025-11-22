package com.nequiApp.nequiApp.utils;

import org.springframework.stereotype.Component;

@Component
public class AccountNumberGenerator {

    public String generateAccountNumber() {
        Long number = (long) (Math.random() * 100_000_000);
        return "46" + String.format("%08d", number);
    }
}
