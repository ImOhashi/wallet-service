package com.service.wallet_service.factories;

import com.service.wallet_service.domain.entities.WalletHistory;

import java.time.LocalDateTime;

public class WalletHistoryFactory {
    public static WalletHistory sample() {
        return new WalletHistory(
                "123456",
                "Teste",
                10.0,
                0.0,
                10.0,
                LocalDateTime.now(),
                "Teste"
        );
    }
}
