package com.service.wallet_service.factories;

import com.service.wallet_service.application.web.dto.request.WalletRequestDTO;
import com.service.wallet_service.domain.entities.Wallet;

public class WalletFactory {

    public static Wallet sample() {
        return new Wallet(
                "title",
                "description",
                10.0
        );
    }

    public static WalletRequestDTO sampleWalletRequestDTO() {
        return new WalletRequestDTO(
                "12345678",
                "title",
                "description",
                10.00
        );
    }
}
