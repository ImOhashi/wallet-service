package com.service.wallet_service.domain.exceptions;

public class SubtractAmountNotIsPossibleException extends RuntimeException {
    public SubtractAmountNotIsPossibleException(String message) {
        super(message);
    }
}
