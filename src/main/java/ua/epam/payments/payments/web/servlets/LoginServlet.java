package ua.epam.payments.payments.web.servlets;

import org.apache.commons.codec.DecoderException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.dao.UserDao;
import ua.epam.payments.payments.dao.impl.UserDaoImpl;
import ua.epam.payments.payments.model.entity.Role;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(name = "LoginServlet", value = Path.LOGIN_PATH)
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LoginServlet.class);


    //  vlad@gmail.com
    //  Qwerty12345
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getSession().getAttribute("user") != null) {
            resp.sendRedirect(Path.CARDS_PATH);

        } else req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("LoginServlet started");
        if (req.getSession().getAttribute("user") != null) {
            resp.sendRedirect(Path.PROFILE_PATH);
            return;
        }

        UserService userService = new UserService();
        UserDao userDao = new UserDaoImpl();
        String email = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();

        if (email.isEmpty() || !userService.validateEmail(email)) {
            req.setAttribute(Constants.INVALID_EMAIL, Constants.INVALID_EMAIL);
            req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
        }
        if (password.isEmpty() || !userService.validatePassword(password)) {
            req.setAttribute(Constants.INVALID_PASSWORD, Constants.INVALID_PASSWORD);
            req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
        }
        logger.info("User input validated");

        User user = userDao.getUserByEmail(email);
        PasswordEncryption passwordEncryption = new PasswordEncryption();

        if (user == null || !email.equals(user.getEmail())) {
            logger.info("User {} does not exist", email);
            req.setAttribute(Constants.WRONG_EMAIL, Constants.WRONG_EMAIL);
            req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
            return;
        }

        try {
            if ( !passwordEncryption.isPasswordCorrect(password, user.getPassword())) {
                logger.info("Wrong password for {} does not exist", email);
                req.setAttribute(Constants.WRONG_PASSWORD, Constants.WRONG_PASSWORD);
                req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
            }
        } catch (DecoderException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.warn("Ec");
            req.setAttribute(Constants.WRONG_PASSWORD, Constants.WRONG_PASSWORD);
            req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
        }

        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        session.setAttribute("userRole", userDao.getUserRoleByRoleId(user));

        logger.info("User {} is authenticated", user.getEmail());

        if (userDao.getUserRoleByRoleId(user).equals(Role.CLIENT.name())) {
            resp.sendRedirect(Path.CARDS_PATH);
        } else resp.sendRedirect(Path.ADMIN_ALL_USERS_PATH);
    }
}
