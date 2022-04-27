package ua.epam.payments.payments.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.PaymentDao;
import ua.epam.payments.payments.db.DBManager;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.entity.dto.FullPaymentDto;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.Payment;
import ua.epam.payments.payments.model.dao.mapper.PaymentMapper;

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

    public static final String SQL_UPDATE_PREPARED_PAYMENTS_MONEY = "UPDATE payment SET balance=? WHERE id=?";
    public static final String SQL_COUNT_PAYMENTS_BY_USER = "SELECT  count(payment.id)  FROM payment LEFT JOIN card c on c.id = payment.card_destination_id where c.user_id =?";

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
            logger.error("{}, when trying to get Payment by Id = {}", throwables.getMessage(), id);
            throw new RuntimeException(throwables);
        }

        return payment;
    }

    @Override
    public int countPaymentsByUser(User user) {
        int counter = 0;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_COUNT_PAYMENTS_BY_USER)) {
            stmt.setLong(1, user.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    counter = rs.getInt(1);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return counter;
    }

    @Override
    public List<Payment> getPaymentsByUserId(long id) {
        List<Payment> paymentList = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_PAYMENTS_BY_USER)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                paymentList = new ArrayList<>();
                PaymentMapper paymentMapper = new PaymentMapper();
                while (rs.next()) {
                    paymentList.add(paymentMapper.mapRSToPayment(rs));

                }
            }

        } catch (SQLException throwables) {
            logger.error("{}, when trying to get Payment list by User Id = {}", throwables.getMessage(), id);
            throw new RuntimeException(throwables);
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
    public List<FullPaymentDto> getFullPaymentsByUserLimitSorted(long id, String query) {
        List<FullPaymentDto> paymentList = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                paymentList = new ArrayList<>();
                PaymentMapper paymentMapper = new PaymentMapper();
                while (rs.next()) {
                    paymentList.add(paymentMapper.mapRSToFullPaymentDto(rs));

                }
            }

        } catch (SQLException throwables) {
            logger.error("{}, when trying to get FullPaymentDto list by User Id = {} with query = [{}]", throwables.getMessage(), id, query);
            throw new RuntimeException(throwables);
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

            return true;
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
            System.out.println(id);
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException throwables) {
            logger.error("{}, when trying to confirm Payment Id = {}", throwables.getMessage(), id);
            throw new RuntimeException(throwables);
        }
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
            logger.error("{}, when trying to create PreparedPayment cardSender Id = {},cardDestination Id = {}, money = {}",
                    throwables.getMessage(), cardSender.getId(), cardDestination.getId(), money);
            throw new RuntimeException(throwables);
        }


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
            logger.error("{}, when trying to create ConfirmedPayment cardSender Id = {},cardDestination Id = {}, money = {}",
                    throwables.getMessage(), cardSender.getId(), cardDestination.getId(), money);
            throw new RuntimeException(throwables);
        }

    }
}
