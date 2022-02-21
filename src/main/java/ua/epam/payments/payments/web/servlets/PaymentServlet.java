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

        String cardSenderNumber = req.getParameter("cardSenderId");
        String cardDestinationNumber = req.getParameter("cardDestinationNumber").trim();
        String money = req.getParameter("money");
        System.out.println(cardDestinationNumber);
        System.out.println(cardDestinationNumber.matches("^[0-9]{16}$"));
        if (cardDestinationNumber == null || cardDestinationNumber.isEmpty() || !cardDestinationNumber.matches("^[0-9]{16}$")){
            req.setAttribute(Constants.INVALID_CARD_NUMBER, Constants.INVALID_CARD_NUMBER);
            doGet(req, resp);
            return;
        }
        if (money == null || money.isEmpty() || !money.matches("^[0-9]{1,4}$")){
            req.setAttribute(Constants.INVALID_MONEY_AMOUNT, Constants.INVALID_MONEY_AMOUNT);
            doGet(req, resp);
            return;
        }

     /*   Card cardSender = cardDao.getCardById(cardSenderNumber);
        Card cardDestination = cardDao.getCardById(cardDestinationNumber);

        if (cardSender.getMoney() < money) {
            req.setAttribute(Constants.OUT_OF_MONEY, Constants.OUT_OF_MONEY);
            req.getRequestDispatcher(Path.PAYMENT_JSP).forward(req, resp);
            return;
        }

        paymentsDao.createPayment(cardSender, cardDestination, money);
*/


            resp.sendRedirect(Path.PAYMENTS_PATH);
    }
}
