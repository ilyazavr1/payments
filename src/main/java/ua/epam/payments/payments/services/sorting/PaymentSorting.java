package ua.epam.payments.payments.services.sorting;

import ua.epam.payments.payments.model.dto.FullPaymentDto;

import java.util.Comparator;
import java.util.List;

public class PaymentSorting {

    public void sortByNumberAndOrder(List<FullPaymentDto> list, String type, String order) {
        Comparator<FullPaymentDto> comparatorId = Comparator.comparing(FullPaymentDto::getId);
        Comparator<FullPaymentDto> comparatorIdReversed = Comparator.comparing(FullPaymentDto::getId, Comparator.reverseOrder());
        Comparator<FullPaymentDto> comparatorDate = Comparator.comparing(FullPaymentDto::getCreationTimestamp);
        Comparator<FullPaymentDto> comparatorDateReversed = Comparator.comparing(FullPaymentDto::getCreationTimestamp, Comparator.reverseOrder());

        if (type.equalsIgnoreCase("number")) {

            if (order.equalsIgnoreCase("ASC")) {
                list.sort(comparatorId);
            } else if (order.equalsIgnoreCase("DESC")) {
                list.sort(comparatorIdReversed);
            }
        }
        if (type.equalsIgnoreCase("date")) {

            if (order.equalsIgnoreCase("ASC")) {
                list.sort(comparatorDate);
            } else if (order.equalsIgnoreCase("DESC")) {
                list.sort(comparatorDateReversed);
            }
        }

    }
}