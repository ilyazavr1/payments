package ua.epam.payments.payments.model.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Payment {


    private long id;
    private int balance;
    private int balanceDestination;
    private int money;
    private int paymentStatusId;
    private LocalDateTime creationTimestamp;
    private long cardSenderId;
    private long cardDestinationId;


    private long userId;
    private long userDestinationId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalanceDestination() {
        return balanceDestination;
    }

    public void setBalanceDestination(int balanceDestination) {
        this.balanceDestination = balanceDestination;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPaymentStatusId() {
        return paymentStatusId;
    }

    public void setPaymentStatusId(int paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public long getCardSenderId() {
        return cardSenderId;
    }

    public void setCardSenderId(long cardSenderId) {
        this.cardSenderId = cardSenderId;
    }

    public long getCardDestinationId() {
        return cardDestinationId;
    }

    public void setCardDestinationId(long cardDestinationId) {
        this.cardDestinationId = cardDestinationId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserDestinationId() {
        return userDestinationId;
    }

    public void setUserDestinationId(long userDestinationId) {
        this.userDestinationId = userDestinationId;
    }

    public static class Builder {
        private final Payment newPayment;

        public Builder() {
            newPayment = new Payment();
        }

        public Payment.Builder withId(long id) {
            newPayment.id = id;
            return this;
        }

        public Payment.Builder withBalance(int balance) {
            newPayment.balance = balance;
            return this;
        }
        public Payment.Builder withBalanceDestination(int balanceDestination) {
            newPayment.balanceDestination = balanceDestination;
            return this;
        }

        public Payment.Builder withMoney(int money) {
            newPayment.money = money;
            return this;
        }

        public Payment.Builder withPaymentStatusId(int paymentStatusId) {
            newPayment.paymentStatusId = paymentStatusId;
            return this;
        }

        public Payment.Builder withCreationTimestamp(LocalDateTime creationTimestamp) {
            newPayment.creationTimestamp = creationTimestamp;
            return this;
        }

        public Payment.Builder withCreationTimestamp(Timestamp timestamp) {
            newPayment.creationTimestamp = timestamp.toLocalDateTime();
            return this;
        }

        public Payment.Builder withCardSenderId(long cardSenderId) {
            newPayment.cardSenderId = cardSenderId;
            return this;
        }

        public Payment.Builder withCardDestinationId(long cardDestinationId) {
            newPayment.cardDestinationId = cardDestinationId;
            return this;
        }

        public Payment.Builder withUserId(long userId) {
            newPayment.userId = userId;
            return this;
        }
        public Payment.Builder withUserDestinationId(long userDestinationId) {
            newPayment.userDestinationId = userDestinationId;
            return this;
        }
        public Payment build() {
            return newPayment;
        }


    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", balance=" + balance +
                ", money=" + money +
                ", paymentStatusId=" + paymentStatusId +
                ", creationTimestamp=" + creationTimestamp +
                ", cardSenderId=" + cardSenderId +
                ", cardDestinationId=" + cardDestinationId +
                ", userId=" + userId +
                ", userDestinationId=" + userDestinationId +
                '}';
    }


}



