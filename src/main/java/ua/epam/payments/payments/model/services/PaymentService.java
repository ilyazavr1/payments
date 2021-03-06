package ua.epam.payments.payments.model.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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


/**
 * Manages business logic related with Payment.
 *
 * @author Illia Smiian
 */
public class PaymentService {
    private static final Logger logger = LogManager.getLogger(PaymentService.class);

    public static final String CREATION_TIMESTAMP = "creation_timestamp";
    public static final String NUMBER = "payment.id";
    private final PaymentDao paymentDao;
    private final CardDao cardDao;

    public PaymentService(PaymentDao paymentDao, CardDao cardDao) {
        this.paymentDao = paymentDao;
        this.cardDao = cardDao;
    }


    public int countPaymentsByUser(User user) {
        return paymentDao.countPaymentsByUser(user);
    }


    /**
     * Gets prepared Payment from database.
     * Gets a card from which money is withdrawn from database.
     * Gets a card that replenishes from database.
     * Calls the method that transfers money.
     * Updates the balance of Cards in the prepared Payment.
     * Confirms Payment.
     *
     * @param id Payment id
     * @return boolean if the payment was confirmed or not
     * @throws CardBlockedException if card is blocked
     * @throws OutOfMoneyException  if the difference between card balance and payment money is negative
     */
    public boolean confirmPayment(long id) throws CardBlockedException, OutOfMoneyException {
        Payment payment = paymentDao.getPaymentById(id);
        if (payment.getPaymentStatusId() == 2) return false;
        Card cardSender = cardDao.getCardById(payment.getCardSenderId());
        Card cardDestination = cardDao.getCardById(payment.getCardDestinationId());

        if ((payment.getBalance() - payment.getMoney()) < 0) throw new OutOfMoneyException();
        if (cardSender.isBlocked() || cardDestination.isBlocked()) throw new CardBlockedException();

        if (!cardDao.transferMoneyFromCardToCard(cardSender.getId(), cardDestination.getId(), payment.getMoney()))
            return false;

        cardSender.setMoney(cardSender.getMoney() - payment.getMoney());
        cardDestination.setMoney(cardDestination.getMoney() + payment.getMoney());
        paymentDao.updatePreparedPaymentsByTwoCards(cardSender, cardDestination);

        paymentDao.confirmPayment(payment.getId(), cardSender, cardDestination, payment.getMoney());

        logger.info("Payment with id \"{}\" confirmed", id);

        return true;

    }


    /**
     * Validates money string.
     * Checks whether there is enough money on card sender balance.
     * Creates prepared Payment.
     *
     * @param cardSender      card from which money is withdrawn from database.
     * @param cardDestination card that replenishes from database.
     * @param money           money to be transferred.
     * @return boolean if the prepared payment was confirmed or not.
     * @throws InvalidMoneyException if money input is invalid.
     * @throws OutOfMoneyException   if the difference between card balance and payment money is negative.
     */
    public boolean createPreparedPayment(Card cardSender, Card cardDestination, String money) throws InvalidMoneyException, OutOfMoneyException {
        if (money.isEmpty() || !money.replaceFirst("^0*", "").matches("^[0-9]{0,5}$"))
            throw new InvalidMoneyException();
        int moneyInt = Integer.parseInt(money);
        if (moneyInt <= 0 || moneyInt > 10000) throw new InvalidMoneyException();
        if ((cardSender.getMoney() - moneyInt) < 0) throw new OutOfMoneyException();

        if (paymentDao.createPreparedPayment(cardSender, cardDestination, moneyInt)) {
            logger.info("Payment prepared [cardFrom: {}; cardTo: {}; money: {}]", cardSender.getNumber(), cardDestination.getNumber(), money);
            return true;
        } else return false;
    }


    /**
     * Validates string money input.
     * Checks whether there is enough money on card sender balance.
     * Transfers money.
     * Updates the balance of Cards in the prepared Payment.
     * Creates payment in database.
     *
     * @param cardSender      a card from which money is withdrawn from database.
     * @param cardDestination a card that replenishes from database.
     * @param money           money to transfer.
     * @return boolean if payment was made
     * @throws InvalidMoneyException if money input is invalid.
     * @throws OutOfMoneyException   if the difference between card balance and payment money is negative.
     */
    public boolean makePayment(Card cardSender, Card cardDestination, String money) throws InvalidMoneyException, OutOfMoneyException {
        if (money.isEmpty() || !money.replaceFirst("^0*", "").matches("^[0-9]{0,5}$")) {
            throw new InvalidMoneyException();
        }
        int moneyInt = Integer.parseInt(money);
        if (moneyInt < 0 || moneyInt > 10000) throw new InvalidMoneyException();
        if ((cardSender.getMoney() - moneyInt) < 0) throw new OutOfMoneyException();

        if (!cardDao.transferMoneyFromCardToCard(cardSender.getId(), cardDestination.getId(), moneyInt)) return false;
        cardSender.setMoney(cardSender.getMoney() - moneyInt);
        cardDestination.setMoney(cardDestination.getMoney() + moneyInt);
        paymentDao.updatePreparedPaymentsByTwoCards(cardSender, cardDestination);

        if (!paymentDao.createConfirmedPayment(cardSender, cardDestination, moneyInt)) return false;

        logger.info("Payment has been made [cardFrom: {}; cardTo: {}; money: {}]", cardSender.getNumber(), cardDestination.getNumber(), money);

        return true;
    }


    public List<FullPaymentDto> sort(long id, String type, String order, int limit, int offset) {

        String query = "SELECT payment.id,\n" +
                "       u1.first_name,u1.last_name,u1.surname,c1.number,\n" +
                "       payment.balance, payment.money, payment.balance_destination,\n" +
                "       u2.first_name,u2.last_name,u2.surname, c2.number,\n" +
                "       payment.creation_timestamp,\n" +
                "       (SELECT status  FROM payment_status WHERE payment.payment_status_id = payment_status.id),\n" +
                "       payment.user_id, payment.user_destination_id\n" +
                "       from payment\n" +
                "    left join card c1 on payment.card_sender_id = c1.id\n" +
                "    left join card c2 on payment.card_destination_id = c2.id\n" +
                "    left join \"user\" u1 on u1.id = payment.user_id\n" +
                "    left join \"user\" u2 on u2.id = payment.user_destination_id\n" +
                "WHERE payment.user_id =? OR payment.user_destination_id = ? ORDER BY %s %s LIMIT %d OFFSET %d";
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