package ua.epam.payments.payments.model.mapper;

import ua.epam.payments.payments.model.dto.CardsUnblockRequestDto;
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

    public CardsUnblockRequestDto mapRSToCardRequest(ResultSet rs) throws SQLException {
        return new CardsUnblockRequestDto.Builder()
                .withId(rs.getInt(1))
                .withUserId(rs.getLong(2))
                .withCardId(rs.getLong(3))
                .withFirstName(rs.getString(4))
                .withLastName(rs.getString(5))
                .withSurname(rs.getString(6))
                .withCardNumber(rs.getString(7))
                .withMoney(rs.getInt(8))
                .withBlocked(rs.getBoolean(9))
                .build();
    }

}
