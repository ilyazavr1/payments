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

@WebServlet(name = "CardTopUpServlet", value = Path.CARD_TOP_UP_PATH)
public class CardTopUpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CardDao cardDao = new CardDaoImpl();

        long cardId = Long.parseLong(req.getParameter("id"));

        Card card = cardDao.getCardById(cardId);
        req.setAttribute("card", card);


        req.getRequestDispatcher(Path.CARD_TOP_UP_JSP).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CardDao cardDao = new CardDaoImpl();
        int money = Integer.parseInt(req.getParameter("money"));
        long cardId = Long.parseLong(req.getParameter("id"));

        User user = (User) req.getSession().getAttribute("user");
        Card card = cardDao.getCardById(cardId);

        if (money < 0 || user == null || card == null){
            System.out.println("user or money or card is absent");
            resp.sendRedirect(Path.CARD_PATH);
        }

        cardDao.updateCardWithMoney(card, money);


        //TODO top up card
        resp.sendRedirect(Path.CARD_PATH);
    }
}
