package com.service.wallet_service.domain.services.impl;

import com.service.wallet_service.domain.entities.WalletHistory;
import com.service.wallet_service.domain.services.WalletHistoryService;
import com.service.wallet_service.resources.repository.WalletHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WalletHistoryServiceImpl implements WalletHistoryService {

    private final WalletHistoryRepository walletHistoryRepository;

    public WalletHistoryServiceImpl(WalletHistoryRepository walletHistoryRepository) {
        this.walletHistoryRepository = walletHistoryRepository;
    }

    public List<WalletHistory> getWalletHistoryByDate(String cpf, LocalDateTime date) {
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        return walletHistoryRepository.findAllByUserCpfAndOperationDateBetween(cpf, startOfDay, endOfDay);
    }

    public List<WalletHistory> getWalletHistoryByDateRange(String cpf, LocalDateTime startDate, LocalDateTime endDate) {
        return walletHistoryRepository.findAllByUserCpfAndOperationDateBetween(cpf, startDate, endDate);
    }

    public List<WalletHistory> getWalletHistoryBeforeDate(String cpf, LocalDateTime date) {
        return walletHistoryRepository.findAllByUserCpfAndOperationDateBefore(cpf, date);
    }

    public List<WalletHistory> getWalletHistoryAfterDate(String cpf, LocalDateTime date) {
        return walletHistoryRepository.findAllByUserCpfAndOperationDateAfter(cpf, date);
    }
}
