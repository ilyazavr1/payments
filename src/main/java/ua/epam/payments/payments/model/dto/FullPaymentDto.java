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
    private String senderFirstName;
    private String senderLastName;
    private String senderSurnameName;
    private String destinationFirstName;
    private String destinationLastName;
    private String destinationSurnameName;
    private String destinationFullName;
    private String senderFullName;

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

    public String getSenderFirstName() {
        return senderFirstName;
    }

    public void setSenderFirstName(String senderFirstName) {
        this.senderFirstName = senderFirstName;
    }

    public String getSenderLastName() {
        return senderLastName;
    }

    public void setSenderLastName(String senderLastName) {
        this.senderLastName = senderLastName;
    }

    public String getSenderSurnameName() {
        return senderSurnameName;
    }

    public void setSenderSurnameName(String senderSurnameName) {
        this.senderSurnameName = senderSurnameName;
    }

    public String getDestinationFirstName() {
        return destinationFirstName;
    }

    public void setDestinationFirstName(String destinationFirstName) {
        this.destinationFirstName = destinationFirstName;
    }

    public String getDestinationLastName() {
        return destinationLastName;
    }

    public void setDestinationLastName(String destinationLastName) {
        this.destinationLastName = destinationLastName;
    }

    public String getDestinationSurnameName() {
        return destinationSurnameName;
    }

    public void setDestinationSurnameName(String destinationSurnameName) {
        this.destinationSurnameName = destinationSurnameName;
    }

    public String getDestinationFullName() {
        return destinationFullName;
    }

    public void setDestinationFullName(String destinationFullName) {
        this.destinationFullName = destinationFullName;
    }

    public String getSenderFullName() {
        return senderFullName;
    }

    public void setSenderFullName(String senderFullName) {
        this.senderFullName = senderFullName;
    }

    public static class Builder {
        private final FullPaymentDto newFullPayment;

        public Builder() {
            newFullPayment = new FullPaymentDto();
        }

        public FullPaymentDto.Builder withId(long id) {
            newFullPayment.id = id;
            return this;
        }

        public FullPaymentDto.Builder withMoney(int money) {
            newFullPayment.money = money;
            return this;
        }

        public FullPaymentDto.Builder withPaymentStatus(String status) {
            newFullPayment.status = status;
            return this;
        }

        public FullPaymentDto.Builder withCreationTimestamp(LocalDateTime creationTimestamp) {
            newFullPayment.creationTimestamp = creationTimestamp;
            return this;
        }

        public FullPaymentDto.Builder withCreationTimestamp(Timestamp timestamp) {
            newFullPayment.creationTimestamp = timestamp.toLocalDateTime();
            return this;
        }

        public FullPaymentDto.Builder withCardSenderNumber(String senderCardNumber) {
            newFullPayment.senderCardNumber = senderCardNumber;
            return this;
        }

        public FullPaymentDto.Builder withCardDestinationNumber(String destinationCardNumber) {
            newFullPayment.destinationCardNumber = destinationCardNumber;
            return this;
        }
        public FullPaymentDto.Builder withUserSenderFullName(String fName, String lName, String surname) {
            newFullPayment.senderFullName = String.format("%s %s %s", lName,fName,surname);
            return this;
        }
        public FullPaymentDto.Builder withUserDestinationFullName(String fName, String lName, String surname) {
            newFullPayment.destinationFullName = String.format("%s %s %s", lName,fName,surname);
            return this;
        }
        public FullPaymentDto build() {
            return newFullPayment;
        }


    }

    @Override
    public String toString() {
        return "FullPaymentDto{" +
                "id=" + id +
                ", money=" + money +
                ", status='" + status + '\'' +
                ", creationTimestamp=" + creationTimestamp +
                ", senderCardNumber='" + senderCardNumber + '\'' +
                ", destinationCardNumber='" + destinationCardNumber + '\'' +
                ", senderFirstName='" + senderFirstName + '\'' +
                ", senderLastName='" + senderLastName + '\'' +
                ", senderSurnameName='" + senderSurnameName + '\'' +
                ", destinationFirstName='" + destinationFirstName + '\'' +
                ", destinationLastName='" + destinationLastName + '\'' +
                ", destinationSurnameName='" + destinationSurnameName + '\'' +
                ", destinationFullName='" + destinationFullName + '\'' +
                ", senderFullName='" + senderFullName + '\'' +
                '}';
    }
}
