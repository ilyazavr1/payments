package ua.epam.payments.payments.model.dto;

import ua.epam.payments.payments.model.entity.Payment;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class FullPaymentDto {
    private long id;
    private int money;
    private String status;
    private LocalDateTime creationTimestamp;
    private String senderCardNumber;
    private String destinationCardNumber;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getSenderCardNumber() {
        return senderCardNumber;
    }

    public void setSenderCardNumber(String senderCardNumber) {
        this.senderCardNumber = senderCardNumber;
    }

    public String getDestinationCardNumber() {
        return destinationCardNumber;
    }

    public void setDestinationCardNumber(String destinationCardNumber) {
        this.destinationCardNumber = destinationCardNumber;
    }
    public static class Builder {
        private final FullPaymentDto newFullPayment;

        public Builder() {
            newFullPayment = new FullPaymentDto();
        }

        public FullPaymentDto.Builder withId(long id){
            newFullPayment.id = id;
            return this;
        }

        public FullPaymentDto.Builder withMoney(int money){
            newFullPayment.money = money;
            return this;
        }

        public FullPaymentDto.Builder withPaymentStatus(String status){
            newFullPayment.status = status;
            return this;
        }

        public FullPaymentDto.Builder withCreationTimestamp(LocalDateTime creationTimestamp){
            newFullPayment.creationTimestamp = creationTimestamp;
            return this;
        }
        public FullPaymentDto.Builder withCreationTimestamp(Timestamp timestamp) {
            newFullPayment.creationTimestamp = timestamp.toLocalDateTime();
            return this;
        }
        public FullPaymentDto.Builder withCardSenderNumber(String senderCardNumber){
            newFullPayment.senderCardNumber = senderCardNumber;
            return this;
        }

        public FullPaymentDto.Builder withCardDestinationNumber(String destinationCardNumber){
            newFullPayment.destinationCardNumber = destinationCardNumber;
            return this;
        }

        public FullPaymentDto build(){
            return newFullPayment;
        }


    }
}
/*
    private long id;
    private int money;
    private String status;
    private LocalDateTime creationTimestamp;
    private String senderCardNumber;
    private String destinationCardNumber;
*/
