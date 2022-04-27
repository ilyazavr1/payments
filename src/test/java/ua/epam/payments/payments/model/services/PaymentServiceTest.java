package ua.epam.payments.payments.model.services;

import org.junit.Before;
import org.junit.Test;
import ua.epam.payments.payments.model.dao.CardDao;
import ua.epam.payments.payments.model.dao.PaymentDao;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.Payment;
import ua.epam.payments.payments.model.exception.CardBlockedException;
import ua.epam.payments.payments.model.exception.InvalidMoneyException;
import ua.epam.payments.payments.model.exception.OutOfMoneyException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    private PaymentService paymentService;
    private PaymentDao paymentDao;
    private CardDao cardDao;
    private Payment PAYMENT;
    private Card BLOCKED_CARD = new Card.Builder().withBlocked(true).build();
    private Card UNBLOCKED_CARD_SEND = new Card.Builder()
            .withId(1)
            .withName("Send")
            .withNumber("1234123412341234")
            .withUnderConsideration(false)
            .withMoney(1000)
            .withUserId(1)
            .withBlocked(false)
            .build();
    private Card UNBLOCKED_CARD_DEST = new Card.Builder()
            .withId(2)
            .withName("Dest")
            .withNumber("1234123412341232")
            .withUnderConsideration(false)
            .withMoney(1000)
            .withUserId(12)
            .withBlocked(false)
            .build();
    private static final long ID = 1;
    private static final int BALANCE = 1111;
    private static final int MONEY_INT = 10;
    private static final String MONEY_STRING = "10";
    private static final String MONEY_EMPTY_STRING = "";
    private static final int PAYMENT_STATUS_ID = 1;
    private static final LocalDateTime TIME = LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40);
    private static final long SENDER_CARD_ID = 22;
    private static final long DESTINATION_CARD_ID = 33;

    @Before
    public void setUp() throws Exception {
        paymentDao = mock(PaymentDao.class);
        cardDao = mock(CardDao.class);
        paymentService = new PaymentService(paymentDao, cardDao);

        PAYMENT = new Payment.Builder()
                .withId(ID)
                .withBalance(BALANCE)
                .withMoney(MONEY_INT)
                .withPaymentStatusId(PAYMENT_STATUS_ID)
                .withCreationTimestamp(TIME)
                .withCardSenderId(SENDER_CARD_ID)
                .withCardDestinationId(DESTINATION_CARD_ID)
                .build();
    }

    @Test
    public void confirmPaymentShouldThrowOutOfMoneyException() {
        PAYMENT.setBalance(0);
        PAYMENT.setMoney(100);
        when(paymentDao.getPaymentById(ID)).thenReturn(PAYMENT);

        assertThrows(OutOfMoneyException.class, () -> paymentService.confirmPayment(ID));

    }

    @Test
    public void confirmPaymentShouldThrowCardBlockedException() {
        PAYMENT.setBalance(100);
        PAYMENT.setMoney(1);
        when(paymentDao.getPaymentById(ID)).thenReturn(PAYMENT);
        when(cardDao.getCardById(PAYMENT.getCardSenderId())).thenReturn(BLOCKED_CARD);
        when(cardDao.getCardById(PAYMENT.getCardDestinationId())).thenReturn(BLOCKED_CARD);

        assertThrows(CardBlockedException.class, () -> paymentService.confirmPayment(ID));
    }

    @Test
    public void confirmPaymentShouldNotThrowExceptions() throws OutOfMoneyException, CardBlockedException {
        PAYMENT.setBalance(100);
        PAYMENT.setMoney(1);
        when(paymentDao.getPaymentById(ID)).thenReturn(PAYMENT);
        when(cardDao.getCardById(PAYMENT.getCardSenderId())).thenReturn(UNBLOCKED_CARD_SEND);
        when(cardDao.getCardById(PAYMENT.getCardDestinationId())).thenReturn(UNBLOCKED_CARD_DEST);
        when(cardDao.transferMoneyFromCardToCard(UNBLOCKED_CARD_SEND.getId(), UNBLOCKED_CARD_DEST.getId(), PAYMENT.getMoney())).thenReturn(true);
        when(paymentDao.confirmPayment(PAYMENT.getId())).thenReturn(true);

        assertDoesNotThrow(() -> paymentService.confirmPayment(ID));
        assertTrue(paymentService.confirmPayment(ID));
    }





    @Test
    public void createPreparedPaymentShouldThrowInvalidMoneyException() {
        assertThrows(InvalidMoneyException.class,
                () -> paymentService.createPreparedPayment(UNBLOCKED_CARD_SEND, UNBLOCKED_CARD_DEST, ""));
        assertThrows(InvalidMoneyException.class,
                () -> paymentService.createPreparedPayment(UNBLOCKED_CARD_SEND, UNBLOCKED_CARD_DEST, "qwe"));

    }

    @Test
    public void createPreparedPaymentShouldThrowOutOfMoneyException() {
        assertThrows(OutOfMoneyException.class,
                () -> paymentService.createPreparedPayment(UNBLOCKED_CARD_SEND, UNBLOCKED_CARD_DEST, "10000"));
    }


    @Test
    public void createPreparedPaymentShouldNotThrowExceptions() {
        when(paymentDao.createPreparedPayment(UNBLOCKED_CARD_SEND, UNBLOCKED_CARD_DEST, 10)).thenReturn(true);
        assertDoesNotThrow(() -> paymentService.createPreparedPayment(UNBLOCKED_CARD_SEND, UNBLOCKED_CARD_DEST, "10"));
    }

    @Test
    public void makePaymentShouldThrowInvalidMoneyException() {
        assertThrows(InvalidMoneyException.class,
                () -> paymentService.makePayment(UNBLOCKED_CARD_SEND, UNBLOCKED_CARD_DEST, ""));
        assertThrows(InvalidMoneyException.class,
                () -> paymentService.makePayment(UNBLOCKED_CARD_SEND, UNBLOCKED_CARD_DEST, "10001"));
        assertThrows(InvalidMoneyException.class,
                () -> paymentService.makePayment(UNBLOCKED_CARD_SEND, UNBLOCKED_CARD_DEST, "-1"));
    }

    @Test
    public void makePaymentShouldNotThrowException() throws OutOfMoneyException, InvalidMoneyException {

        when(cardDao.transferMoneyFromCardToCard(UNBLOCKED_CARD_SEND.getId(), UNBLOCKED_CARD_DEST.getId(), PAYMENT.getMoney())).thenReturn(true);
        when(paymentDao.createConfirmedPayment(UNBLOCKED_CARD_SEND, UNBLOCKED_CARD_DEST, 10)).thenReturn(true);


        assertDoesNotThrow(() -> paymentService.makePayment(UNBLOCKED_CARD_SEND, UNBLOCKED_CARD_DEST, MONEY_STRING));


    }


}