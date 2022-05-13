package ua.epam.payments.payments.model.services;

import ua.epam.payments.payments.model.dao.CardDao;
import ua.epam.payments.payments.model.entity.dto.CardsUnblockRequestDto;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.exception.CardExistException;
import ua.epam.payments.payments.model.exception.CardTopUpException;

import java.util.List;

public class CardService {
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

        return cardDao.createCardWithUser(card, user);
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
    public boolean topUpCard(Card card, String money) throws CardTopUpException {
        if (money.isEmpty() || !money.replaceFirst("^0*", "").matches("^[0-9]{0,5}$")) throw new CardTopUpException();

        int moneyInt = Integer.parseInt(money);

        if (moneyInt <= 0 || moneyInt > 10000) throw new CardTopUpException();

        return cardDao.updateCardWithMoney(card, moneyInt);
    }

    public boolean blockCardById(long id) {

        return cardDao.blockCardById(id);
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
        return cardDao.updateCardConsiderationById(card.getId()) && cardDao.createCardUnblockRequest(card, user);
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

