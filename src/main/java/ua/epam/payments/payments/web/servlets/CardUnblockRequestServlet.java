package ua.epam.payments.payments.web.servlets;


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

@WebServlet(name = "CardUnblockRequestServlet", value = Path.CARD_UNBLOCK_REQUEST_PATH)
public class CardUnblockRequestServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(CardUnblockRequestServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("CardUnblockRequestServlet started");
        UserService userService = new UserService(new UserDaoImpl());
        CardService cardService = new CardService(new CardDaoImpl());

        Card card = cardService.getCardById(Long.parseLong(req.getParameter("id")));
        User user = userService.getUserById(card.getUserId());

        cardService.makeRequestToUnblockCard(card, user);

        resp.sendRedirect(Path.CARDS_PATH);
    }
}
