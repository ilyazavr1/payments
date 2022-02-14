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
                .withAccountSenderId(rs.getLong(5))
                .withAccountDestinationId(rs.getLong(6))
                .build();
    }
}
/*
    id                     serial  NOT NULL,
    money                  integer NOT NULL DEFAULT 0,
        sent                   bool    NOT NULL DEFAULT FALSE,
        creation_timestamp     timestamp        DEFAULT CURRENT_TIMESTAMP,
        account_sender_id      bigint  NOT NULL REFERENCES account (id),
        account_destination_id bigint  NOT NULL REFERENCES account (id),*/
