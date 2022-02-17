package ua.epam.payments.payments.dao.impl;

import ua.epam.payments.payments.dao.PaymentsDao;
import ua.epam.payments.payments.db.DBManager;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.Payment;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.services.mapper.PaymentMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentsDaoImpl implements PaymentsDao {
    public static final String SQL_GET_PAYMENT_BY_ID = "SELECT * FROM payment WHERE id=?";
    public static final String SQL_CREATE_PAYMENT = "INSERT INTO payment VALUES (default, ?, default, default, ?, ?)";
    public static final String SQL_GET_PAYMENTS_BY_USER = "SELECT * FROM payment WHERE card_sender_id IN (SELECT card.id FROM card WHERE user_id =?);";

    @Override
    public Payment getPaymentById(long id) {
        Payment payment = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_PAYMENT_BY_ID)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PaymentMapper paymentMapper = new PaymentMapper();
                    payment = paymentMapper.mapRSToPayment(rs);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return payment;
    }

    @Override
    public List<Payment> getPaymentsByUser(User user) {
        List<Payment> paymentList = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_PAYMENTS_BY_USER)) {
            stmt.setLong(1, user.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                paymentList = new ArrayList<>();
                PaymentMapper paymentMapper = new PaymentMapper();
                while (rs.next()) {
                    paymentList.add(paymentMapper.mapRSToPayment(rs));

                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return paymentList;
    }

    @Override
    public boolean createPayment(Card cardSender, Card cardDestination, int money) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_CREATE_PAYMENT)) {
            stmt.setInt(1,money);
            stmt.setLong(2, cardSender.getId());
            stmt.setLong(3, cardDestination.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;

    }
}
