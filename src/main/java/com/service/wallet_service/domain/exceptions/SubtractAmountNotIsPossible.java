package com.service.wallet_service.domain.exceptions;

public class SubtractAmountNotIsPossible extends RuntimeException {
    public SubtractAmountNotIsPossible(String message) {
        super(message);
    }
}
