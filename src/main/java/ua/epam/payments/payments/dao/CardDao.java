package ua.epam.payments.payments.dao;

import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;

import java.util.List;

public interface CardDao {

    Card getCardById(long id);

    Card getCardByNumber(String number);

    List<Card> getCardByUser(User user);

    int countCardsByUser(User user);

    List<Card> getCardByUserLimit(User user, int limit, int offset);

    List<Card> getCardByUserLimitSorted(User user, String query);

    boolean createCardWithUser(Card card, User user);

    boolean addCardToUser(Card card, User user);

    boolean updateCardWithMoney(Card card, int money);

    boolean blockCardById(long id);

    boolean updateCardConsiderationById(long id);

    boolean createCardUnblockRequest(Card card, User user);

    boolean transferMoneyFromCardToCard(long cardSenderId, long cardDestinationId, int money);

    boolean isExistCard(String number);

}
