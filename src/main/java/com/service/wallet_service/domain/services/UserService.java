package com.service.wallet_service.domain.services;

import com.service.wallet_service.application.web.dto.request.UserRequestDTO;
import com.service.wallet_service.domain.entities.User;

public interface UserService {
    void create(UserRequestDTO userRequestDTO);
}
