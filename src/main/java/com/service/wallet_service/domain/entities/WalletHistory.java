package com.service.wallet_service.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("wallet_history")
public class WalletHistory {

    @Id
    private String id;

    private String userCpf;
    private String operationType;
    private Double amount;
    private Double previousBalance;
    private Double currentBalance;
    private LocalDateTime operationDate;
    private String description;

    public WalletHistory(String userCpf, String operationType, double amount, double previousBalance, double currentBalance, LocalDateTime operationDate, String description) {
        this.userCpf = userCpf;
        this.operationType = operationType;
        this.amount = amount;
        this.previousBalance = previousBalance;
        this.currentBalance = currentBalance;
        this.operationDate = operationDate;
        this.description = description;
    }

    public String getUserCpf() {
        return userCpf;
    }

    public String getOperationType() {
        return operationType;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getPreviousBalance() {
        return previousBalance;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public LocalDateTime getOperationDate() {
        return operationDate;
    }

    public String getDescription() {
        return description;
    }
}
