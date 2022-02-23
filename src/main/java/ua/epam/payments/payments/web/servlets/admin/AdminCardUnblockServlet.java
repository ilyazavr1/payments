package ua.epam.payments.payments.web.servlets.admin;


import ua.epam.payments.payments.dao.CardDao;
import ua.epam.payments.payments.dao.impl.CardDaoImpl;
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
        long cardId = Long.parseLong(req.getParameter("cardId"));


        System.out.println(cardId);

        if (cardDao.unblockCardById(cardId)) {
            cardDao.deleteCardRequestByCardId(cardId);

        }


        resp.sendRedirect(Path.ADMIN_UNBLOCK_USERS_CARDS_REQUESTS_PATH);

    }
}
