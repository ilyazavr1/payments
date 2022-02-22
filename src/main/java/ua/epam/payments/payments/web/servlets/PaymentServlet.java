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
        PaymentsDao paymentsDao = new PaymentsDaoImpl();
        CardDao cardDao = new CardDaoImpl();

        long cardSenderId = Long.parseLong(req.getParameter("cardSenderId"));
        String cardDestinationNumber = req.getParameter("cardDestinationNumber").trim();
        String money = req.getParameter("money").trim();

        if (cardDestinationNumber != null) {
            cardDestinationNumber = cardDestinationNumber.replaceAll("[^0-9]+", "");
        }
        System.out.println(cardDestinationNumber);
        if (!cardDestinationNumber.matches("^[0-9]{16}$")) {
            req.setAttribute(Constants.INVALID_CARD_NUMBER, Constants.INVALID_CARD_NUMBER);
            doGet(req, resp);
            return;
        }
        if (money == null || money.isEmpty() || !money.matches("^[1-9][0-9]{0,4}$")) {
            req.setAttribute(Constants.INVALID_MONEY_AMOUNT, Constants.INVALID_MONEY_AMOUNT);
            doGet(req, resp);
            return;
        }

        Card cardSender = cardDao.getCardById(cardSenderId);
        Card cardDestination = cardDao.getCardByNumber(cardDestinationNumber);

        if (cardSender.isBlocked()){
            req.setAttribute(Constants.CARD_SENDER_IS_BLOCKED, Constants.CARD_SENDER_IS_BLOCKED);
            doGet(req, resp);
            return;
        }
        if (cardDestination.isBlocked()){
            req.setAttribute(Constants.CARD_DESTINATION_IS_BLOCKED, Constants.CARD_DESTINATION_IS_BLOCKED);
            doGet(req, resp);
            return;
        }

        if (cardSender.getId() == cardDestination.getId()){
            req.setAttribute(Constants.CARDS_ARE_SAME, Constants.CARDS_ARE_SAME);
            doGet(req, resp);
            return;
        }

        if (cardDestination == null) {
            req.setAttribute(Constants.INVALID_CARD, Constants.INVALID_CARD);
            doGet(req, resp);
            return;
        }

        int moneyInt = Integer.parseInt(money);
        if (req.getParameter("prepare") != null && (cardSender.getMoney() - moneyInt) >= 0) {
            paymentsDao.createPreparedPayment(cardSender, cardDestination, moneyInt);
        } else if (req.getParameter("send") != null && (cardSender.getMoney() - moneyInt) >= 0) {
            cardDao.transferMoneyFromCardToCard(cardSender.getId(), cardDestination.getId(), moneyInt);
            paymentsDao.createConfirmedPayment(cardSender, cardDestination, moneyInt);
        } else {
            req.setAttribute(Constants.OUT_OF_MONEY, Constants.OUT_OF_MONEY);
            doGet(req, resp);
            return;
        }

        resp.sendRedirect(Path.PAYMENTS_PATH);
    }
}
