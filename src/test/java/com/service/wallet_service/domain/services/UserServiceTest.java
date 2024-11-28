package com.service.wallet_service.domain.services;

import com.service.wallet_service.domain.exceptions.UserAlreadyExistsException;
import com.service.wallet_service.factories.UserFactory;
import com.service.wallet_service.resources.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Test create - Success")
    void testCreateSuccess() {
        var mockUser = UserFactory.sample();
        var mockUserDTO = UserFactory.sampleUserRequestDTO();

        doReturn(Optional.empty()).when(userRepository).findByCpf(any());
        doReturn(mockUser).when(userRepository).save(any());

        var createdUser = userService.create(mockUserDTO);

        Assertions.assertEquals(createdUser.getName(), mockUser.getName());
        Assertions.assertEquals(createdUser.getSurname(), mockUser.getSurname());
        Assertions.assertEquals(createdUser.getCpf(), mockUser.getCpf());
        Assertions.assertEquals(createdUser.getEmail(), mockUser.getEmail());
        Assertions.assertEquals(createdUser.getPhone(), mockUser.getPhone());
    }

    @Test
    @DisplayName("Test error - Already Exists User")
    void testCreateWithErrorAlreadyExistsUSer() {
        var mockUser = UserFactory.sample();
        var mockUserDTO = UserFactory.sampleUserRequestDTO();

        doReturn(Optional.of(mockUser)).when(userRepository).findByCpf(any());

        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.create(mockUserDTO));
    }
}
