package com.service.wallet_service.application.web.dto.request;

public record UserRequestDTO(
        String name,
        String surname,
        String cpf,
        String email,
        String phone
) {
}
