package ua.epam.payments.payments.web.servlets.admin;


import ua.epam.payments.payments.dao.CardDao;
import ua.epam.payments.payments.dao.PaymentsDao;
import ua.epam.payments.payments.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.dao.impl.PaymentsDaoImpl;
import ua.epam.payments.payments.model.entity.Payment;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminConfirmPaymentServlet", value = Path.ADMIN_CONFIRM_PAYMENT_PATH)
public class AdminConfirmPaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long paymentId = Long.parseLong(req.getParameter("paymentId"));
        System.out.println(paymentId);
        if (paymentId < 0) {
            resp.sendRedirect(Path.ADMIN_ALL_PAYMENTS_REQUESTS_PATH);
            return;
        }
        PaymentsDao paymentsDao = new PaymentsDaoImpl();
        Payment payment = paymentsDao.getPaymentById(paymentId);

        CardDao cardDao = new CardDaoImpl();

        if (!cardDao.transferMoneyFromCardToCard(payment.getCardSenderId(), payment.getCardDestinationId(), payment.getMoney())) {
            System.out.println("transaction failed");
            resp.sendRedirect(Path.ADMIN_ALL_PAYMENTS_REQUESTS_PATH);
            return;
        }
        if (paymentsDao.confirmPayment(paymentId)) {
            System.out.println("payment confirmed");
        }


        //req.getRequestDispatcher(Path.ADMIN_ALL_PAYMENTS_REQUESTS_PATH).forward(req,resp);
        resp.sendRedirect(Path.ADMIN_ALL_PAYMENTS_REQUESTS_PATH);
    }
}

