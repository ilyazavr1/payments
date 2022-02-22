package ua.epam.payments.payments.model.mapper;

import ua.epam.payments.payments.model.entity.Card;


import java.sql.ResultSet;
import java.sql.SQLException;


public class CardMapper {

    public Card mapRSToCard(ResultSet rs) throws SQLException {
        return new Card.Builder()
                .withId(rs.getInt(1))
                .withName(rs.getString(2))
                .withNumber(rs.getString(3))
                .withMoney(rs.getInt(4))
                .withBlocked(rs.getBoolean(5))
                .withUnderConsideration(rs.getBoolean(6))
                .withUserId(rs.getLong(7))
                .build();
    }


}
