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
                .withBalanceDestination(rs.getInt(3))
                .withMoney(rs.getInt(4))
                .withPaymentStatusId(rs.getInt(5))
                .withCreationTimestamp(rs.getTimestamp(6))
                .withCardSenderId(rs.getLong(7))
                .withCardDestinationId(rs.getLong(8))
                .withUserId(rs.getLong(9))
                .withUserDestinationId(rs.getLong(10))
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
                .withDestinationBalance(rs.getInt(8))

                .withUserDestinationFirstName(rs.getString(9))
                .withUserDestinationLastName(rs.getString(10))
                .withUserDestinationSurname(rs.getString(11))
                .withCardDestinationNumber(rs.getString(12))

                .withCreationTimestamp(rs.getTimestamp(13))
                .withPaymentStatus(rs.getString(14))
                .withUserId(rs.getLong(15))
                .withUserDestinationId(rs.getLong(16))
                .build();
    }



}
