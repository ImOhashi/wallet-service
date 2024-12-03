package com.service.wallet_service.resources.repository;

import com.service.wallet_service.domain.entities.WalletHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WalletHistoryRepository extends MongoRepository<WalletHistory, String> {

    List<WalletHistory> findAllByUserCpfAndOperationDateBetween(String userCpf, LocalDateTime startDate, LocalDateTime endDate);

    List<WalletHistory> findAllByUserCpfAndOperationDateBefore(String userCpf, LocalDateTime date);

    List<WalletHistory> findAllByUserCpfAndOperationDateAfter(String userCpf, LocalDateTime date);
}
