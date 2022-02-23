package ua.epam.payments.payments.web.servlets;

import org.apache.commons.codec.DecoderException;
import ua.epam.payments.payments.dao.CardDao;
import ua.epam.payments.payments.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.services.PasswordEncryption;
import ua.epam.payments.payments.services.validation.UserValidation;
import ua.epam.payments.payments.web.Constants;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


@WebServlet(name = "CardBlock", value = Path.CARD_BLOCK_PATH)
public class CardBlock extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathTrace = req.getHeader("referer");

        req.setAttribute("cardId", req.getParameter("id"));
        req.setAttribute("adminPathToRedirect", pathTrace);

        req.getRequestDispatcher(Path.CARD_BLOCK_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String adminPathToRedirect = req.getParameter("adminPathToRedirect");
        String cardIdToBlock = req.getParameter("cardId");
        req.setAttribute("cardId", cardIdToBlock);

        CardDao cardDao = new CardDaoImpl();

        User user = (User) req.getSession().getAttribute("user");
        String password = req.getParameter("password").trim();

        if (password == null || password.isEmpty() || !UserValidation.validatePassword(password)) {
            req.setAttribute(Constants.INVALID_PASSWORD, Constants.INVALID_PASSWORD);
            req.getRequestDispatcher(Path.CARD_BLOCK_JSP).forward(req, resp);
        }

        try {
            if (!PasswordEncryption.isPasswordCorrect(password, user.getPassword())) {
                req.setAttribute(Constants.WRONG_PASSWORD, Constants.WRONG_PASSWORD);
                req.getRequestDispatcher(Path.CARD_BLOCK_JSP).forward(req, resp);
            }
        } catch (DecoderException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            req.setAttribute(Constants.WRONG_PASSWORD, Constants.WRONG_PASSWORD);
            req.getRequestDispatcher(Path.CARD_BLOCK_JSP).forward(req, resp);
        }

        cardDao.blockCardById(Long.parseLong(cardIdToBlock));


        if (adminPathToRedirect.contains(Path.ADMIN_USER_CARDS_PATH)) {
            resp.sendRedirect(adminPathToRedirect);
            return;
        }

        resp.sendRedirect(Path.CARDS_PATH);
    }
}
