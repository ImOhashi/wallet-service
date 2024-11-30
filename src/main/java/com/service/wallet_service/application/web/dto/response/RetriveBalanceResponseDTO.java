package com.service.wallet_service.application.web.dto.response;

import com.service.wallet_service.domain.entities.Wallet;

public record RetriveBalanceResponseDTO(String title, String description, Double amount) {
    public RetriveBalanceResponseDTO(Wallet wallet) {
        this(
                wallet.title(),
                wallet.description(),
                wallet.amount()
        );
    }
}
