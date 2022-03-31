package ua.epam.payments.payments.web.servlets.admin;


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
import java.util.List;

@WebServlet(name = "AdminUserCardsServlet", value = Path.ADMIN_USER_CARDS_PATH)
public class AdminUserCardsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDao userDao = new UserDaoImpl();
        CardDao cardDao = new CardDaoImpl();

        long userId = Long.parseLong(req.getParameter("userId"));

        User user = userDao.getUserById(userId);
        List<Card> cards = cardDao.getCardByUser(user);


        req.setAttribute("user", user);
        req.setAttribute("cards", cards);
        req.getRequestDispatcher(Path.ADMIN_USER_CARDS_JSP).forward(req,resp);

    }
}
