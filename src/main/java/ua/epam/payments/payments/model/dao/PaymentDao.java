package ua.epam.payments.payments.model.dao;


import ua.epam.payments.payments.model.dto.FullPaymentDto;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.Payment;
import ua.epam.payments.payments.model.entity.User;

import java.util.List;

public interface PaymentDao {
    Payment getPaymentById(long id);

    List<Payment> getPaymentsByUserId(long id);

    List<FullPaymentDto> getFullPaymentsByUserLimitSorted(long id, String query);

    boolean updatePreparedPaymentMoney(List<Payment> paymentList);

    boolean confirmPayment(long id);

    boolean createPreparedPayment(Card cardSender, Card cardDestination, int money);

    boolean createConfirmedPayment(Card cardSender, Card cardDestination, int money);


/*    List<FullPaymentDto> getFullPaymentsByUser(User user);

    List<FullPaymentDto> getFullPayments();*/
}
