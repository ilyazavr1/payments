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

@WebServlet(name = "CreateCardServlet", value = Path.CARD_CREATE_PATH)
public class CreateCardServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Path.CARD_CREATE_JSP).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CardDao cardDao = new CardDaoImpl();
        String cardName = req.getParameter("cardName");
        String cardNumber = CardGeneration.generateCardNumber();
        User user = (User) req.getSession().getAttribute("user");


//TODO validation
        Card newCard = new Card.Builder().withName(cardName).withNumber(cardNumber).build();
        cardDao.createCardWithUser(newCard, user);

        resp.sendRedirect(Path.CARDS_PATH);
    }
}
