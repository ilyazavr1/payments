package ua.epam.payments.payments.web.servlets.admin;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.CardDao;
import ua.epam.payments.payments.model.dao.UserDao;
import ua.epam.payments.payments.model.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.model.dao.impl.UserDaoImpl;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.services.CardService;
import ua.epam.payments.payments.model.services.UserService;
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
    private static final Logger logger = LogManager.getLogger(AdminCardUnblockServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameter("userId"));
        req.getSession().setAttribute("userId", req.getParameter("userId"));
        resp.sendRedirect(Path.ADMIN_USER_CARDS_PATH);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("AdminUnblockUser started");
        UserService userService = new UserService(new UserDaoImpl());
        CardService cardService = new CardService(new CardDaoImpl());

        long userId = Long.parseLong((String) req.getSession().getAttribute("userId"));

        User user = userService.getUserById(userId);
        List<Card> cards = cardService.getCardByUserId(user.getId());


        req.setAttribute("user", user);
        req.setAttribute("cards", cards);
        req.getRequestDispatcher(Path.ADMIN_USER_CARDS_JSP).forward(req, resp);

    }
}
