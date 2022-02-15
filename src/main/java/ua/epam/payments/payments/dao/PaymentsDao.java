package ua.epam.payments.payments.dao;


import ua.epam.payments.payments.model.entity.Account;
import ua.epam.payments.payments.model.entity.Payment;
import ua.epam.payments.payments.model.entity.User;

import java.util.List;

public interface PaymentsDao {
    Payment getPaymentById(long id);
    List<Payment> getPaymentsByUser(User user);
    boolean createPayment(Account accountSender, Account accountDestination, int money);

}
