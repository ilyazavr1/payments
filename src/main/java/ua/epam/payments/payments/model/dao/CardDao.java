package ua.epam.payments.payments.model.dao;

import ua.epam.payments.payments.model.entity.dto.CardsUnblockRequestDto;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;

import java.util.List;

public interface CardDao {

    Card getCardById(long id);

    Card getCardByNumber(String number);

    List<Card> getCardsByUserId(long id);

    List<Card> getCardByUserLimitSorted(long id, String query);

    List<CardsUnblockRequestDto> getCardRequests();

    int countCardsByUser(User user);

    boolean createCardWithUser(Card card, User user);

    boolean updateCardByCardIdWithMoney(Card card, int money);

    boolean blockCardById(long id);

    boolean unblockCardById(long id);

    boolean deleteCardRequestByCardId(long id);

    boolean updateCardConsiderationById(long id);

    boolean createCardUnblockRequest(Card card, User user);

    boolean transferMoneyFromCardToCard(long cardSenderId, long cardDestinationId, int money);

    boolean isExistCard(String number);

}
