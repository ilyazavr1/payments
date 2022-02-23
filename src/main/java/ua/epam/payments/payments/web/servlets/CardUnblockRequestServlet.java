package ua.epam.payments.payments.web.servlets;


import ua.epam.payments.payments.dao.CardDao;
import ua.epam.payments.payments.dao.UserDao;
import ua.epam.payments.payments.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.dao.impl.UserDaoImpl;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CardUnblockRequestServlet", value = Path.CARD_UNBLOCK_REQUEST_PATH)
public class CardUnblockRequestServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameter("id"));
        System.out.println("unblock");
        long cardId = Long.parseLong(req.getParameter("id"));

        CardDao cardDao = new CardDaoImpl();
        UserDao userDao = new UserDaoImpl();

        Card card = cardDao.getCardById(cardId);
        User user = userDao.getUserById(card.getUserId());

        System.out.println(card);
        System.out.println(user);
        cardDao.updateCardConsiderationById(card.getId());
        cardDao.createCardUnblockRequest(card, user);


        resp.sendRedirect(Path.CARDS_PATH);
    }
}