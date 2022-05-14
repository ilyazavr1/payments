package ua.epam.payments.payments.model.dao;


import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.entity.dto.FullPaymentDto;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.Payment;

import java.util.List;

public interface PaymentDao {
    Payment getPaymentById(long id);

    int countPaymentsByUser(User user);

    List<FullPaymentDto> getFullPaymentsByUserLimitSorted(long id, String query);

    boolean updatePreparedPaymentsByTwoCards(Card sender, Card destination);

    boolean updatePreparedPaymentByOneCard(Card card);

    boolean confirmPayment(long id, Card cardSender, Card cardDestination, int money);

    boolean createPreparedPayment(Card cardSender, Card cardDestination, int money);

    boolean createConfirmedPayment(Card cardSender, Card cardDestination, int money);


}
