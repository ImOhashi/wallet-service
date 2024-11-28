package com.service.wallet_service.application.web.controllers.handler;

import com.service.wallet_service.application.web.controllers.handler.exceptions.ApiErrorMessage;
import com.service.wallet_service.domain.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, userAlreadyExistsException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }
}
