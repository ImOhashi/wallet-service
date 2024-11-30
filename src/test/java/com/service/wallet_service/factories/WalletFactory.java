package com.service.wallet_service.factories;

import com.service.wallet_service.application.web.dto.request.WalletRequestDTO;

public class WalletFactory {
    public static WalletRequestDTO sampleWalletRequestDTO() {
        return new WalletRequestDTO(
                "12345678",
                "title",
                "description",
                10.00
        );
    }
}
