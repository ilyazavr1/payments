package ua.epam.payments.payments.model.entity;


public class Card {
    private static final long serialVersionUID = 123L;

    private long id;
    private String name;
    private String number;
    private int money;
    private boolean blocked;
    private long userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public static class Builder {
        private final Card newCard;

        public Builder() {
            newCard = new Card();
        }

        public Card.Builder withId(long id) {
            newCard.id = id;
            return this;
        }

        public Card.Builder withNumber(String number) {
            newCard.number = number;
            return this;
        }

        public Card.Builder withName(String name) {
            newCard.name = name;
            return this;
        }

        public Card.Builder withMoney(int money) {
            newCard.money = money;
            return this;
        }
        public Card.Builder withBlocked(boolean blocked) {
            newCard.blocked = blocked;
            return this;
        }
        public Card.Builder withUserId(long userId) {
            newCard.userId = userId;
            return this;
        }

        public Card build() {
            return newCard;
        }

    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", money=" + money +
                ", blocked=" + blocked +
                ", userId=" + userId +
                '}';
    }
}
