package ua.epam.payments.payments.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.PaymentDao;
import ua.epam.payments.payments.db.DBManager;
import ua.epam.payments.payments.model.dto.FullPaymentDto;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.Payment;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.mapper.PaymentMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentsDaoImpl implements PaymentDao {
    public static final String SQL_GET_PAYMENT_BY_ID = "SELECT * FROM payment WHERE id=?";
    public static final String SQL_CREATE_PREPARED_PAYMENT = "INSERT INTO payment VALUES (default, ?, ?, default, default, ?, ?)";
    public static final String SQL_CREATE_CONFIRMED_PAYMENT = "INSERT INTO payment VALUES (default, ?,  ?, 2, default, ?, ?)";
    public static final String SQL_GET_PAYMENTS_BY_USER = "SELECT * FROM payment WHERE card_sender_id IN (SELECT card.id FROM card WHERE user_id =?);";
    /* public static final String SQL_GET_FULL_PAYMENTS_BY_USER = "SELECT payment.id,\n" +
             "       payment.money,\n" +
             "       (SELECT status  FROM payment_status WHERE payment.payment_status_id = payment_status.id),\n" +
             "       payment.creation_timestamp,\n" +
             "       (SELECT card.number as sender_card_number FROM card WHERE card.id = payment.card_sender_id),\n" +
             "       (SELECT card.number as destination_card_number FROM card WHERE card.id = payment.card_destination_id)\n" +
             "FROM payment\n" +
             "WHERE card_sender_id IN (SELECT card.id FROM card WHERE user_id =?)";
     public static final String SQL_GET_FULL_PAYMENTS = "SELECT payment.id,\n" +
             "       payment.money,\n" +
             "       (SELECT status  FROM payment_status WHERE payment.payment_status_id = payment_status.id),\n" +
             "       payment.creation_timestamp,\n" +
             "       (SELECT card.number as sender_card_number FROM card WHERE card.id = payment.card_sender_id),\n" +
             "       (SELECT \"user\".first_name FROM \"user\",card WHERE payment.card_sender_id=card.id and card.user_id = \"user\".id),\n" +
             "       (SELECT \"user\".last_name FROM \"user\",card WHERE payment.card_sender_id=card.id and card.user_id = \"user\".id),\n" +
             "       (SELECT \"user\".surname FROM \"user\",card WHERE payment.card_sender_id=card.id and card.user_id = \"user\".id),\n" +
             "       (SELECT card.number as destination_card_number FROM card WHERE card.id = payment.card_destination_id),\n" +
             "       (SELECT \"user\".first_name FROM \"user\",card WHERE payment.card_destination_id=card.id and card.user_id = \"user\".id),\n" +
             "       (SELECT \"user\".last_name FROM \"user\",card WHERE payment.card_destination_id=card.id and card.user_id = \"user\".id),\n" +
             "       (SELECT \"user\".surname FROM \"user\",card WHERE payment.card_destination_id=card.id and card.user_id = \"user\".id)\n" +
             "FROM payment";*/
    public static final String SQL_UPDATE_PREPARED_PAYMENTS_MONEY = "UPDATE payment SET balance=? WHERE id=?";

    //UPDATE payment SET balance=? WHERE id=?;
    public static final String SQL_CONFIRM_PAYMENT_BY_ID = "UPDATE payment SET creation_timestamp = default, payment_status_id=2 WHERE id =?";

    private final Logger logger = LogManager.getLogger(PaymentsDaoImpl.class);

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

/*    @Override
    public List<FullPaymentDto> getFullPaymentsByUser(User user) {
        List<FullPaymentDto> paymentList = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_FULL_PAYMENTS_BY_USER)) {
            stmt.setLong(1, user.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                paymentList = new ArrayList<>();
                PaymentMapper paymentMapper = new PaymentMapper();
                while (rs.next()) {
                    paymentList.add(paymentMapper.mapRSToFullPaymentDto(rs));

                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return paymentList;
    }*/

    @Override
    public List<FullPaymentDto> getFullPaymentsByUserLimitSorted(User user, String query) {
        List<FullPaymentDto> paymentList = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setLong(1, user.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                paymentList = new ArrayList<>();
                PaymentMapper paymentMapper = new PaymentMapper();
                while (rs.next()) {
                    paymentList.add(paymentMapper.mapRSToFullPaymentDto(rs));

                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return paymentList;
    }


    //SQL_UPDATE_PREPARED_PAYMENTS_MONEY
    //UPDATE payment SET balance=? WHERE id=?
    @Override
    public boolean updatePreparedPaymentMoney(List<Payment> paymentList) {

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_PREPARED_PAYMENTS_MONEY)) {

            for (Payment payment : paymentList) {
                stmt.setInt(1, payment.getBalance());
                stmt.setLong(2, payment.getId());
                stmt.executeUpdate();
            }

            return  true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return false;
    }

   /* @Override
    public List<FullPaymentDto> getFullPayments() {
        List<FullPaymentDto> paymentList = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_FULL_PAYMENTS)) {

            try (ResultSet rs = stmt.executeQuery()) {
                paymentList = new ArrayList<>();
                PaymentMapper paymentMapper = new PaymentMapper();
                while (rs.next()) {
                    paymentList.add(paymentMapper.mapRSToFullPaymentWithNameDto(rs));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return paymentList;
    }*/


    @Override
    public boolean confirmPayment(long id) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_CONFIRM_PAYMENT_BY_ID)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean createPreparedPayment(Card cardSender, Card cardDestination, int money) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_CREATE_PREPARED_PAYMENT)) {
            stmt.setInt(1, cardSender.getMoney());
            stmt.setInt(2, money);
            stmt.setLong(3, cardSender.getId());
            stmt.setLong(4, cardDestination.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;

    }

    @Override
    public boolean createConfirmedPayment(Card cardSender, Card cardDestination, int money) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_CREATE_CONFIRMED_PAYMENT)) {
            stmt.setInt(1, cardSender.getMoney());
            stmt.setInt(2, money);
            stmt.setLong(3, cardSender.getId());
            stmt.setLong(4, cardDestination.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
