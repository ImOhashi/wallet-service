package com.service.wallet_service.application.web.dto.response;

import com.service.wallet_service.domain.entities.WalletHistory;

import java.time.LocalDateTime;

public record WalletHistoryResponseDTO(
        String cpf,
        String operationType,
        Double amount,
        Double previousBalance,
        Double currentBalance,
        LocalDateTime operationDate,
        String description
) {
    public WalletHistoryResponseDTO(WalletHistory walletHistory) {
        this(
                walletHistory.getUserCpf(),
                walletHistory.getOperationType(),
                walletHistory.getAmount(),
                walletHistory.getPreviousBalance(),
                walletHistory.getCurrentBalance(),
                walletHistory.getOperationDate(),
                walletHistory.getDescription()
        );
    }
}
