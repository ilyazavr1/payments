package ua.epam.payments.payments.model.dao.mapper;


import ua.epam.payments.payments.model.entity.dto.FullPaymentDto;
import ua.epam.payments.payments.model.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentMapper {

    public Payment mapRSToPayment(ResultSet rs) throws SQLException {
        return new Payment.Builder()
                .withId(rs.getInt(1))
                .withBalance(rs.getInt(2))
                .withMoney(rs.getInt(3))
                .withPaymentStatusId(rs.getInt(4))
                .withCreationTimestamp(rs.getTimestamp(5))
                .withCardSenderId(rs.getLong(6))
                .withCardDestinationId(rs.getLong(7))
                .withUserId(rs.getLong(8))
                .withUserDestinationId(rs.getLong(9))
                .build();
    }

    public FullPaymentDto mapRSToFullPaymentDto(ResultSet rs) throws SQLException {
        /*return new FullPaymentDto.Builder()
                .withId(rs.getInt(1))
                .withCardSenderNumber(rs.getString(2))
                .withSenderBalance(rs.getInt(3))
                .withMoney(rs.getInt(4))
                .withUserDestinationFirstName(rs.getString(5))
                .withUserDestinationLastName(rs.getString(6))
                .withUserDestinationSurname(rs.getString(7))
                .withCardDestinationNumber(rs.getString(8))
                .withCreationTimestamp(rs.getTimestamp(9))
                .withPaymentStatus(rs.getString(10))
                .withUserId(rs.getLong(11))
                .withUserDestinationId(rs.getLong(12))
                .build();*/
        return new FullPaymentDto.Builder()
                .withId(rs.getInt(1))
                .withUserSenderFirstName(rs.getString(2))
                .withUserSenderLastName(rs.getString(3))
                .withUserSenderSurname(rs.getString(4))
                .withCardSenderNumber(rs.getString(5))

                .withSenderBalance(rs.getInt(6))
                .withMoney(rs.getInt(7))

                .withUserDestinationFirstName(rs.getString(8))
                .withUserDestinationLastName(rs.getString(9))
                .withUserDestinationSurname(rs.getString(10))
                .withCardDestinationNumber(rs.getString(11))

                .withCreationTimestamp(rs.getTimestamp(12))
                .withPaymentStatus(rs.getString(13))
                .withUserId(rs.getLong(14))
                .withUserDestinationId(rs.getLong(15))
                .build();
    }



}
