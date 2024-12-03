package com.service.wallet_service.domain.services;

import com.service.wallet_service.factories.WalletHistoryFactory;
import com.service.wallet_service.resources.repository.WalletHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WalletHistoryTest {

    @Autowired
    private WalletHistoryService walletHistoryService;

    @MockitoBean
    private WalletHistoryRepository walletHistoryRepository;

    @Test
    @DisplayName("Get wallet history by date - Success")
    void getWalletHistoryByDateSuccess() {
        var mockWalletHistory = WalletHistoryFactory.sample();

        doReturn(List.of(mockWalletHistory)).when(walletHistoryRepository).findAllByUserCpfAndOperationDateBetween(any(), any(), any());

        var result = walletHistoryService.getWalletHistoryByDate("123456", LocalDateTime.now());

        assertEquals(result.getFirst().getAmount(), mockWalletHistory.getAmount());
        assertEquals(result.getFirst().getUserCpf(), mockWalletHistory.getUserCpf());
    }

    @Test
    @DisplayName("Get wallet history date range - Success")
    void getHistoryDateRangeSuccess() {
        var mockWalletHistory = List.of(WalletHistoryFactory.sample());

        doReturn(mockWalletHistory).when(walletHistoryRepository).findAllByUserCpfAndOperationDateBetween(any(), any(), any());

        var result = walletHistoryService.getWalletHistoryByDateRange("123456", LocalDateTime.now(), LocalDateTime.MAX);

        assertEquals(result.getFirst().getAmount(), mockWalletHistory.getFirst().getAmount());
        assertEquals(result.getFirst().getUserCpf(), mockWalletHistory.getFirst().getUserCpf());
    }

    @Test
    @DisplayName("Get wallet history before date - Success")
    void getWalletHistoryBeforeDateSuccess() {
        var mockWalletHistory = List.of(WalletHistoryFactory.sample());

        doReturn(mockWalletHistory).when(walletHistoryRepository).findAllByUserCpfAndOperationDateBefore(any(), any());

        var result = walletHistoryService.getWalletHistoryBeforeDate("123456", LocalDateTime.now());

        assertEquals(result.getFirst().getAmount(), mockWalletHistory.getFirst().getAmount());
        assertEquals(result.getFirst().getUserCpf(), mockWalletHistory.getFirst().getUserCpf());
    }

    @Test
    @DisplayName("Get wallet history after date - Success")
    void getWalletHistoryAfterDateSuccess() {
        var mockWalletHistory = List.of(WalletHistoryFactory.sample());

        doReturn(mockWalletHistory).when(walletHistoryRepository).findAllByUserCpfAndOperationDateAfter(any(), any());

        var result = walletHistoryService.getWalletHistoryAfterDate("123456", LocalDateTime.now());

        assertEquals(result.getFirst().getAmount(), mockWalletHistory.getFirst().getAmount());
        assertEquals(result.getFirst().getUserCpf(), mockWalletHistory.getFirst().getUserCpf());
    }
}
