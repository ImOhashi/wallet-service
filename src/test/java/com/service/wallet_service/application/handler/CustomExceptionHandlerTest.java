package com.service.wallet_service.application.handler;

import com.service.wallet_service.application.web.controllers.handler.CustomExceptionHandler;
import com.service.wallet_service.domain.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomExceptionHandlerTest {

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    @Test
    void testCustomExceptionHandler() {
        var response = customExceptionHandler.handleUserAlreadyExistsException(new UserAlreadyExistsException(""));

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
