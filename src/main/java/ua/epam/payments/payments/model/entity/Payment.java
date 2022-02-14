package ua.epam.payments.payments.model.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Payment {
    private static final long serialVersionUID = 123L;

    private long id;
    private int money;
    private int paymentStatusId;
    private LocalDateTime creationTimestamp;
    private long accountSenderId;
    private long accountDestinationId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getAccountSenderId() {
        return accountSenderId;
    }

    public void setAccountSenderId(long accountSenderId) {
        this.accountSenderId = accountSenderId;
    }

    public long getAccountDestinationId() {
        return accountDestinationId;
    }

    public void setAccountDestinationId(long accountDestinationId) {
        this.accountDestinationId = accountDestinationId;
    }

    public static class Builder {
        private final Payment newPayment;

        public Builder() {
            newPayment = new Payment();
        }

        public Payment.Builder withId(long id){
            newPayment.id = id;
            return this;
        }

        public Payment.Builder withPaymentStatusId(int paymentStatusId){
            newPayment.paymentStatusId = paymentStatusId;
            return this;
        }

        public Payment.Builder withMoney(int money){
            newPayment.money = money;
            return this;
        }

        public Payment.Builder withCreationTimestamp(LocalDateTime creationTimestamp){
            newPayment.creationTimestamp = creationTimestamp;
            return this;
        }
        public Payment.Builder withCreationTimestamp(Timestamp timestamp) {
            newPayment.creationTimestamp = timestamp.toLocalDateTime();
            return this;
        }
        public Payment.Builder withAccountSenderId(long accountSenderId){
            newPayment.accountSenderId = accountSenderId;
            return this;
        }

        public Payment.Builder withAccountDestinationId(long accountDestinationId){
            newPayment.accountDestinationId = accountDestinationId;
            return this;
        }

        public Payment build(){
            return newPayment;
        }


    }

}



