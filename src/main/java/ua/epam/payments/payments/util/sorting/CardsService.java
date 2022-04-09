package ua.epam.payments.payments.util.sorting;

import ua.epam.payments.payments.dao.CardDao;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;
import java.util.List;

public class CardsService {
    private static final String NUMBER = "number";
    private static final String NAME = "name";
    private static final String MONEY = "money";

    public List<Card> sort(CardDao cardDao, User user, String type, String order, int limit, int offset) {
        String query = "SELECT * FROM card WHERE user_id=? ORDER BY %s %s LIMIT %d OFFSET %d";


        if (type.equalsIgnoreCase(NUMBER)) {
            if (order.equalsIgnoreCase("ASC")) {
                query = String.format(query, NUMBER,"ASC",limit,offset);
                return cardDao.getCardByUserLimitSorted(user, query);
            } else if (order.equalsIgnoreCase("DESC")) {
                query = String.format(query, NUMBER,"DESC",limit,offset);
                return cardDao.getCardByUserLimitSorted(user, query);
            }
        }
        if (type.equalsIgnoreCase(NAME)) {

            if (order.equalsIgnoreCase("ASC")) {
                query = String.format(query, NAME,"ASC",limit,offset);
                return cardDao.getCardByUserLimitSorted(user, query);
            } else if (order.equalsIgnoreCase("DESC")) {
                query = String.format(query, NAME,"DESC",limit,offset);
                return cardDao.getCardByUserLimitSorted(user, query);
            }
        }
        if (type.equalsIgnoreCase(MONEY)) {

            if (order.equalsIgnoreCase("ASC")) {
                query = String.format(query, MONEY,"ASC",limit,offset);
                return cardDao.getCardByUserLimitSorted(user, query);
            } else if (order.equalsIgnoreCase("DESC")) {
                query = String.format(query, MONEY,"DESC",limit,offset);
                return cardDao.getCardByUserLimitSorted(user, query);
            }
        }

        return null;
    }
}

