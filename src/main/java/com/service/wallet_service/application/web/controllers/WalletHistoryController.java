package com.service.wallet_service.application.web.controllers;

import com.service.wallet_service.application.web.dto.response.WalletHistoryResponseDTO;
import com.service.wallet_service.domain.services.WalletHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/wallet/history")
public class WalletHistoryController {

    private final WalletHistoryService walletHistoryService;

    public WalletHistoryController(WalletHistoryService walletHistoryService) {
        this.walletHistoryService = walletHistoryService;
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<WalletHistoryResponseDTO>> getWalletHistoryByDate(
            @RequestParam String cpf,
            @RequestParam String date
    ) {
        LocalDateTime parsedDate = LocalDateTime.parse(date);

        var result = walletHistoryService.getWalletHistoryByDate(cpf, parsedDate);
        var responseList = result.stream().map(WalletHistoryResponseDTO::new);

        return ResponseEntity.ok(responseList.toList());
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<List<WalletHistoryResponseDTO>> getWalletHistoryByDateRange(
            @RequestParam String cpf,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        LocalDateTime parsedStartDate = LocalDateTime.parse(startDate);
        LocalDateTime parsedEndDate = LocalDateTime.parse(endDate);

        var result = walletHistoryService.getWalletHistoryByDateRange(cpf, parsedStartDate, parsedEndDate);
        var responseList = result.stream().map(WalletHistoryResponseDTO::new);

        return ResponseEntity.ok(responseList.toList());
    }

    @GetMapping("/before-date")
    public ResponseEntity<List<WalletHistoryResponseDTO>> getWalletHistoryBeforeDate(
            @RequestParam String cpf,
            @RequestParam String date) {
        LocalDateTime parsedDate = LocalDateTime.parse(date);

        var result = walletHistoryService.getWalletHistoryBeforeDate(cpf, parsedDate);
        var responseList = result.stream().map(WalletHistoryResponseDTO::new);

        return ResponseEntity.ok(responseList.toList());
    }

    @GetMapping("/after-date")
    public ResponseEntity<List<WalletHistoryResponseDTO>> getWalletHistoryAfterDate(
            @RequestParam String cpf,
            @RequestParam String date
    ) {
        LocalDateTime parsedDate = LocalDateTime.parse(date);

        var result = walletHistoryService.getWalletHistoryAfterDate(cpf, parsedDate);
        var responseList = result.stream().map(WalletHistoryResponseDTO::new);

        return ResponseEntity.ok(responseList.toList());
    }
}
