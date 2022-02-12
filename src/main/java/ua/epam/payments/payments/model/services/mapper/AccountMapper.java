package ua.epam.payments.payments.model.services.mapper;

import ua.epam.payments.payments.model.entity.Account;


import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper {

    public Account mapRSToAccount(ResultSet rs) throws SQLException {
        Account account = null;
        while (rs.next()) {
            account = new Account.Builder()
                    .withId(rs.getInt(1))
                    .withNumber(rs.getString(2))
                    .withMoney(rs.getBigDecimal(3))
                    .build();

        }

        return account;
    }
}
