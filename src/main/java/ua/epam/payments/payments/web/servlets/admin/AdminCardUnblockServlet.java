package ua.epam.payments.payments.web.servlets.admin;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.CardDao;
import ua.epam.payments.payments.model.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.services.CardService;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminCardUnblockServlet", value = Path.ADMIN_UNBLOCK_CARD_PATH)
public class AdminCardUnblockServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AdminCardUnblockServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("AdminCardUnblockServlet started");

        CardService cardService = new CardService(new CardDaoImpl());
        String pathTrace = req.getHeader("referer");
        long cardId = Long.parseLong(req.getParameter("cardId"));

        if (cardService.unblockCardById(cardId)) logger.info("Card with Id = {} is unblocked", cardId);
        else logger.info("Card with Id = {} was not unblocked", cardId);

        if (pathTrace.contains(Path.ADMIN_USER_CARDS_PATH)) {
            resp.sendRedirect(pathTrace);
            return;
        }

        resp.sendRedirect(Path.ADMIN_UNBLOCK_USERS_CARDS_REQUESTS_PATH);

    }
}
