package ua.epam.payments.payments.web.servlets;


import ua.epam.payments.payments.dao.CardDao;
import ua.epam.payments.payments.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PaymentsServlet", value = Path.PAYMENT_PATH)
public class PaymentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // UserDao userDao = new UserDaoImpl();
        CardDao cardDao = new CardDaoImpl();

        User user = (User) req.getSession().getAttribute("user");

        List<Card> cards = cardDao.getCardByUser(user);

        req.setAttribute("cards", cards);

        req.getRequestDispatcher(Path.PAYMENT_JSP).forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CardDao cardDao = new CardDaoImpl();

        int cardSenderNumber = Integer.parseInt(req.getParameter("cardSenderId"));
        int cardDestinationNumber = Integer.parseInt(req.getParameter("cardDestinationId"));
        int money= Integer.parseInt(req.getParameter("money"));

        Card cardFrom = cardDao.getCardById(cardSenderNumber);
        Card cardTo = cardDao.getCardById(cardDestinationNumber);

        if (cardFrom.getMoney() < money){
            System.out.println("out of money");
            resp.sendRedirect(Path.PAYMENT_PATH);;
            return;
        }

        System.out.println(cardDao.transferMoneyFromCardToCard(cardFrom.getId(), cardTo.getId(), money));



        resp.sendRedirect(Path.PROFILE_PATH);
    }
}
