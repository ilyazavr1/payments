package ua.epam.payments.payments.web.servlets.admin;

import org.apache.commons.codec.DecoderException;
import ua.epam.payments.payments.dao.CardDao;
import ua.epam.payments.payments.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.util.PasswordEncryption;
import ua.epam.payments.payments.util.UserService;
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

@WebServlet(name = "AdminBockCardServlet", value = Path.ADMIN_CARD_BLOCK_PATH)
public class AdminBockCardServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("cardId", req.getSession().getAttribute("cardId"));

        req.getRequestDispatcher(Path.ADMIN_BLOCK_USER_CARD_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cardIdToBlock = req.getParameter("cardId");
        req.setAttribute("cardId", cardIdToBlock);
        req.getSession().setAttribute("cardId", req.getParameter("cardId"));

        if (req.getParameter("password") == null){
            resp.sendRedirect(Path.ADMIN_CARD_BLOCK_PATH);
            return;
        }
        CardDao cardDao = new CardDaoImpl();



        UserService userService = new UserService();
        User user = (User) req.getSession().getAttribute("user");
        String password = req.getParameter("password").trim();
        PasswordEncryption passwordEncryption = new PasswordEncryption();

        if ( password.isEmpty() || !userService.validatePassword(password)) {
            req.setAttribute(Constants.INVALID_PASSWORD, Constants.INVALID_PASSWORD);
            req.getRequestDispatcher(Path.ADMIN_BLOCK_USER_CARD_JSP).forward(req, resp);
        }

        try {
            if (!passwordEncryption.isPasswordCorrect(password, user.getPassword())) {
                req.setAttribute(Constants.WRONG_PASSWORD, Constants.WRONG_PASSWORD);
                req.getRequestDispatcher(Path.ADMIN_BLOCK_USER_CARD_JSP).forward(req, resp);
            }
        } catch (DecoderException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            req.setAttribute(Constants.WRONG_PASSWORD, Constants.WRONG_PASSWORD);
            req.getRequestDispatcher(Path.ADMIN_BLOCK_USER_CARD_JSP).forward(req, resp);
        }

        cardDao.blockCardById(Long.parseLong(cardIdToBlock));


        resp.sendRedirect(Path.ADMIN_ALL_USERS_PATH);
    }

}
