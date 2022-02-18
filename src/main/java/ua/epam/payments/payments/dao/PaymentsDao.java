package ua.epam.payments.payments.dao;


import ua.epam.payments.payments.model.dto.FullPaymentDto;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.Payment;
import ua.epam.payments.payments.model.entity.User;

import java.util.List;

public interface PaymentsDao {
    Payment getPaymentById(long id);
    List<Payment> getPaymentsByUser(User user);
    List<FullPaymentDto> getFullPaymentsByUser(User user);
    boolean createPayment(Card cardSender, Card cardDestination, int money);

}
