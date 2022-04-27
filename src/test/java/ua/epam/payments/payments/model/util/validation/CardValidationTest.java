package ua.epam.payments.payments.model.util.validation;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.exception.InvalidCardNumberException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


public class CardValidationTest {

    private static final String VALID_CARD_NUMBER = "1234123412341234";
    private static final String INVALID_CARD_NUMBER = "12341234asddasdd";

    private Card cardSender;
    private Card cardDestination;

    private CardValidation cardValidation = new CardValidation();

    @Before
    public void setUp() throws Exception {
        cardSender = new Card.Builder().withId(1).withNumber("1234123412341234").withBlocked(false).build();
        cardDestination = new Card.Builder().withId(2).withNumber("1234000000000000").withBlocked(false).build();
    }

    @Test
    public void isCardsValidShouldNotThrowException() throws InvalidCardNumberException {
        assertDoesNotThrow(() -> cardValidation.isCardsValid(cardSender, cardDestination));
        assertTrue(cardValidation.isCardsValid(cardSender, cardDestination));
    }


    @Test
    public void isCardsValidShouldInvalidCardNumberException() {
        cardSender = null;
        assertThrows(InvalidCardNumberException.class, () -> cardValidation.isCardsValid(cardSender, cardDestination));

    }

    @Test
    public void isCardsValidReturnFalse() throws InvalidCardNumberException {
        cardSender.setBlocked(true);

        assertFalse(cardValidation.isCardsValid(cardSender, cardDestination));
    }


    @Test
    public void testGetErrorsWithThreeInvalidDataInput() throws InvalidCardNumberException{
        cardSender.setBlocked(true);
        cardDestination.setBlocked(true);
        cardDestination.setId(1);

        cardValidation.isCardsValid(cardSender, cardDestination);

        assertEquals(3, cardValidation.getErrors().size());
    }

    @Test
    public void isCardNumberValidReturnFalls(){

        assertFalse(cardValidation.isCardNumberValid(VALID_CARD_NUMBER+"1"));
        assertFalse(cardValidation.isCardNumberValid(INVALID_CARD_NUMBER));
        assertFalse(cardValidation.isCardNumberValid(""));
    }

    @Test
    public void isCardNumberValidReturnTrue(){
        assertTrue(cardValidation.isCardNumberValid(VALID_CARD_NUMBER));
    }
}