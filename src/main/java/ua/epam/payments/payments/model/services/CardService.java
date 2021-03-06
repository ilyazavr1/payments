package ua.epam.payments.payments.model.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.CardDao;
import ua.epam.payments.payments.model.dao.PaymentDao;
import ua.epam.payments.payments.model.entity.dto.CardsUnblockRequestDto;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.exception.CardExistException;
import ua.epam.payments.payments.model.exception.CardTopUpException;
import ua.epam.payments.payments.web.servlets.CardBlock;

import java.util.List;

/**
 * Manages business logic related with Card.
 *
 * @author Illia Smiian
 */
public class CardService {
    private static final Logger logger = LogManager.getLogger(CardService.class);
    private final CardDao cardDao;
    private static final String NUMBER = "number";
    private static final String NAME = "name";
    private static final String MONEY = "money";

    public CardService(CardDao cardDao) {
        this.cardDao = cardDao;
    }


    public Card getCardById(long id) {
        return cardDao.getCardById(id);
    }

    public Card getCardByNumber(String number) {
        return cardDao.getCardByNumber(number);
    }

    public int countCardsByUser(User user) {
        return cardDao.countCardsByUser(user);
    }

    public List<Card> getCardByUserId(long id) {
        return cardDao.getCardsByUserId(id);
    }

    /**
     * Takes from database CardsUnblockRequest that contains information about the user and his card to be unlocked.
     *
     * @return CardsUnblockRequestDto with information about user and card
     */
    public List<CardsUnblockRequestDto> getCardRequestsToUnblock() {
        return cardDao.getCardRequests();
    }

    /**
     * Checks if card exits in database.
     * Creates new Card in database and assigns User to it.
     *
     * @param card new Card to be saved
     * @param user User to be associated with Card
     * @return boolean if the Card was created or not
     * @throws CardExistException if card already exists in database
     */
    public boolean createCardWithUser(Card card, User user) throws CardExistException {
        if (cardDao.isExistCard(card.getNumber()) || user == null) throw new CardExistException();

        if (cardDao.createCardWithUser(card, user)) {
            logger.info("Card with number {} created and added to user {}", card.getNumber(), user.getEmail());
            return true;
        } else return false;
    }

    /**
     * Validates money input.
     * Replenishes the balance on the card.
     *
     * @param card  Card to be topped up
     * @param money money for card
     * @return if Card was topped up or not
     * @throws CardTopUpException if money input is invalid
     */
    public boolean topUpCard(Card card, String money, PaymentDao paymentDao) throws CardTopUpException {
        if (money.isEmpty() || !money.replaceFirst("^0*", "").matches("^[0-9]{0,5}$")) throw new CardTopUpException();

        int moneyInt = Integer.parseInt(money);

        if (moneyInt <= 0 || moneyInt > 10000) throw new CardTopUpException();


        if (cardDao.updateCardByCardIdWithMoney(card, moneyInt)) {
            paymentDao.updatePreparedPaymentByOneCard(card);
            logger.info("Card with id \"{}\" topped up", card.getId());
            return true;
        } else return false;
    }

    public boolean blockCardById(long id) {
        if (cardDao.blockCardById(id)) {
            logger.info("Card with id:\"{}\" is blocked", id);
            return true;
        } else return false;
    }

    public boolean unblockCardById(long id) {
        Card card = cardDao.getCardById(id);

        if (card == null) return false;

        if (!card.isUnderConsideration()) {
            cardDao.unblockCardById(id);
        } else {
            cardDao.unblockCardById(id);
            cardDao.deleteCardRequestByCardId(id);
        }


        return cardDao.unblockCardById(id);
    }

    /**
     * Change Card attribute 'underConsideration' to true.
     * Creates new card unblock request.
     *
     * @param card Card to be unblocked.
     * @param user User assigned to Card.
     * @return boolean if request to unblock was made or not.
     */
    public boolean makeRequestToUnblockCard(Card card, User user) {
        if (cardDao.updateCardConsiderationById(card.getId()) && cardDao.createCardUnblockRequest(card, user)) {
            logger.info("Card with id \"{}\" was requested to be unlocked by {}", card.getId(), user.getEmail());
            return true;
        } else return false;

    }

    public List<Card> sort(long id, String type, String order, int limit, int offset) {
        String query = "SELECT * FROM card WHERE user_id=? ORDER BY %s %s LIMIT %d OFFSET %d";


        if (type.equalsIgnoreCase(NUMBER)) {
            if (order.equalsIgnoreCase("ASC")) {
                query = String.format(query, NUMBER, "ASC", limit, offset);
                return cardDao.getCardByUserLimitSorted(id, query);
            } else if (order.equalsIgnoreCase("DESC")) {
                query = String.format(query, NUMBER, "DESC", limit, offset);
                return cardDao.getCardByUserLimitSorted(id, query);
            }
        }
        if (type.equalsIgnoreCase(NAME)) {

            if (order.equalsIgnoreCase("ASC")) {
                query = String.format(query, NAME, "ASC", limit, offset);
                return cardDao.getCardByUserLimitSorted(id, query);
            } else if (order.equalsIgnoreCase("DESC")) {
                query = String.format(query, NAME, "DESC", limit, offset);
                return cardDao.getCardByUserLimitSorted(id, query);
            }
        }
        if (type.equalsIgnoreCase(MONEY)) {

            if (order.equalsIgnoreCase("ASC")) {
                query = String.format(query, MONEY, "ASC", limit, offset);
                return cardDao.getCardByUserLimitSorted(id, query);
            } else if (order.equalsIgnoreCase("DESC")) {
                query = String.format(query, MONEY, "DESC", limit, offset);
                return cardDao.getCardByUserLimitSorted(id, query);
            }
        }

        return null;
    }


}

