package ua.epam.payments.payments.model.entity;


public class Account {
    private static final long serialVersionUID = 123L;

    private long id;
    private String number;
    private int money;
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public static class Builder {
        private final Account newAccount;

        public Builder() {
            newAccount = new Account();
        }

        public Account.Builder withId(long id) {
            newAccount.id = id;
            return this;
        }

        public Account.Builder withNumber(String number) {
            newAccount.number = number;
            return this;
        }

        public Account.Builder withMoney(int money) {
            newAccount.money = money;
            return this;
        }

        public Account.Builder withUserId(long userId) {
            newAccount.userId = userId;
            return this;
        }

        public Account build() {
            return newAccount;
        }

    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", money=" + money +
                ", userId=" + userId +
                '}';
    }
}
