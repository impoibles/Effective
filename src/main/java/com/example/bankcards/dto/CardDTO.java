package com.example.bankcards.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.NoArgsConstructor;

public class CardDTO {
    @NoArgsConstructor
    public static class CardRequest {
        @NotNull(message = "ID пользователя обязательно")
        private Long userId;

        @Positive(message = "Баланс должен быть положительным")
        private int initialBalance;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public int getInitialBalance() {
            return initialBalance;
        }

        public void setInitialBalance(int initialBalance) {
            this.initialBalance = initialBalance;
        }
    }

    @NoArgsConstructor
    public static class CardResponse {
        private Long id;
        private String cardNumber;
        private int balance;
        private String ownerName;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }
    }
}
