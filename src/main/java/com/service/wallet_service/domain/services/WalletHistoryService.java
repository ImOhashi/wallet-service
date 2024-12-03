package com.service.wallet_service.domain.services;

import com.service.wallet_service.domain.entities.WalletHistory;

import java.time.LocalDateTime;
import java.util.List;

public interface WalletHistoryService {
    List<WalletHistory> getWalletHistoryByDate(String cpf, LocalDateTime date);

    List<WalletHistory> getWalletHistoryByDateRange(String cpf, LocalDateTime startDate, LocalDateTime endDate);

    List<WalletHistory> getWalletHistoryBeforeDate(String cpf, LocalDateTime date);

    List<WalletHistory> getWalletHistoryAfterDate(String cpf, LocalDateTime date);
}
