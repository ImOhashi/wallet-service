package com.service.wallet_service.application.web.dto.request;

public record WalletRequestDTO(String userCpf, String title, String description, Double amount) {
}
