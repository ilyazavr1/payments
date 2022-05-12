package ua.epam.payments.payments.model.entity.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class FullPaymentDto {
    private long id;
    private int money;
    private String status;
    private LocalDateTime creationTimestamp;
    private String senderCardNumber;
    private int senderBalance;
    private String destinationCardNumber;
    private String senderFirstName;
    private String senderLastName;
    private String senderSurname;
    private String destinationFirstName;
    private String destinationLastName;
    private String destinationSurname;
    private String destinationFullName;
    private String senderFullName;
    private long userId;
    private long userDestinationId;

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

    public int getSenderBalance() {
        return senderBalance;
    }

    public void setSenderBalance(int senderBalance) {
        this.senderBalance = senderBalance;
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

    public String getSenderSurname() {
        return senderSurname;
    }

    public void setSenderSurname(String senderSurname) {
        this.senderSurname = senderSurname;
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

    public String getDestinationSurname() {
        return destinationSurname;
    }

    public void setDestinationSurname(String destinationSurname) {
        this.destinationSurname = destinationSurname;
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

        public FullPaymentDto.Builder withSenderBalance(int senderBalance) {
            newFullPayment.senderBalance = senderBalance;
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

        public FullPaymentDto.Builder withUserDestinationFirstName(String destinationFirstName) {
            newFullPayment.destinationFirstName = destinationFirstName;
            return this;
        }
        public FullPaymentDto.Builder withUserDestinationLastName(String destinationLastName) {
            newFullPayment.destinationLastName = destinationLastName;
            return this;
        }
        public FullPaymentDto.Builder withUserDestinationSurname(String destinationSurname) {
            newFullPayment.destinationSurname = destinationSurname;
            return this;
        }

        public FullPaymentDto.Builder withUserSenderFirstName(String senderFirstName) {
            newFullPayment.senderFirstName = senderFirstName;
            return this;
        }
        public FullPaymentDto.Builder withUserSenderLastName(String senderFirstName) {
            newFullPayment.senderLastName = senderFirstName;
            return this;
        }
        public FullPaymentDto.Builder withUserSenderSurname(String senderSurname) {
            newFullPayment.senderSurname = senderSurname;
            return this;
        }
        public FullPaymentDto.Builder withUserSenderFullName(String fName, String lName, String surname) {
            newFullPayment.senderFullName = String.format("%s %s %s", lName, fName, surname);
            return this;
        }

        public FullPaymentDto.Builder withUserDestinationFullName(String fName, String lName, String surname) {
            newFullPayment.destinationFullName = String.format("%s %s %s", lName, fName, surname);
            return this;
        }
        public FullPaymentDto.Builder withUserId(long userId) {
            newFullPayment.userId = userId;
            return this;
        }
        public FullPaymentDto.Builder withUserDestinationId(long userDestinationId) {
            newFullPayment.userDestinationId = userDestinationId;
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
                ", senderBalance=" + senderBalance +
                ", destinationCardNumber='" + destinationCardNumber + '\'' +
                ", senderFirstName='" + senderFirstName + '\'' +
                ", senderLastName='" + senderLastName + '\'' +
                ", senderSurnameName='" + senderSurname + '\'' +
                ", destinationFirstName='" + destinationFirstName + '\'' +
                ", destinationLastName='" + destinationLastName + '\'' +
                ", destinationSurname='" + destinationSurname + '\'' +
                ", destinationFullName='" + destinationFullName + '\'' +
                ", senderFullName='" + senderFullName + '\'' +
                ", userId=" + userId +
                ", userDestinationId=" + userDestinationId +
                '}';
    }
}
