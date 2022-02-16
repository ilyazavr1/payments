package ua.epam.payments.payments.model.services.mapper;

import ua.epam.payments.payments.model.entity.Card;


import java.sql.ResultSet;
import java.sql.SQLException;


public class CardMapper {

    public Card mapRSToCard(ResultSet rs) throws SQLException {
        return new Card.Builder()
                .withId(rs.getInt(1))
                .withNumber(rs.getString(2))
                .withMoney(rs.getInt(3))
                .withBlocked(rs.getBoolean(4))
                .build();
    }


}
