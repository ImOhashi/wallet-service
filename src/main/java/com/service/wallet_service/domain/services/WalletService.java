package com.service.wallet_service.domain.services;

import com.service.wallet_service.application.web.dto.request.AmountRequestDTO;
import com.service.wallet_service.application.web.dto.request.TransferRequestDTO;
import com.service.wallet_service.application.web.dto.request.WalletRequestDTO;
import com.service.wallet_service.domain.entities.TransferInformation;
import com.service.wallet_service.domain.entities.User;
import com.service.wallet_service.domain.entities.Wallet;

public interface WalletService {

    void addWallet(WalletRequestDTO walletRequestDTO);

    Wallet retrieveBalance(String cpf);

    void depositAmount(String cpf, AmountRequestDTO amountRequestDTO);

    void withdrawAmount(String cpf, AmountRequestDTO amountRequestDTO);

//    TransferInformation transferFunds(String from, TransferRequestDTO transferRequestDTO);
}
