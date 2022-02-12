package ua.epam.payments.payments.model.entity;

import java.math.BigDecimal;

public class Account {
    private static final long serialVersionUID = 123L;

    private long id;
    private String number;
    private BigDecimal money;

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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public static class Builder {
        private final Account newAccount;

        public Builder() {
            newAccount = new Account();
        }

        public Account.Builder withId(long id){
            newAccount.id = id;
            return this;
        }

        public Account.Builder withNumber(String number){
            newAccount.number = number;
            return this;
        }

        public Account.Builder withMoney(BigDecimal money){
            newAccount.money = money;
            return this;
        }

        public Account build(){
            return newAccount;
        }

    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", money=" + money +
                '}';
    }
}
