package com.service.wallet_service.factories;

import com.service.wallet_service.application.web.dto.request.AmountRequestDTO;

public class AmountRequestDTOFactory {

    public static AmountRequestDTO sample() {
        return new AmountRequestDTO(10.0);
    }
}
