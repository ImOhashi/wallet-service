package com.service.wallet_service.domain.services;

import com.service.wallet_service.application.web.dto.request.AmountRequestDTO;
import com.service.wallet_service.application.web.dto.request.TransferRequestDTO;
import com.service.wallet_service.domain.exceptions.*;
import com.service.wallet_service.factories.AmountRequestDTOFactory;
import com.service.wallet_service.factories.UserFactory;
import com.service.wallet_service.factories.WalletFactory;
import com.service.wallet_service.factories.WalletHistoryFactory;
import com.service.wallet_service.resources.repository.UserRepository;
import com.service.wallet_service.resources.repository.WalletHistoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WalletServiceTest {

    @Autowired
    private WalletService walletService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private WalletHistoryRepository walletHistoryRepository;

    @Test
    @DisplayName("Test add wallet - Success")
    void testAddWalletWithSuccess() {
        var mockWalletRequestDto = WalletFactory.sampleWalletRequestDTO();
        var mockUser = UserFactory.sample();
        var mockWalletHistory = WalletHistoryFactory.sample();

        doReturn(Optional.of(mockUser)).when(userRepository).findByCpf(mockWalletRequestDto.userCpf());
        doReturn(mockWalletHistory).when(walletHistoryRepository).save(any());

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

    @Test
    @DisplayName("Test retrieve wallet information - Success")
    void testRetrieveWallet() {
        var mockUser = UserFactory.sampleWithWallet();
        var mockWallet = mockUser.getWallets().getFirst();

        doReturn(Optional.of(mockUser)).when(userRepository).findByCpf(any());

        var result = walletService.retrieveBalance(mockUser.getCpf());

        assertEquals(result.title(), mockWallet.title());
        assertEquals(result.description(), mockWallet.description());
        assertEquals(result.amount(), mockWallet.amount());
    }

    @Test
    @DisplayName("Test retrieve wallet but user not found - Error")
    void testRetrieveWalletButUserNotFound() {
        var mockUser = UserFactory.sample();

        doReturn(Optional.empty()).when(userRepository).findByCpf(mockUser.getCpf());

        Assertions.assertThrows(UserNotFoundException.class, () -> walletService.retrieveBalance(mockUser.getCpf()));
    }

    @Test
    @DisplayName("Test deposit amount - Success")
    void testDepositAmount() {
        var mockUser = UserFactory.sampleWithWallet();
        var mockAmountRequestDTO = AmountRequestDTOFactory.sample();
        var mockWalletHistory = WalletHistoryFactory.sample();

        doReturn(Optional.of(mockUser)).when(userRepository).findByCpf(any());
        doReturn(mockUser).when(userRepository).save(any());
        doReturn(mockWalletHistory).when(walletHistoryRepository).save(any());

        var result = walletService.depositAmount(mockUser.getCpf(), mockAmountRequestDTO);

        assertEquals(result.getCpf(), mockUser.getCpf());
        assertEquals(result.getWallets().getFirst().amount(), 20);
    }

    @Test
    @DisplayName("Test deposit amount but user don't have wallet - Error")
    void testDepositAmountWithoutWallet() {
        var mockUser = UserFactory.sample();
        var mockAmountRequestDTO = AmountRequestDTOFactory.sample();

        doReturn(Optional.of(mockUser)).when(userRepository).findByCpf(any());

        assertThrows(WalletNotExistsException.class, () -> walletService.depositAmount(mockUser.getCpf(), mockAmountRequestDTO));
    }

    @Test
    @DisplayName("Test deposit amount but user not found - Error")
    void testDepositAmountUserNotFound() {
        var mockUser = UserFactory.sample();
        var mockAmountRequestDTO = AmountRequestDTOFactory.sample();

        doReturn(Optional.empty()).when(userRepository).findByCpf(any());

        assertThrows(UserNotFoundException.class, () -> walletService.depositAmount(mockUser.getCpf(), mockAmountRequestDTO));
    }

    @Test
    @DisplayName("Test deposit amount with a invalid amount - Error")
    void testDepositAmountWithInvalidAmount() {
        var mockUser = UserFactory.sampleWithWallet();

        doReturn(Optional.of(mockUser)).when(userRepository).findByCpf(any());

        assertThrows(InvalidAmountValueException.class, () -> walletService.depositAmount(mockUser.getCpf(), new AmountRequestDTO(-1.0)));
    }

    @Test
    @DisplayName("Test withdraw amount - Success")
    void testWithdrawAmount() {
        var mockUser = UserFactory.sampleWithWallet();
        var mockAmountRequestDTO = AmountRequestDTOFactory.sample();
        var mockWalletHistory = WalletHistoryFactory.sample();

        doReturn(Optional.of(mockUser)).when(userRepository).findByCpf(any());
        doReturn(mockUser).when(userRepository).save(any());
        doReturn(mockWalletHistory).when(walletHistoryRepository).save(any());

        var result = walletService.withdrawAmount(mockUser.getCpf(), mockAmountRequestDTO);

        assertEquals(result.getCpf(), mockUser.getCpf());
        assertEquals(result.getWallets().getFirst().amount(), 0);
    }

    @Test
    @DisplayName("Test withdraw amount but user don't have wallet - Error")
    void testWithdrawAmountWithoutWallet() {
        var mockUser = UserFactory.sample();
        var mockAmountRequestDTO = AmountRequestDTOFactory.sample();

        doReturn(Optional.of(mockUser)).when(userRepository).findByCpf(any());

        assertThrows(WalletNotExistsException.class, () -> walletService.withdrawAmount(mockUser.getCpf(), mockAmountRequestDTO));
    }

    @Test
    @DisplayName("Test withdraw amount but user not found - Error")
    void testWithdrawAmountButUserNotFound() {
        var mockUser = UserFactory.sample();
        var mockAmountRequestDTO = AmountRequestDTOFactory.sample();

        doReturn(Optional.empty()).when(userRepository).findByCpf(any());

        assertThrows(UserNotFoundException.class, () -> walletService.withdrawAmount(mockUser.getCpf(), mockAmountRequestDTO));
    }

    @Test
    @DisplayName("Test withdraw amount with invalid amount value - Error")
    void testWithdrawAmountInvalidAmountValue() {
        var mockUser = UserFactory.sampleWithWallet();

        doReturn(Optional.of(mockUser)).when(userRepository).findByCpf(any());

        assertThrows(SubtractAmountNotIsPossibleException.class, () -> walletService.withdrawAmount(mockUser.getCpf(), new AmountRequestDTO(100.0)));
    }

    @Test
    @DisplayName("Transfer value - Success")
    void transferValue() {
        var mockFromUser = UserFactory.sampleWithWallet();
        var mockToUser = UserFactory.sampleWithWallet();

        doReturn(Optional.of(mockFromUser)).when(userRepository).findByCpf("123456");
        doReturn(Optional.of(mockToUser)).when(userRepository).findByCpf("654321");

        doReturn(mockFromUser).when(userRepository).save(mockFromUser);
        doReturn(mockToUser).when(userRepository).save(mockToUser);

        walletService.transferFunds("123456", new TransferRequestDTO("654321", 10.0));
    }
}
