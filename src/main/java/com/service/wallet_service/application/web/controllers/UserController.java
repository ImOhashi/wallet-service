package com.service.wallet_service.application.web.controllers;

import com.service.wallet_service.application.web.dto.request.UserRequestDTO;
import com.service.wallet_service.domain.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserRequestDTO userRequestDTO) {
        this.userService.create(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
