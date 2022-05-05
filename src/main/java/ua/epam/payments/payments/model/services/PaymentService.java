package ua.epam.payments.payments.model.services;

import ua.epam.payments.payments.model.dao.CardDao;
import ua.epam.payments.payments.model.dao.PaymentDao;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.entity.dto.FullPaymentDto;
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
    public static final String CREATION_TIMESTAMP = "creation_timestamp";
    public static final String NUMBER = "payment.id";
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    private final PaymentDao paymentDao;
    private final CardDao cardDao;

    public PaymentService(PaymentDao paymentDao, CardDao cardDao) {
        this.paymentDao = paymentDao;
        this.cardDao = cardDao;
    }


    public int countPaymentsByUser(User user) {
        return paymentDao.countPaymentsByUser(user);
    }

    public Payment getPaymentById(long id) {
        return paymentDao.getPaymentById(id);
    }

    /**
     * Gets prepared Payment from database.
     * Gets a card from which money is withdrawn from database.
     * Gets a card that replenishes from database.
     * Calls the method that transfers money.
     * Confirms Payment.
     *
     * @param id Payment id
     * @return boolean if the payment was confirmed or not
     * @throws CardBlockedException if card is blocked
     * @throws OutOfMoneyException if the difference between card balance and payment money is negative
     */
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


    /**
     * Validates money string.
     * Checks whether there is enough money on card sender balance.
     * Creates prepared Payment.
     *
     * @param cardSender card from which money is withdrawn from database.
     * @param cardDestination card that replenishes from database.
     * @param money money to be transferred.
     * @return boolean if the prepared payment was confirmed or not.
     * @throws InvalidMoneyException if money input is invalid.
     * @throws OutOfMoneyException if the difference between card balance and payment money is negative.
     */
    public boolean createPreparedPayment(Card cardSender, Card cardDestination, String money) throws InvalidMoneyException, OutOfMoneyException {
        if (money.isEmpty() || !money.replaceFirst("^0*", "").matches("^[0-9]{0,5}$"))
            throw new InvalidMoneyException();
        int moneyInt = Integer.parseInt(money);
        if (moneyInt <= 0 || moneyInt > 10000) throw new InvalidMoneyException();
        if ((cardSender.getMoney() - moneyInt) < 0) throw new OutOfMoneyException();

        return paymentDao.createPreparedPayment(cardSender, cardDestination, moneyInt);
    }


    /**
     * Updates database Payment records in case the Card information has changed.
     *
     * @param id User id
     * @return boolean if payments eas updated
     */
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


    /**
     * Validates string money input.
     * Checks whether there is enough money on card sender balance.
     * Transfers money.
     * Creates payment in database.
     *
     * @param cardSender a card from which money is withdrawn from database.
     * @param cardDestination a card that replenishes from database.
     * @param money money to transfer.
     * @return boolean if payment was made
     * @throws InvalidMoneyException if money input is invalid.
     * @throws OutOfMoneyException if the difference between card balance and payment money is negative.
     */
    public boolean makePayment(Card cardSender, Card cardDestination, String money) throws InvalidMoneyException, OutOfMoneyException {
        if (money.isEmpty() || !money.replaceFirst("^0*", "").matches("^[0-9]{0,5}$")) {
            throw new InvalidMoneyException();
        }
        int moneyInt = Integer.parseInt(money);
        if (moneyInt < 0 || moneyInt > 10000) throw new InvalidMoneyException();
        if ((cardSender.getMoney() - moneyInt) < 0) throw new OutOfMoneyException();

        if (cardDao.transferMoneyFromCardToCard(cardSender.getId(), cardDestination.getId(), moneyInt)) {
            return paymentDao.createConfirmedPayment(cardSender, cardDestination, moneyInt);
        } else return false;

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