package ua.epam.payments.payments.dao;

import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;

import java.util.List;

public interface CardDao {

    Card getCardById(long id);

    Card getCardByNumber(String number);

    List<Card> getCardByUser(User user);

    List<Card> getCardByUserLimit(User user, int limit, int offset);

    boolean createCardWithUser(Card card, User user);

    boolean addCardToUser(Card card, User user);

    boolean updateCardWithMoney(Card card, int money);

    boolean transferMoneyFromCardToCard(long cardSenderId, long cardDestinationId, int money);

    boolean isExistCard(String number);

}
