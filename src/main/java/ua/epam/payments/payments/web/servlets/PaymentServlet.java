package ua.epam.payments.payments.web.servlets;


import ua.epam.payments.payments.dao.CardDao;
import ua.epam.payments.payments.dao.PaymentsDao;
import ua.epam.payments.payments.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.dao.impl.PaymentsDaoImpl;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.web.Constants;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PaymentServlet", value = Path.PAYMENT_PATH)
public class PaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CardDao cardDao = new CardDaoImpl();

        User user = (User) req.getSession().getAttribute("user");

        List<Card> cards = cardDao.getCardByUser(user);

        req.setAttribute("cards", cards);

        req.getRequestDispatcher(Path.PAYMENT_JSP).forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CardDao cardDao = new CardDaoImpl();
        PaymentsDao paymentsDao = new PaymentsDaoImpl();

        int cardSenderNumber = Integer.parseInt(req.getParameter("cardSenderId"));
        int cardDestinationNumber = Integer.parseInt(req.getParameter("cardDestinationId"));
        int money = Integer.parseInt(req.getParameter("money"));

        Card cardSender = cardDao.getCardById(cardSenderNumber);
        Card cardDestination = cardDao.getCardById(cardDestinationNumber);

        if (cardSender.getMoney() < money) {
            req.setAttribute(Constants.OUT_OF_MONEY, Constants.OUT_OF_MONEY);
            req.getRequestDispatcher(Path.PAYMENT_JSP).forward(req, resp);
            return;
        }

        paymentsDao.createPayment(cardSender, cardDestination, money);


        //  System.out.println(cardDao.transferMoneyFromCardToCard(cardSender.getId(), cardDestination.getId(), money));


        resp.sendRedirect(Path.PROFILE_PATH);
    }
}
