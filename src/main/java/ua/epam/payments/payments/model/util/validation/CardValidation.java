package ua.epam.payments.payments.model.util.validation;

import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.exception.InvalidCardNumberException;
import ua.epam.payments.payments.web.Constants;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class CardValidation {

   private List<String> errors = new ArrayList<>();


    public boolean isCardsValid(Card cardSender, Card cardDestination) throws InvalidCardNumberException {
        if (cardSender == null) throw new InvalidCardNumberException();
        if (cardDestination == null) throw new InvalidCardNumberException();

        if (cardSender.isBlocked()) errors.add(Constants.CARD_SENDER_IS_BLOCKED);


        if (cardDestination.isBlocked()) errors.add(Constants.CARD_DESTINATION_IS_BLOCKED);


        if (cardSender.getId() == cardDestination.getId()) errors.add(Constants.CARDS_ARE_SAME);


        return errors.isEmpty();
    }

    public boolean isCardNumberValid(String cardNumber){
        if (!cardNumber.isEmpty()) {
            cardNumber = cardNumber.replaceAll("[^0-9]+", "").trim();
        }

        return cardNumber.matches("^[0-9]{16}$");
    }

    public List<String> getErrors() {
        return errors;
    }
}
