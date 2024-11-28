package com.service.wallet_service.application.web.controllers.handler.exceptions;

import org.springframework.http.HttpStatus;

public record ApiErrorMessage(HttpStatus httpStatus, String error) {
}
