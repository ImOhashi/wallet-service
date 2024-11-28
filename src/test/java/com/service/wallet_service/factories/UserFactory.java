package com.service.wallet_service.factories;

import com.service.wallet_service.application.web.dto.request.UserRequestDTO;
import com.service.wallet_service.application.web.dto.request.UserResponseDTO;
import com.service.wallet_service.domain.entities.User;

import java.util.List;

public class UserFactory {
    public static User sample() {
        return new User
                .Builder()
                .setName("Name")
                .setSurname("Surname")
                .setCpf("12345678")
                .setEmail("teste@teste.com")
                .setPhone("11912344321")
                .build();
    }

    public static UserRequestDTO sampleUserRequestDTO() {
        return new UserRequestDTO(
                "Name",
                "Surname",
                "12345678",
                "teste@teste.com",
                "11912344321"
        );
    }

    public static UserResponseDTO sampleUserResponseDTO() {
        return new UserResponseDTO(
                UserFactory.sample()
        );
    }
}
