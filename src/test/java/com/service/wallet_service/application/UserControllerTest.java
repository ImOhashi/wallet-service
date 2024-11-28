package com.service.wallet_service.application;

import com.service.wallet_service.application.web.controllers.UserController;
import com.service.wallet_service.domain.services.UserService;
import com.service.wallet_service.factories.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("Test create - success")
    void testCreateSuccess() {
        var mockUser = UserFactory.sample();
        var mockUserRequestDTO = UserFactory.sampleUserRequestDTO();
        var mockUserResponseDTO = UserFactory.sampleUserResponseDTO();

        doReturn(mockUser).when(userService).create(mockUserRequestDTO);

        var createdUserDTO = userController.create(mockUserRequestDTO);

        Assertions.assertEquals(HttpStatus.CREATED, createdUserDTO.getStatusCode());

        Assertions.assertEquals(mockUserResponseDTO.name(), createdUserDTO.getBody().name());
        Assertions.assertEquals(mockUserResponseDTO.surname(), createdUserDTO.getBody().surname());
        Assertions.assertEquals(mockUserResponseDTO.email(), createdUserDTO.getBody().email());
        Assertions.assertEquals(mockUserResponseDTO.phone(), createdUserDTO.getBody().phone());
        Assertions.assertEquals(mockUserResponseDTO.wallets().stream().count(), createdUserDTO.getBody().wallets().stream().count());
    }
}
