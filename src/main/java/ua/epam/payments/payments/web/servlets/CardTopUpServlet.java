package ua.epam.payments.payments.web.servlets;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.CardDao;
import ua.epam.payments.payments.model.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.services.CardService;
import ua.epam.payments.payments.web.Constants;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CardTopUpServlet", value = Path.CARD_TOP_UP_PATH)
public class CardTopUpServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(CardTopUpServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("CardTopUpServlet started");

        CardService cardService = new CardService(new CardDaoImpl());


        String str = String.valueOf(req.getSession().getAttribute("cardId"));
        Card card = cardService.getCardById(Long.parseLong(str));


        if (card.isBlocked()) {
            resp.sendRedirect(Path.CARDS_PATH);
            return;
        }

        if (req.getSession().getAttribute(Constants.INVALID_MONEY_AMOUNT) != null) {
            req.getSession().removeAttribute(Constants.INVALID_MONEY_AMOUNT);
            req.setAttribute(Constants.INVALID_MONEY_AMOUNT,Constants.INVALID_MONEY_AMOUNT);
        }

        req.setAttribute("card", card);

        req.getRequestDispatcher(Path.CARD_TOP_UP_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("cardId") == null) {
            req.getSession().setAttribute("cardId", req.getSession().getAttribute("cardId"));
        } else req.getSession().setAttribute("cardId", req.getParameter("cardId"));


        resp.sendRedirect(Path.CARD_TOP_UP_PATH);
    }
}
