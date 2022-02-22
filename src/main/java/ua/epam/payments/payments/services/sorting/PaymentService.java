package ua.epam.payments.payments.services.sorting;

import ua.epam.payments.payments.dao.CardDao;
import ua.epam.payments.payments.dao.PaymentsDao;
import ua.epam.payments.payments.model.dto.FullPaymentDto;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PaymentService {
    private static final String CREATION_TIMESTAMP = "creation_timestamp";
    private static final String NUMBER = "payment.id";

    public List<FullPaymentDto> sort(PaymentsDao paymentsDao, User user, String type, String order, int limit, int offset) {
/*        String q = "SELECT payment.id, payment.money, (SELECT status  FROM payment_status WHERE payment.payment_status_id = payment_status.id),\n" +
                "       payment.creation_timestamp, (SELECT card.number as sender_card_number FROM card WHERE card.id = payment.card_sender_id),\n" +
                "       (SELECT card.number as destination_card_number FROM card WHERE card.id = payment.card_destination_id) FROM payment\n" +
                "WHERE card_sender_id IN (SELECT card.id FROM card WHERE user_id =?) ORDER BY %s %s LIMIT %d OFFSET %d";*/
        String query = "SELECT payment.id,\n" +
                "       (SELECT card.number as sender_card_number FROM card WHERE card.id = payment.card_sender_id),\n" +
                "       (SELECT money as sender_balance FROM card WHERE payment.card_sender_id = card.id ),\n" +
                "       payment.money,\n" +
                "       (SELECT \"user\".first_name FROM \"user\",card WHERE payment.card_destination_id=card.id and card.user_id = \"user\".id),\n" +
                "       (SELECT \"user\".last_name FROM \"user\",card WHERE payment.card_destination_id=card.id and card.user_id = \"user\".id),\n" +
                "       (SELECT \"user\".surname FROM \"user\",card WHERE payment.card_destination_id=card.id and card.user_id = \"user\".id),\n" +
                "       (SELECT card.number as destination_card_number FROM card WHERE card.id = payment.card_destination_id),\n" +
                "       payment.creation_timestamp,\n" +
                "       (SELECT status  FROM payment_status WHERE payment.payment_status_id = payment_status.id)\n" +
                "FROM payment\n" +
                "WHERE card_sender_id IN (SELECT card.id FROM card WHERE user_id =?) ORDER BY %s %s LIMIT %d OFFSET %d";

        if (type.equalsIgnoreCase(CREATION_TIMESTAMP)) {

            if (order.equalsIgnoreCase("ASC")) {
                query = String.format(query, CREATION_TIMESTAMP, "ASC", limit, offset);
                return paymentsDao.getFullPaymentsByUserLimitSorted(user, query);
            } else if (order.equalsIgnoreCase("DESC")) {
                query = String.format(query, CREATION_TIMESTAMP, "DESC", limit, offset);
                return paymentsDao.getFullPaymentsByUserLimitSorted(user, query);
            }
        }
        if (type.equalsIgnoreCase(NUMBER)) {

            if (order.equalsIgnoreCase("ASC")) {
                query = String.format(query, NUMBER, "ASC", limit, offset);
                return paymentsDao.getFullPaymentsByUserLimitSorted(user, query);
            } else if (order.equalsIgnoreCase("DESC")) {
                query = String.format(query, NUMBER, "DESC", limit, offset);
                return paymentsDao.getFullPaymentsByUserLimitSorted(user, query);
            }
        }

        return new ArrayList<>();
    }
}