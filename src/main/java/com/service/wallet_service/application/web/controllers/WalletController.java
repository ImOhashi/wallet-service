package com.service.wallet_service.application.web.controllers;

import com.service.wallet_service.application.web.dto.request.AmountRequestDTO;
import com.service.wallet_service.application.web.dto.request.TransferRequestDTO;
import com.service.wallet_service.application.web.dto.request.WalletRequestDTO;
import com.service.wallet_service.application.web.dto.response.RetriveBalanceResponseDTO;
import com.service.wallet_service.application.web.dto.response.TransferInformationResponseDTO;
import com.service.wallet_service.domain.services.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity<Void> addWallet(@RequestBody WalletRequestDTO walletRequestDTO) {
        walletService.addWallet(walletRequestDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping
    public ResponseEntity<RetriveBalanceResponseDTO> retrieveBalance(@RequestParam String cpf) {
        var wallet = walletService.retrieveBalance(cpf);
        return ResponseEntity.ok(new RetriveBalanceResponseDTO(wallet));
    }

    @PatchMapping("/deposit")
    public ResponseEntity<Void> depositAmount(@RequestParam String cpf, @RequestBody AmountRequestDTO amountRequestDTO) {
        walletService.depositAmount(cpf, amountRequestDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PatchMapping("/withdraw")
    public ResponseEntity<Void> withdrawAmount(@RequestParam String cpf, @RequestBody AmountRequestDTO amountRequestDTO) {
        walletService.withdrawAmount(cpf, amountRequestDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferInformationResponseDTO> transfer(
            @RequestParam String from,
            @RequestBody TransferRequestDTO transferRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(
                        new TransferInformationResponseDTO(
                                walletService.transferFunds(from, transferRequestDTO)
                        )
                );
    }
}
