package com.service.wallet_service.application.web.controllers.handler;

import com.service.wallet_service.application.web.controllers.handler.exceptions.ApiErrorMessage;
import com.service.wallet_service.domain.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, userAlreadyExistsException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.NOT_FOUND, userNotFoundException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleWalletAlreadyExistsException(WalletAlreadyExistsException walletAlreadyExistsException) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.CONFLICT, walletAlreadyExistsException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleWalletNotExistsException(WalletNotExistsException walletNotExistsException) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.NOT_ACCEPTABLE, walletNotExistsException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleRuntimeException(RuntimeException runtimeException) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, runtimeException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleInvalidAmountValueException(
            InvalidAmountValueException invalidAmountValueException
    ) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, invalidAmountValueException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleSubtractAmountNotIsPossibleException(
            SubtractAmountNotIsPossibleException subtractAmountNotIsPossibleException
    ) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, subtractAmountNotIsPossibleException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }
}
