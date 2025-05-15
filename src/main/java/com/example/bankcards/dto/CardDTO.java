package com.example.bankcards.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.NoArgsConstructor;



public class CardDTO {




    @NoArgsConstructor
    public static class CardRequest {


        @NotNull
        private String number;
        @Positive(message = "Баланс должен быть положительным")
        private int balance;
        @NotNull(message = "Дата")
        private String date_out;
        @NotNull(message = "Cтатус карты")
        private String status;
        @NotNull(message = "user_id")
        private  Long user_id;


        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public Long getUser_id() {
            return user_id;
        }

        public void setUser_id(Long user_id) {
            this.user_id = user_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDate_out() {
            return date_out;
        }

        public void setDate_out(String date_out) {
            this.date_out = date_out;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int initialBalance) {
            this.balance = initialBalance;
        }


    }

    @NoArgsConstructor
    public static class CardResponse {
        private Long id;
        private String cardNumber;
        private int balance;
        private String date_out;


        public String getDate_out() {
            return date_out;
        }

        public void setDate_out(String date_out) {
            this.date_out = date_out;
        }

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
    }
}
