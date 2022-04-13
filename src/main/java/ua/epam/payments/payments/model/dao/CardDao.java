package ua.epam.payments.payments.model.dao;

import ua.epam.payments.payments.model.entity.dto.CardsUnblockRequestDto;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;

import java.util.List;

public interface CardDao {

    Card getCardById(long id);

    Card getCardByNumber(String number);

    List<Card> getCardByUserId(long id);

    List<Card> getCardByUserLimitSorted(long id, String query);

    //List<Card> getCardByUserLimit(User user, int limit, int offset);

    List<CardsUnblockRequestDto> getCardRequests();

    //int countCardsByUser(User user);

    boolean createCardWithUser(Card card, User user);

    //boolean addCardToUser(Card card, User user);

    boolean updateCardWithMoney(Card card, int money);

    boolean blockCardById(long id);

    boolean unblockCardById(long id);

    boolean deleteCardRequestByCardId(long id);

    boolean updateCardConsiderationById(long id);

    boolean createCardUnblockRequest(Card card, User user);

    boolean transferMoneyFromCardToCard(long cardSenderId, long cardDestinationId, int money);

    boolean isExistCard(String number);

}
