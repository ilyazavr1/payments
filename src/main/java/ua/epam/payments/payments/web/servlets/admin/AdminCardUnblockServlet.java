package ua.epam.payments.payments.web.servlets.admin;


import ua.epam.payments.payments.model.dao.CardDao;
import ua.epam.payments.payments.model.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminCardUnblockServlet", value = Path.ADMIN_UNBLOCK_CARD_PATH)
public class AdminCardUnblockServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CardDao cardDao = new CardDaoImpl();
        String pathTrace = req.getHeader("referer");
        long cardId = Long.parseLong(req.getParameter("cardId"));
        Card card = cardDao.getCardById(cardId);

        System.out.println(cardId);

        if (!card.isUnderConsideration()) {
            cardDao.unblockCardById(cardId);
        } else {
            cardDao.unblockCardById(cardId);
            cardDao.deleteCardRequestByCardId(cardId);

        }

        if (pathTrace.contains(Path.ADMIN_USER_CARDS_PATH)) {
            resp.sendRedirect(pathTrace);
            return;
        }

        resp.sendRedirect(Path.ADMIN_UNBLOCK_USERS_CARDS_REQUESTS_PATH);

    }
}
/*
  String pathTrace = req.getHeader("referer");
System.out.println(pathTrace    );
        if (pathTrace.contains(Path.ADMIN_USER_CARDS_PATH)){
        resp.sendRedirect(pathTrace);
        return;
        }*/
