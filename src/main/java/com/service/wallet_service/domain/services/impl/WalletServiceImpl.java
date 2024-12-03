package com.service.wallet_service.domain.services.impl;

import com.service.wallet_service.application.web.dto.request.AmountRequestDTO;
import com.service.wallet_service.application.web.dto.request.TransferRequestDTO;
import com.service.wallet_service.application.web.dto.request.WalletRequestDTO;
import com.service.wallet_service.domain.entities.TransferInformation;
import com.service.wallet_service.domain.entities.User;
import com.service.wallet_service.domain.entities.Wallet;
import com.service.wallet_service.domain.entities.WalletHistory;
import com.service.wallet_service.domain.exceptions.*;
import com.service.wallet_service.domain.services.WalletService;
import com.service.wallet_service.resources.repository.UserRepository;
import com.service.wallet_service.resources.repository.WalletHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WalletServiceImpl implements WalletService {

    private static final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    private final UserRepository userRepository;
    private final WalletHistoryRepository walletHistoryRepository;

    public WalletServiceImpl(UserRepository userRepository, WalletHistoryRepository walletHistoryRepository) {
        this.userRepository = userRepository;
        this.walletHistoryRepository = walletHistoryRepository;
    }

    @Override
    public void addWallet(WalletRequestDTO walletRequestDTO) {
        userRepository.findByCpf(walletRequestDTO.userCpf()).ifPresentOrElse(user -> {
            logger.info("Trying adding wallet for the user with cpf={}", walletRequestDTO.userCpf());

            if (this.validateIfWalletExists(user)) {
                logger.error("Wallet already exists");
                throw new WalletAlreadyExistsException("Wallet already exists");
            }

            this.createWallet(user, walletRequestDTO);
        }, () -> {
            logger.error("User not found with cpf={}", walletRequestDTO.userCpf());
            throw new UserNotFoundException("User not found with cpf=" + walletRequestDTO.userCpf());
        });
    }

    @Override
    public Wallet retrieveBalance(String cpf) {
        return userRepository.findByCpf(cpf).map(user -> user.getWallets().getFirst())
                .orElseThrow(() -> {
                    logger.error("User not found with cpf={}", cpf);
                    return new UserNotFoundException("User not found with cpf=" + cpf);
                });
    }

    @Override
    public User depositAmount(String cpf, AmountRequestDTO amountRequestDTO) {
        return userRepository.findByCpf(cpf).map((user) -> {
            if (!validateIfWalletExists(user)) {
                logger.error("Wallet not exists for the cpf={}", cpf);
                throw new WalletNotExistsException("Wallet not exists for the cpf:" + cpf);
            }

            var updatedUser = sumWalletValue(amountRequestDTO, user);

            logger.info("Update wallet value with success");

            return userRepository.save(updatedUser);
        }).orElseThrow(() -> {
            logger.error("User not found with cpf={}", cpf);
            return new UserNotFoundException("User not found with cpf=" + cpf);
        });
    }

    @Override
    public User withdrawAmount(String cpf, AmountRequestDTO amountRequestDTO) {
        return userRepository.findByCpf(cpf).map((user) -> {
            if (!validateIfWalletExists(user)) {
                logger.error("Wallet not exists for the cpf={}", cpf);
                throw new WalletNotExistsException("Wallet not exists for the cpf:" + cpf);
            }

            var updatedUser = subtractAmount(amountRequestDTO, user);

            logger.info("Withdraw amount with succcess");

            return userRepository.save(updatedUser);
        }).orElseThrow(() -> {
            logger.error("User not found with cpf={}", cpf);
            return new UserNotFoundException("User not found with cpf=" + cpf);
        });
    }

    @Override
    public TransferInformation transferFunds(String from, TransferRequestDTO transferRequestDTO) {
        this.withdrawAmount(from, new AmountRequestDTO(transferRequestDTO.amount()));
        this.depositAmount(transferRequestDTO.to(), new AmountRequestDTO(transferRequestDTO.amount()));

        return new TransferInformation(from, transferRequestDTO.to(), transferRequestDTO.amount());
    }

    private User sumWalletValue(AmountRequestDTO amountRequestDTO, User user) {
        if (amountRequestDTO.amount() <= 0) {
            throw new InvalidAmountValueException("Invalid amount value");
        }

        var wallet = user.getWallets().getFirst();
        double previousBalance = wallet.amount();
        double currentBalance = Double.sum(wallet.amount(), amountRequestDTO.amount());

        user.getWallets().set(0, new Wallet(wallet.title(), wallet.description(), currentBalance));

        registerWalletHistory(
                user,
                "Depósito",
                amountRequestDTO.amount(),
                previousBalance,
                currentBalance,
                "Depósito realizado"
        );

        return user;
    }

    private User subtractAmount(AmountRequestDTO amountRequestDTO, User user) {
        var wallet = user.getWallets().getFirst();

        if (validateAmountSubtract(amountRequestDTO, user)) {
            logger.info("Value to subtract not is possible to user with cpf={}", user.getCpf());
            throw new SubtractAmountNotIsPossibleException("Value to subtract not is possible to user with cpf=" + user.getCpf());
        }

        double previousBalance = wallet.amount();
        double currentBalance = wallet.amount() - amountRequestDTO.amount();

        user.getWallets()
                .set(
                        0,
                        new Wallet(
                                wallet.title(),
                                wallet.description(),
                                currentBalance
                        )
                );

        registerWalletHistory(
                user,
                "Retirada",
                amountRequestDTO.amount(),
                previousBalance,
                currentBalance,
                "Retirada realizada"
        );

        return user;
    }

    private Boolean validateAmountSubtract(AmountRequestDTO amountRequestDTO, User user) {
        return user.getWallets().isEmpty() ||
                amountRequestDTO.amount() > user.getWallets().getFirst().amount() ||
                user.getWallets().getFirst().amount() <= 0;
    }

    private Boolean validateIfWalletExists(User user) {
        return !user.getWallets().isEmpty();
    }

    private void createWallet(User user, WalletRequestDTO walletRequestDTO) {
        var newWallet = new Wallet(
                walletRequestDTO.title(),
                walletRequestDTO.description(),
                walletRequestDTO.amount()
        );

        user.getWallets().add(newWallet);

        registerWalletHistory(
                user,
                "Criação",
                walletRequestDTO.amount(),
                0,
                walletRequestDTO.amount(),
                "Criação de nova carteira"
        );


        logger.info("Added wallet with success");

        userRepository.save(user);
    }

    private void registerWalletHistory(
            User user,
            String operationType,
            double amount,
            double previousBalance,
            double currentBalance,
            String description
    ) {
        var history = new WalletHistory(
                user.getCpf(),
                operationType,
                amount,
                previousBalance,
                currentBalance,
                LocalDateTime.now(),
                description
        );

        walletHistoryRepository.save(history);
    }
}
