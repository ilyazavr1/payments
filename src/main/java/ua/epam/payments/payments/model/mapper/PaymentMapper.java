package ua.epam.payments.payments.model.mapper;


import ua.epam.payments.payments.model.dto.FullPaymentDto;
import ua.epam.payments.payments.model.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentMapper {

    public Payment mapRSToPayment(ResultSet rs) throws SQLException {
        return new Payment.Builder()
                .withId(rs.getInt(1))
                .withMoney(rs.getInt(2))
                .withPaymentStatusId(rs.getInt(3))
                .withCreationTimestamp(rs.getTimestamp(4))
                .withCardSenderId(rs.getLong(5))
                .withCardDestinationId(rs.getLong(6))
                .build();
    }

    public FullPaymentDto mapRSToFullPaymentDto(ResultSet rs) throws SQLException {
        return new FullPaymentDto.Builder()
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
                .build();
    }

    public FullPaymentDto mapRSToFullPaymentWithNameDto(ResultSet rs) throws SQLException {
        return new FullPaymentDto.Builder()
                .withId(rs.getInt(1))
                .withMoney(rs.getInt(2))
                .withPaymentStatus(rs.getString(3))
                .withCreationTimestamp(rs.getTimestamp(4))
                .withCardSenderNumber(rs.getString(5))
                .withUserSenderFullName(rs.getString(6), rs.getString(7), rs.getString(8))
                .withCardDestinationNumber(rs.getString(9))
                .withUserDestinationFullName(rs.getString(10), rs.getString(11), rs.getString(12))
                .build();
    }

}
