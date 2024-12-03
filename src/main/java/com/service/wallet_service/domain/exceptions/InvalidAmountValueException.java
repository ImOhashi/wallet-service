package com.service.wallet_service.domain.exceptions;

public class InvalidAmountValueException extends RuntimeException {
    public InvalidAmountValueException(String message) {
        super(message);
    }
}
