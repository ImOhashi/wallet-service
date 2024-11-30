package com.service.wallet_service.domain.services.impl;

import com.service.wallet_service.application.web.dto.request.AmountRequestDTO;
import com.service.wallet_service.application.web.dto.request.TransferRequestDTO;
import com.service.wallet_service.application.web.dto.request.WalletRequestDTO;
import com.service.wallet_service.domain.entities.TransferInformation;
import com.service.wallet_service.domain.entities.User;
import com.service.wallet_service.domain.entities.Wallet;
import com.service.wallet_service.domain.exceptions.SubtractAmountNotIsPossible;
import com.service.wallet_service.domain.exceptions.UserNotFoundException;
import com.service.wallet_service.domain.exceptions.WalletAlreadyExistsException;
import com.service.wallet_service.domain.exceptions.WalletNotExistsException;
import com.service.wallet_service.domain.services.WalletService;
import com.service.wallet_service.resources.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    private static final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    private final UserRepository userRepository;

    public WalletServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public void depositAmount(String cpf, AmountRequestDTO amountRequestDTO) {
        userRepository.findByCpf(cpf).ifPresentOrElse((user) -> {
            if (!validateIfWalletExists(user)) {
                logger.error("Wallet not exists for the cpf={}", cpf);
                throw new WalletNotExistsException("Wallet not exists for the cpf:" + cpf);
            }

            var updatedUser = sumWalletValue(amountRequestDTO, user);

            logger.info("Update wallet value with success");

            userRepository.save(updatedUser);
        }, () -> {
            logger.error("User not found with cpf={}", cpf);
            throw new UserNotFoundException("User not found with cpf=" + cpf);
        });
    }

    @Override
    public void withdrawAmount(String cpf, AmountRequestDTO amountRequestDTO) {
        userRepository.findByCpf(cpf).ifPresentOrElse((user) -> {
            if (!validateIfWalletExists(user)) {
                logger.error("Wallet not exists for the cpf={}", cpf);
                throw new WalletNotExistsException("Wallet not exists for the cpf:" + cpf);
            }

            var updatedUser = subtractAmount(amountRequestDTO, user);

            logger.info("Withdraw amount with succcess");

            userRepository.save(updatedUser);
        }, () -> {
            logger.error("User not found with cpf={}", cpf);
            throw new UserNotFoundException("User not found with cpf=" + cpf);
        });
    }

//    @Override
//    public TransferInformation transferFunds(String from, TransferRequestDTO transferRequestDTO) {
//
//    }

    private User sumWalletValue(AmountRequestDTO amountRequestDTO, User user) {
        var wallet = user.getWallets().getFirst();
        user.getWallets()
                .set(
                        0,
                        new Wallet(
                                wallet.title(),
                                wallet.description(),
                                Double.sum(wallet.amount(), amountRequestDTO.amount())
                        )
                );

        return user;
    }

    private User subtractAmount(AmountRequestDTO amountRequestDTO, User user) {
        var wallet = user.getWallets().getFirst();

        if (validateAmountSubtract(amountRequestDTO, user)) {
            logger.info("Value to subtract not is possible to user with cpf={}", user.getCpf());
            throw new SubtractAmountNotIsPossible("Value to subtract not is possible to user with cpf=" + user.getCpf());
        }

        user.getWallets()
                .set(
                        0,
                        new Wallet(
                                wallet.title(),
                                wallet.description(),
                                wallet.amount() - amountRequestDTO.amount()
                        )
                );

        return user;
    }

    private Boolean validateAmountSubtract(AmountRequestDTO amountRequestDTO, User user) {
        return amountRequestDTO.amount() > user.getWallets().getFirst().amount() ||
                user.getWallets().isEmpty() ||
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

        logger.info("Added wallet with success");

        userRepository.save(user);
    }
}
