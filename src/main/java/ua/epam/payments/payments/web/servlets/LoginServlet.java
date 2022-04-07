package ua.epam.payments.payments.web.servlets;

import org.apache.commons.codec.DecoderException;
import ua.epam.payments.payments.dao.UserDao;
import ua.epam.payments.payments.dao.impl.UserDaoImpl;
import ua.epam.payments.payments.model.entity.Role;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(name = "LoginServlet", value = Path.LOGIN_PATH)
public class LoginServlet extends HttpServlet {

    //  vlad@gmail.com
    //  Qwerty12345
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getSession().getAttribute("user"));
        if (req.getSession().getAttribute("user") != null) {
            req.getRequestDispatcher(Path.PROFILE_PATH).forward(req, resp);

        } else req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") != null) {
            resp.sendRedirect(Path.PROFILE_PATH);
            return;
        }

        String email = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();

        if (email == null || email.isEmpty() || !UserValidation.validateEmail(email)) {
            System.out.println(email);
            System.out.println(Constants.INVALID_EMAIL);
            req.setAttribute(Constants.INVALID_EMAIL, Constants.INVALID_EMAIL);
            req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
        }
        if (password == null || password.isEmpty() || !UserValidation.validatePassword(password)) {
            req.setAttribute(Constants.INVALID_PASSWORD, Constants.INVALID_PASSWORD);
            req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
        }

        UserDao userDao = new UserDaoImpl();

        User user = userDao.getUserByEmail(email);
        if (user == null || !email.equals(user.getEmail())) {
            req.setAttribute(Constants.WRONG_EMAIL, Constants.WRONG_EMAIL);
            req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
        }

        try {
            if (!PasswordEncryption.isPasswordCorrect(password, user.getPassword())) {
                req.setAttribute(Constants.WRONG_PASSWORD, Constants.WRONG_PASSWORD);
                req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
            }
        } catch (DecoderException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            req.setAttribute(Constants.WRONG_PASSWORD, Constants.WRONG_PASSWORD);
            req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
        }

        HttpSession session = req.getSession();
        session.setAttribute("user", user);

        if (userDao.getUserRoleByRoleId(user).equals(Role.CLIENT.name())) {
            resp.sendRedirect(Path.CARDS_PATH);
        } else  resp.sendRedirect(Path.ADMIN_ALL_USERS_PATH);
    }
}
