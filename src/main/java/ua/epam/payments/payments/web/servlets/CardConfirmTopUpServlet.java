package ua.epam.payments.payments.web.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.CardDao;
import ua.epam.payments.payments.model.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.exception.CardTopUpException;
import ua.epam.payments.payments.model.services.CardService;
import ua.epam.payments.payments.web.Constants;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "CardConfirmTopUpServlet", value = Path.CARD_CONFIRM_TOP_UP_PATH)
public class CardConfirmTopUpServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(CardConfirmTopUpServlet.class);



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("CardTopUpServlet started");
        CardService cardService = new CardService(new CardDaoImpl());
        String inputMoney = req.getParameter("money").trim();

        long cardId = Long.parseLong(req.getParameter("id"));


        Card card = cardService.getCardById(cardId);

        if (card.isBlocked()) {
            resp.sendRedirect(Path.CARDS_PATH);
            return;
        }

        try {
            cardService.topUpCard(card, inputMoney);
            logger.info("Card with id \"{}\" topped up", card.getId());
        } catch (CardTopUpException e) {
            req.getSession().setAttribute(Constants.INVALID_MONEY_AMOUNT, Constants.INVALID_MONEY_AMOUNT);
            req.setAttribute(Constants.INVALID_MONEY_AMOUNT, Constants.INVALID_MONEY_AMOUNT);
            req.getRequestDispatcher(Path.CARD_TOP_UP_PATH).forward(req, resp);
            return;
        }

        req.getSession().removeAttribute("cardId");

        resp.sendRedirect(Path.CARDS_PATH);
    }
}
