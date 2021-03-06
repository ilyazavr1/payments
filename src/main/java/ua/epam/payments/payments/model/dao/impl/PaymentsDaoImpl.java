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
    public static final String SQL_CREATE_PREPARED_PAYMENT = "INSERT INTO payment VALUES (default, ?, ?, ?, default, default, ?, ?, ?, ?)";
    public static final String SQL_CREATE_CONFIRMED_PAYMENT = "INSERT INTO payment VALUES (default, ?, ?, ?, 2, default, ?, ?, ?, ?)";


    public static final String SQL_UPDATE_PREPARED_PAYMENTS_ONE_CARD = "UPDATE payment\n" +
            "SET balance             = (CASE WHEN card_sender_id = ? THEN ? else balance end),\n" +
            "    balance_destination = (CASE WHEN card_destination_id = ? THEN ? else balance_destination end)\n" +
            "WHERE payment_status_id = 1";

    public static final String SQL_UPDATE_PREPARED_PAYMENTS_TWO_CARDS = "UPDATE payment " +
            "SET balance             = (CASE WHEN card_sender_id = ? THEN ? WHEN card_sender_id = ? THEN ? ELSE balance END ),\n" +
            "    balance_destination = (CASE WHEN card_destination_id = ? THEN ? WHEN card_destination_id = ? THEN ? ELSE balance_destination END )" +
            "WHERE payment_status_id = 1";

    public static final String SQL_COUNT_PAYMENTS_BY_USER = "SELECT  count(payment.id)  FROM payment where user_id = ? OR user_destination_id = ?";

    public static final String SQL_CONFIRM_PAYMENT_BY_ID = "UPDATE payment SET creation_timestamp = default, balance = ?, balance_destination = ?, payment_status_id=2 WHERE id =?";

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
            stmt.setLong(2, user.getId());

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
    public List<FullPaymentDto> getFullPaymentsByUserLimitSorted(long id, String query) {
        List<FullPaymentDto> paymentList;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.setLong(2, id);

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


    /**
     * Updates prepared Payments in case the Card balance has changed.
     *
     * @param card Card sender
     * @return boolean if payments eas updated
     */
    @Override
    public boolean updatePreparedPaymentByOneCard(Card card) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_PREPARED_PAYMENTS_ONE_CARD)) {

            stmt.setLong(1, card.getId());
            stmt.setInt(2, card.getMoney());
            stmt.setLong(3, card.getId());
            stmt.setInt(4, card.getMoney());

            return stmt.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    /**
     * Updates prepared Payments records in case the Cards balance has changed.
     *
     * @param sender Card sender
     * @param destination Card destination
     * @return boolean if payments eas updated
     */
    @Override
    public boolean updatePreparedPaymentsByTwoCards(Card sender, Card destination) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_PREPARED_PAYMENTS_TWO_CARDS)) {


            stmt.setLong(1, sender.getId());
            stmt.setInt(2, sender.getMoney());
            stmt.setLong(3, destination.getId());
            stmt.setInt(4, destination.getMoney());

            stmt.setLong(5, sender.getId());
            stmt.setInt(6, sender.getMoney());
            stmt.setLong(7, destination.getId());
            stmt.setInt(8, destination.getMoney());

            return stmt.executeUpdate() > 0;


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean confirmPayment(long id, Card cardSender, Card cardDestination, int money) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_CONFIRM_PAYMENT_BY_ID)) {
            System.out.println(id);
            stmt.setInt(1, cardSender.getMoney());
            stmt.setInt(2, cardDestination.getMoney());
            stmt.setLong(3, id);
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
            stmt.setInt(2, cardDestination.getMoney());
            stmt.setInt(3, money);
            stmt.setLong(4, cardSender.getId());
            stmt.setLong(5, cardDestination.getId());
            stmt.setLong(6, cardSender.getUserId());
            stmt.setLong(7, cardDestination.getUserId());

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
            stmt.setInt(2, cardDestination.getMoney());
            stmt.setInt(3, money);
            stmt.setLong(4, cardSender.getId());
            stmt.setLong(5, cardDestination.getId());
            stmt.setLong(6, cardSender.getUserId());
            stmt.setLong(7, cardDestination.getUserId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException throwables) {
            logger.error("{}, when trying to create ConfirmedPayment cardSender Id = {},cardDestination Id = {}, money = {}",
                    throwables.getMessage(), cardSender.getId(), cardDestination.getId(), money);
            throw new RuntimeException(throwables);
        }

    }
}
