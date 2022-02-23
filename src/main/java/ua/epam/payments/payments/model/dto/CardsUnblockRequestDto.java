package ua.epam.payments.payments.model.dto;

public class CardsUnblockRequestDto {
    private long id;
    private long userId;
    private long cardId;
    private String firstName;
    private String lastName;
    private String surname;
    private String cardNumber;
    private int money;
    private boolean blocked;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }


    public static class Builder {
        private final CardsUnblockRequestDto newCardUnblockRequest;

        public Builder() {
            newCardUnblockRequest = new CardsUnblockRequestDto();
        }

        public CardsUnblockRequestDto.Builder withId(long id) {
            newCardUnblockRequest.id = id;
            return this;
        }
        public CardsUnblockRequestDto.Builder withUserId(long userId) {
            newCardUnblockRequest.userId = userId;
            return this;
        }
        public CardsUnblockRequestDto.Builder withCardId(long cardId) {
            newCardUnblockRequest.cardId = cardId;
            return this;
        }
        public CardsUnblockRequestDto.Builder withFirstName(String firstName) {
            newCardUnblockRequest.firstName = firstName;
            return this;
        }
        public CardsUnblockRequestDto.Builder withLastName(String lastName) {
            newCardUnblockRequest.lastName = lastName;
            return this;
        }
        public CardsUnblockRequestDto.Builder withSurname(String surname) {
            newCardUnblockRequest.surname = surname;
            return this;
        }

        public CardsUnblockRequestDto.Builder withCardNumber(String cardNumber) {
            newCardUnblockRequest.cardNumber = cardNumber;
            return this;
        }

        public CardsUnblockRequestDto.Builder withMoney(int money) {
            newCardUnblockRequest.money = money;
            return this;
        }

        public CardsUnblockRequestDto.Builder withBlocked(boolean blocked) {
            newCardUnblockRequest.blocked = blocked;
            return this;
        }
        public CardsUnblockRequestDto build() {
            return newCardUnblockRequest;
        }


    }

}
