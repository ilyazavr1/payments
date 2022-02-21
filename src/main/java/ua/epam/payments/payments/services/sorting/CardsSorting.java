package ua.epam.payments.payments.services.sorting;

import ua.epam.payments.payments.model.dto.FullPaymentDto;
import ua.epam.payments.payments.model.entity.Card;

import java.util.Comparator;
import java.util.List;

public class CardsSorting {
    public void sortByNumberOrOrderOrMoney(List<Card> list, String type, String order) {
        Comparator<Card> comparatorNumber = Comparator.comparing(Card::getNumber);
        Comparator<Card> comparatorNumberReversed = Comparator.comparing(Card::getNumber, Comparator.reverseOrder());
        Comparator<Card> comparatorName = Comparator.comparing(Card::getName);
        Comparator<Card> comparatorNameReversed = Comparator.comparing(Card::getName, Comparator.reverseOrder());
        Comparator<Card> comparatorMoney = Comparator.comparing(Card::getMoney);
        Comparator<Card> comparatorMoneyReversed = Comparator.comparing(Card::getMoney, Comparator.reverseOrder());

        if (type.equalsIgnoreCase("number")) {

            if (order.equalsIgnoreCase("ASC")) {
                list.sort(comparatorNumber);
            } else if (order.equalsIgnoreCase("DESC")) {
                list.sort(comparatorNumberReversed);
            }
        }
        if (type.equalsIgnoreCase("name")) {

            if (order.equalsIgnoreCase("ASC")) {
                list.sort(comparatorName);
            } else if (order.equalsIgnoreCase("DESC")) {
                list.sort(comparatorNameReversed);
            }
        }
        if (type.equalsIgnoreCase("money")) {

            if (order.equalsIgnoreCase("ASC")) {
                list.sort(comparatorMoney);
            } else if (order.equalsIgnoreCase("DESC")) {
                list.sort(comparatorMoneyReversed);
            }
        }
    }
}