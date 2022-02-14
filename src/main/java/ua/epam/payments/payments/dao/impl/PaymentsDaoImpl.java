package ua.epam.payments.payments.dao.impl;

import ua.epam.payments.payments.dao.PaymentsDao;
import ua.epam.payments.payments.db.DBManager;
import ua.epam.payments.payments.model.entity.Account;
import ua.epam.payments.payments.model.entity.Payment;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.services.mapper.AccountMapper;
import ua.epam.payments.payments.model.services.mapper.PaymentMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PaymentsDaoImpl implements PaymentsDao {
    public static final String SQL_GET_PAYMENT_BY_ID = "SELECT * FROM payment WHERE id=?";

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
        return null;
    }

    @Override
    public void createAccountWithAccount(Payment payment, Account account) {

    }
}
