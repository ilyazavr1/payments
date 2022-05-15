package ua.epam.payments.payments.web.servlets.admin;

import org.apache.commons.codec.DecoderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.CardDao;
import ua.epam.payments.payments.model.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.services.CardService;
import ua.epam.payments.payments.model.util.PasswordEncryption;
import ua.epam.payments.payments.model.util.validation.UserValidator;
import ua.epam.payments.payments.web.Constants;
import ua.epam.payments.payments.web.Path;
import ua.epam.payments.payments.web.servlets.CardBlock;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(name = "AdminBockCardServlet", value = Path.ADMIN_CARD_BLOCK_PATH)
public class AdminBockCardServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AdminBockCardServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("cardId", req.getSession().getAttribute("cardId"));

        req.getRequestDispatcher(Path.ADMIN_BLOCK_USER_CARD_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("AdminBockCardServlet started");
        String cardIdToBlock = req.getParameter("cardId");
        req.setAttribute("cardId", cardIdToBlock);
        req.getSession().setAttribute("cardId", req.getParameter("cardId"));

        if (req.getParameter("password") == null) {
            resp.sendRedirect(Path.ADMIN_CARD_BLOCK_PATH);
            return;
        }

        CardService cardService = new CardService(new CardDaoImpl());

        UserValidator userValidator = new UserValidator();

        User user = (User) req.getSession().getAttribute("user");
        String password = req.getParameter("password").trim();
        PasswordEncryption passwordEncryption = new PasswordEncryption();

        if (password.isEmpty() || !userValidator.validatePassword(password)) {
            logger.info("Password is not valid [{}]",password);
            req.setAttribute(Constants.INVALID_PASSWORD, Constants.INVALID_PASSWORD);
            req.getRequestDispatcher(Path.ADMIN_BLOCK_USER_CARD_JSP).forward(req, resp);
        }


        if (!passwordEncryption.isPasswordCorrect(password, user.getPassword())) {
            logger.info("Password is not correct [{}]",password);
            req.setAttribute(Constants.WRONG_PASSWORD, Constants.WRONG_PASSWORD);
            req.getRequestDispatcher(Path.ADMIN_BLOCK_USER_CARD_JSP).forward(req, resp);
        }

        cardService.blockCardById(Long.parseLong(cardIdToBlock));


        resp.sendRedirect(Path.ADMIN_USER_CARDS_PATH);
    }

}
