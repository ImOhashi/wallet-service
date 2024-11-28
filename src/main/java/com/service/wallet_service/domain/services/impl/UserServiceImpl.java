package com.service.wallet_service.domain.services.impl;

import com.service.wallet_service.application.web.dto.request.UserRequestDTO;
import com.service.wallet_service.domain.entities.User;
import com.service.wallet_service.domain.exceptions.UserAlreadyExistsException;
import com.service.wallet_service.domain.services.UserService;
import com.service.wallet_service.resources.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void create(UserRequestDTO userRequestDTO) {
        logger.info("Received request to create new user with cpf=" + userRequestDTO.cpf());

        this.userRepository.findByCpf(userRequestDTO.cpf()).ifPresent(user -> {
            logger.error("User with cpf=" + userRequestDTO.cpf() + " already exists");
            throw new UserAlreadyExistsException("User with cpf=" + userRequestDTO.cpf() + " already exists");
        });

        var newUser = new User
                .Builder()
                .setName(userRequestDTO.name())
                .setSurname(userRequestDTO.surname())
                .setCpf(userRequestDTO.cpf())
                .setEmail(userRequestDTO.email())
                .setPhone(userRequestDTO.phone())
                .build();

        logger.info("User created");
        this.userRepository.save(newUser);
    }
}
