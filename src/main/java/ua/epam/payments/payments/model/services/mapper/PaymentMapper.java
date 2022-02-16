package ua.epam.payments.payments.model.services.mapper;


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
}
