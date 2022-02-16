package ua.epam.payments.payments.web.servlets;

import ua.epam.payments.payments.dao.CardDao;
import ua.epam.payments.payments.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.services.CardGeneration;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CardServlet", value = Path.CARD_PATH)
public class CardServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getSession().getAttribute("user") != null) {
            CardDao cardDao = new CardDaoImpl();
            User user = (User) req.getSession().getAttribute("user");
            List<Card> cardsList = cardDao.getCardByUser(user);
            //  System.out.println(accountList);
            req.setAttribute("cards", cardDao.getCardByUser(user));

            req.getRequestDispatcher(Path.CARDS_JSP).forward(req, resp);
        } else req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CardDao cardDao = new CardDaoImpl();

        String cardNumber;
        Card newCard;
        List<Card> cards;
        User user;

        //add account to db
      /*  do {
        } while (!accountDao.isExistAccount(accountNumber));*/
        cardNumber = CardGeneration.generateCardNumber();

        user = (User) req.getSession().getAttribute("user");

        //add account to user in db
        if (user == null) {
            System.out.println("user is null:CardServlet");
            resp.sendRedirect(Path.CARD_PATH);
        }

        newCard = new Card.Builder().withNumber(cardNumber).build();
        cardDao.createCardWithUser(newCard, user);

        //add accounts to session
        cards = cardDao.getCardByUser(user);
        req.setAttribute("cards", cards);


        //TODO вывести аккаунты юзера

        resp.sendRedirect(Path.CARD_PATH);

    }
}
