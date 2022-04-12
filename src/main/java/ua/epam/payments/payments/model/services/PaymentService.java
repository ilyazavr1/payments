package ua.epam.payments.payments.model.services;

import ua.epam.payments.payments.model.dao.CardDao;
import ua.epam.payments.payments.model.dao.PaymentDao;
import ua.epam.payments.payments.model.dto.FullPaymentDto;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.Payment;
import ua.epam.payments.payments.model.exception.CardBlockedException;
import ua.epam.payments.payments.model.exception.InvalidMoneyException;
import ua.epam.payments.payments.model.exception.OutOfMoneyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PaymentService {
    private static final String CREATION_TIMESTAMP = "creation_timestamp";
    private static final String NUMBER = "payment.id";

    private final PaymentDao paymentDao;
    private final CardDao cardDao;

    public PaymentService(PaymentDao paymentDao, CardDao cardDao) {
        this.paymentDao = paymentDao;
        this.cardDao = cardDao;
    }


    public Payment getPaymentById(long id) {
        return paymentDao.getPaymentById(id);
    }

    public boolean confirmPayment(long id) throws CardBlockedException, OutOfMoneyException {
        Payment payment = paymentDao.getPaymentById(id);
        if (payment.getPaymentStatusId() == 2) return false;
        Card cardSender = cardDao.getCardById(payment.getCardSenderId());
        Card cardDestination = cardDao.getCardById(payment.getCardDestinationId());

        if ((payment.getBalance() - payment.getMoney()) < 0) throw new OutOfMoneyException();
        if (cardSender.isBlocked() || cardDestination.isBlocked()) throw new CardBlockedException();

        if (cardDao.transferMoneyFromCardToCard(cardSender.getId(), cardDestination.getId(), payment.getMoney())) {
            return paymentDao.confirmPayment(payment.getId());
        } else return false;
    }


    public boolean createPreparedPayment(Card cardSender, Card cardDestination, String money) throws InvalidMoneyException, OutOfMoneyException {
        if (money.isEmpty() || !money.replaceFirst("^0*", "").matches("^[0-9]{0,4}$"))
            throw new InvalidMoneyException();
        int moneyInt = Integer.parseInt(money);
        if (moneyInt <= 0 || moneyInt > 10000) throw new InvalidMoneyException();
        if ((cardSender.getMoney() - moneyInt) < 0) throw new OutOfMoneyException();

        return paymentDao.createPreparedPayment(cardSender, cardDestination, moneyInt);
    }

    public boolean updatePreparedPaymentsByUserId(long id) {
        List<Payment> paymentList = paymentDao.getPaymentsByUserId(id);
        Map<Long, Card> longCardMap = cardDao.getCardByUserId(id).stream().collect(Collectors.toMap(Card::getId, Function.identity()));
        if (longCardMap.isEmpty()) return false;

        paymentList = paymentList.stream()
                .filter(payment -> payment.getPaymentStatusId() == 1)
                .collect(Collectors.toList());


        if (paymentList.isEmpty()) return false;

        paymentList.forEach(payment -> payment.setBalance(longCardMap.get(payment.getCardSenderId()).getMoney()));


        return paymentDao.updatePreparedPaymentMoney(paymentList);
    }

    public boolean createConfirmedPayment(Card cardSender, Card cardDestination, int money) {
        return paymentDao.createConfirmedPayment(cardSender, cardDestination, money);
    }

    public boolean makePayment(Card cardSender, Card cardDestination, String money) throws InvalidMoneyException, OutOfMoneyException {
        if (money.isEmpty() || !money.replaceFirst("^0*", "").matches("^[0-9]{0,4}$"))
            throw new InvalidMoneyException();
        int moneyInt = Integer.parseInt(money);
        if (moneyInt <= 0 || moneyInt > 10000) throw new InvalidMoneyException();
        if ((cardSender.getMoney() - moneyInt) < 0) throw new OutOfMoneyException();


        return cardDao.transferMoneyFromCardToCard(cardSender.getId(), cardDestination.getId(), moneyInt)
                && paymentDao.createConfirmedPayment(cardSender, cardDestination, moneyInt);
    }

    public boolean confirmAndMakePayment(Card cardSender, Card cardDestination) {


        return false;
    }

    public List<FullPaymentDto> sort(long id, String type, String order, int limit, int offset) {

        String query = "SELECT payment.id,\n" +
                "       (SELECT card.number as sender_card_number FROM card WHERE card.id = payment.card_sender_id),\n" +
                "        payment.balance,\n" +
                "       payment.money,\n" +
                "       (SELECT \"user\".first_name FROM \"user\",card WHERE payment.card_destination_id=card.id and card.user_id = \"user\".id),\n" +
                "       (SELECT \"user\".last_name FROM \"user\",card WHERE payment.card_destination_id=card.id and card.user_id = \"user\".id),\n" +
                "       (SELECT \"user\".surname FROM \"user\",card WHERE payment.card_destination_id=card.id and card.user_id = \"user\".id),\n" +
                "       (SELECT card.number as destination_card_number FROM card WHERE card.id = payment.card_destination_id),\n" +
                "       payment.creation_timestamp,\n" +
                "       (SELECT status  FROM payment_status WHERE payment.payment_status_id = payment_status.id)\n" +
                "FROM payment\n" +
                "WHERE card_sender_id IN (SELECT card.id FROM card WHERE user_id =?) ORDER BY %s %s LIMIT %d OFFSET %d";

        if (type.equalsIgnoreCase(CREATION_TIMESTAMP)) {

            if (order.equalsIgnoreCase("ASC")) {
                query = String.format(query, CREATION_TIMESTAMP, "ASC", limit, offset);
                return paymentDao.getFullPaymentsByUserLimitSorted(id, query);
            } else if (order.equalsIgnoreCase("DESC")) {
                query = String.format(query, CREATION_TIMESTAMP, "DESC", limit, offset);
                return paymentDao.getFullPaymentsByUserLimitSorted(id, query);
            }
        }
        if (type.equalsIgnoreCase(NUMBER)) {

            if (order.equalsIgnoreCase("ASC")) {
                query = String.format(query, NUMBER, "ASC", limit, offset);
                return paymentDao.getFullPaymentsByUserLimitSorted(id, query);
            } else if (order.equalsIgnoreCase("DESC")) {
                query = String.format(query, NUMBER, "DESC", limit, offset);
                return paymentDao.getFullPaymentsByUserLimitSorted(id, query);
            }
        }

        return new ArrayList<>();
    }
}