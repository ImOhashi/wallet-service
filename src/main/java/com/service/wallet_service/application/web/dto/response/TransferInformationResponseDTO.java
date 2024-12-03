package com.service.wallet_service.application.web.dto.response;

import com.service.wallet_service.domain.entities.TransferInformation;

public record TransferInformationResponseDTO(String from, String to, Double amount) {
    public TransferInformationResponseDTO(TransferInformation transferInformation) {
        this(transferInformation.from(), transferInformation.to(), transferInformation.amount());
    }
}
