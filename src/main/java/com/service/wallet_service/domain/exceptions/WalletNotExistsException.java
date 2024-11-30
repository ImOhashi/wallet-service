package com.service.wallet_service.domain.exceptions;

public class WalletNotExistsException extends RuntimeException {
    public WalletNotExistsException(String message) {
        super(message);
    }
}
