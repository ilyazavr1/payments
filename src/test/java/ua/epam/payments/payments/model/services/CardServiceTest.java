package ua.epam.payments.payments.model.services;

import org.junit.Before;
import org.junit.Test;
import ua.epam.payments.payments.model.dao.CardDao;
import ua.epam.payments.payments.model.dao.PaymentDao;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.exception.CardExistException;
import ua.epam.payments.payments.model.exception.CardTopUpException;

import static org.junit.Assert.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CardServiceTest {

    private CardDao cardDao = mock(CardDao.class);
    private PaymentDao paymentDao = mock(PaymentDao.class);
    private CardService cardService = new CardService(cardDao);

    private static final int MONEY_INT = 1000;
    private static final String MONEY_STRING = "1000";
    private static final String MONEY_EMPTY_STRING = "";

    private static final long ID = 1;
    private static final String NAME = "Card";
    private static final String NUMBER = "1234123412341234";
    private static final boolean BLOCKED_F = false;
    private static final boolean UNDER_CONSIDERATION_F = false;
    private static final long USER_ID = 11;


    private Card card;
    private User USER;
    private final static User USER_NULL = null;

    @Before
    public void setUp() throws Exception {
        USER = new User.Builder().build();
        card = new Card.Builder()
                .withId(ID)
                .withName(NAME)
                .withNumber(NUMBER)
                .withMoney(MONEY_INT)
                .withBlocked(BLOCKED_F)
                .withUnderConsideration(UNDER_CONSIDERATION_F)
                .withUserId(USER_ID)
                .build();
    }

    @Test
    public void createCardWithUserShouldThrowCardExistException() {
        when(cardDao.isExistCard(card.getNumber())).thenReturn(true);

        assertThrows(CardExistException.class, () -> cardService.createCardWithUser(card, USER));
        assertThrows(CardExistException.class, () -> cardService.createCardWithUser(card, USER_NULL));
    }

    @Test
    public void createCardWithUserShouldNotThrowException() throws CardExistException {
        when(cardDao.isExistCard(card.getNumber())).thenReturn(false);
        when(cardDao.createCardWithUser(card, USER)).thenReturn(true);

        assertDoesNotThrow(() -> cardService.createCardWithUser(card, USER));
        assertTrue(cardService.createCardWithUser(card, USER));
    }

    @Test
    public void topUpCardShouldThrowCardTopUpException() {


        assertThrows(CardTopUpException.class, () -> cardService.topUpCard(card, "", paymentDao));
        assertThrows(CardTopUpException.class, () -> cardService.topUpCard(card, "dsd", paymentDao));
        assertThrows(CardTopUpException.class, () -> cardService.topUpCard(card, "10001", paymentDao));
        assertThrows(CardTopUpException.class, () -> cardService.topUpCard(card, "-1", paymentDao));
        assertThrows(CardTopUpException.class, () -> cardService.topUpCard(card, "123123123213", paymentDao));

    }

    @Test
    public void topUpCardShouldNotThrowException() throws CardTopUpException {
        when(cardDao.updateCardByCardIdWithMoney(card, MONEY_INT)).thenReturn(true);

        assertDoesNotThrow(() -> cardService.topUpCard(card, MONEY_STRING, paymentDao));
        assertTrue(cardService.topUpCard(card, MONEY_STRING, paymentDao));
    }

    @Test
    public void unblockCardByIdReturnFalls() {
        when(cardDao.getCardById(ID)).thenReturn(null);

        assertFalse(cardService.unblockCardById(ID));
    }

    @Test
    public void unblockCardByIdReturnTrue() {
        when(cardDao.getCardById(ID)).thenReturn(card);
        when(cardDao.unblockCardById(ID)).thenReturn(true);

        assertTrue(cardService.unblockCardById(ID));
    }

    @Test
    public void makeRequestToUnblockCardReturnFalse() {

        when(cardDao.updateCardConsiderationById(card.getId())).thenReturn(false);
        when(cardDao.createCardUnblockRequest(card, USER)).thenReturn(false);

        assertFalse(cardService.makeRequestToUnblockCard(card,USER));

    }
    @Test
    public void makeRequestToUnblockCardReturnTrue() {

        when(cardDao.updateCardConsiderationById(card.getId())).thenReturn(true);
        when(cardDao.createCardUnblockRequest(card, USER)).thenReturn(true);

        assertTrue(cardService.makeRequestToUnblockCard(card,USER));

    }


}