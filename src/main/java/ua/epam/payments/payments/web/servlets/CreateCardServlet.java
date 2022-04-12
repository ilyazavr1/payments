package ua.epam.payments.payments.web.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.CardDao;
import ua.epam.payments.payments.model.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.exception.CardExistException;
import ua.epam.payments.payments.model.services.CardService;
import ua.epam.payments.payments.model.util.CardGeneration;
import ua.epam.payments.payments.web.Constants;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateCardServlet", value = Path.CARD_CREATE_PATH)
public class CreateCardServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(CreateCardServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Path.CARD_CREATE_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("CreateCardServlet started");
        String cardName = req.getParameter("cardName").trim();

        if (cardName.length() > 30 || cardName.isEmpty()) {
            logger.info("Invalid card name: {}", cardName);
            req.setAttribute(Constants.INVALID_CARD_NAME, Constants.INVALID_CARD_NAME);
            req.getRequestDispatcher(Path.CARD_CREATE_JSP).forward(req, resp);
            return;
        }
        CardService cardService = new CardService(new CardDaoImpl());

        String cardNumber = CardGeneration.generateCardNumber();
        Card newCard = new Card.Builder().withName(cardName).withNumber(cardNumber).build();
        User user = (User) req.getSession().getAttribute("user");

        try {
            cardService.createCardWithUser(newCard, user);
            logger.info("Card with number {} created and added to user {}", newCard.getNumber(), user.getEmail());
        } catch (CardExistException e) {
            logger.info("Failed to create card");
            req.setAttribute(Constants.FAILED_TO_CREATE_CARD, Constants.FAILED_TO_CREATE_CARD);
            req.getRequestDispatcher(Path.CARD_CREATE_JSP).forward(req, resp);
            return;
        }


        resp.sendRedirect(Path.CARDS_PATH);
    }
}
