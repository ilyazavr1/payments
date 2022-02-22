package ua.epam.payments.payments.web.servlets;


import ua.epam.payments.payments.dao.CardDao;
import ua.epam.payments.payments.dao.PaymentsDao;
import ua.epam.payments.payments.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.dao.impl.PaymentsDaoImpl;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.Payment;
import ua.epam.payments.payments.web.Constants;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "ConfirmPayment", value = Path.PAYMENTS_CONFIRM_PATH)
public class ConfirmPayment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PaymentsDao paymentsDao = new PaymentsDaoImpl();
        CardDao cardDao = new CardDaoImpl();

        long paymentId = Long.parseLong(req.getParameter("paymentId"));
        Payment payment = paymentsDao.getPaymentById(paymentId);

        long cardSenderId = payment.getCardSenderId();
        long cardDestinationId = payment.getCardDestinationId();
        int moneyToTransfer = payment.getMoney();

        Card cardSender = cardDao.getCardById(cardSenderId);

        if (cardSender.getMoney() - moneyToTransfer >= 0) {
            cardDao.transferMoneyFromCardToCard(cardSenderId, cardDestinationId, moneyToTransfer);
            paymentsDao.confirmPayment(payment.getId());

        } else {
            resp.sendRedirect(Path.PAYMENTS_PATH+"?invalidPayment="+paymentId);
            return;
        }

        System.out.println(payment);


        resp.sendRedirect(Path.PAYMENTS_PATH);
    }

}
