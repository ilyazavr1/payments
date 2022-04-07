package ua.epam.payments.payments.web.servlets;

import ua.epam.payments.payments.dao.CardDao;
import ua.epam.payments.payments.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;
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


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CardDao cardDao = new CardDaoImpl();
        String inoutMoney = req.getParameter("money").trim();
        int moneyTopUp;
        long cardId = Long.parseLong(req.getParameter("id"));

        User user = (User) req.getSession().getAttribute("user");
        Card card = cardDao.getCardById(cardId);


        if (card.isBlocked()) {
            resp.sendRedirect(Path.CARDS_PATH);
            return;
        }

        if (inoutMoney == null || inoutMoney.isEmpty() || inoutMoney.length() > 10) {
            req.getSession().setAttribute("cardId", cardId);
            req.getSession().setAttribute(Constants.INVALID_MONEY_AMOUNT, Constants.INVALID_MONEY_AMOUNT);
            req.setAttribute(Constants.INVALID_MONEY_AMOUNT, Constants.INVALID_MONEY_AMOUNT);
            req.getRequestDispatcher(Path.CARD_TOP_UP_PATH).forward(req, resp);
            return;
        }

        moneyTopUp = Integer.parseInt(req.getParameter("money"));

        if (moneyTopUp <= 0 || moneyTopUp > 10000) {
            req.getSession().setAttribute("cardId", cardId);
            req.getSession().setAttribute(Constants.INVALID_MONEY_AMOUNT, Constants.INVALID_MONEY_AMOUNT);
            req.setAttribute(Constants.INVALID_MONEY_AMOUNT, Constants.INVALID_MONEY_AMOUNT);
            req.getRequestDispatcher(Path.CARD_TOP_UP_PATH).forward(req, resp);
            return;
        }

        cardDao.updateCardWithMoney(card, moneyTopUp);

        req.getSession().removeAttribute("cardId");

        resp.sendRedirect(Path.CARDS_PATH);
    }
}
