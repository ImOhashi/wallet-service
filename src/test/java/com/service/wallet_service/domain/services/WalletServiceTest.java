package com.service.wallet_service.domain.services;

import com.service.wallet_service.domain.exceptions.UserNotFoundException;
import com.service.wallet_service.domain.exceptions.WalletAlreadyExistsException;
import com.service.wallet_service.factories.UserFactory;
import com.service.wallet_service.factories.WalletFactory;
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

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WalletServiceTest {

    @Autowired
    private WalletService walletService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Test add wallet - Success")
    void testAddWalletWithSuccess() {
        var mockWalletRequestDto = WalletFactory.sampleWalletRequestDTO();
        var mockUser = UserFactory.sample();

        doReturn(Optional.of(mockUser)).when(userRepository).findByCpf(mockWalletRequestDto.userCpf());

        walletService.addWallet(mockWalletRequestDto);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Test add wallet but user already contains wallet - Error")
    void testAddWalletButAlreadyExists() {
        var mockWalletRequestDto = WalletFactory.sampleWalletRequestDTO();
        var mockUser = UserFactory.sampleWithWallet();

        doReturn(Optional.of(mockUser)).when(userRepository).findByCpf(mockWalletRequestDto.userCpf());

        Assertions.assertThrows(WalletAlreadyExistsException.class, () -> walletService.addWallet(mockWalletRequestDto));
    }

    @Test
    @DisplayName("Test add wallet but user not found - Error")
    void testAddWalletButUserNotFound() {
        var mockWalletRequestDto = WalletFactory.sampleWalletRequestDTO();

        doReturn(Optional.empty()).when(userRepository).findByCpf(mockWalletRequestDto.userCpf());

        Assertions.assertThrows(UserNotFoundException.class, () -> walletService.addWallet(mockWalletRequestDto));
    }
}
