package com.service.wallet_service.application.web.dto.response;

import com.service.wallet_service.domain.entities.User;
import com.service.wallet_service.domain.entities.Wallet;

import java.util.List;

public record UserResponseDTO(
        String name,
        String surname,
        String email,
        String phone,
        List<Wallet> wallets
) {
    public UserResponseDTO(User user) {
        this(
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPhone(),
                user.getWallets()
        );
    }
}
