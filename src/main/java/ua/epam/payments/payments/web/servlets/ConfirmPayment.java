package ua.epam.payments.payments.web.servlets;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.model.dao.impl.PaymentsDaoImpl;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.Payment;
import ua.epam.payments.payments.model.exception.CardBlockedException;
import ua.epam.payments.payments.model.exception.OutOfMoneyException;
import ua.epam.payments.payments.model.services.CardService;
import ua.epam.payments.payments.model.services.PaymentService;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;


@WebServlet(name = "ConfirmPayment", value = Path.PAYMENTS_CONFIRM_PATH)
public class ConfirmPayment extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ConfirmPayment.class);


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("ConfirmPayment started");

        PaymentService paymentService = new PaymentService(new PaymentsDaoImpl(),new CardDaoImpl());
        long paymentId = Long.parseLong(req.getParameter("paymentId"));

        try {
            paymentService.confirmPayment(paymentId);
        } catch (CardBlockedException | OutOfMoneyException e) {
            resp.sendRedirect(Path.PAYMENTS_PATH+"?invalidPayment="+paymentId);
            return;
        }

        resp.sendRedirect(Path.PAYMENTS_PATH);
    }

}
