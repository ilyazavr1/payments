package ua.epam.payments.payments.web.servlets;

import org.apache.commons.codec.DecoderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.services.CardService;
import ua.epam.payments.payments.model.util.PasswordEncryption;
import ua.epam.payments.payments.model.util.validation.UserValidator;
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
    private static final Logger logger = LogManager.getLogger(CardBlock.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("CardBlock started");
        req.setAttribute("cardId", req.getSession().getAttribute("cardId"));
        req.getRequestDispatcher(Path.CARD_BLOCK_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("CardBlock started");

        String cardIdToBlock = req.getParameter("cardId");
        req.setAttribute("cardId", cardIdToBlock);
        req.getSession().setAttribute("cardId", req.getParameter("cardId"));

        if (req.getParameter("password") == null) {
            resp.sendRedirect(Path.CARD_BLOCK_PATH);
            return;
        }

        PasswordEncryption passwordEncryption = new PasswordEncryption();

        UserValidator userService = new UserValidator();
        CardService cardService = new CardService(new CardDaoImpl());
        User user = (User) req.getSession().getAttribute("user");
        String password = req.getParameter("password").trim();

        if (!userService.validatePassword(password)) {
            req.setAttribute(Constants.INVALID_PASSWORD, Constants.INVALID_PASSWORD);
            req.getRequestDispatcher(Path.CARD_BLOCK_JSP).forward(req, resp);
        }


        if (!passwordEncryption.isPasswordCorrect(password, user.getPassword())) {
            req.setAttribute(Constants.WRONG_PASSWORD, Constants.WRONG_PASSWORD);
            req.getRequestDispatcher(Path.CARD_BLOCK_JSP).forward(req, resp);
            return;
        }

        if (cardService.blockCardById(Long.parseLong(cardIdToBlock)))
            logger.info("Card with id:\"{}\" is blocked", cardIdToBlock);

        resp.sendRedirect(Path.CARDS_PATH);
    }
}
