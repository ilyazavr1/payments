package ua.epam.payments.payments.web.servlets;

import org.apache.commons.codec.DecoderException;
import ua.epam.payments.payments.dao.AccountDao;
import ua.epam.payments.payments.dao.UserDao;
import ua.epam.payments.payments.dao.impl.AccountDaoImpl;
import ua.epam.payments.payments.dao.impl.UserDaoImpl;
import ua.epam.payments.payments.model.entity.Account;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.services.PasswordEncryption;
import ua.epam.payments.payments.model.services.validation.UserValidation;
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
import java.util.List;

@WebServlet(name = "LoginServlet", value = Path.LOGIN_PATH)
public class LoginServlet extends HttpServlet {

    //  vlad@gmail.com
    //  Qwerty12345
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") != null) {
            AccountDao accountDao = new AccountDaoImpl();
            List<Account> accountList;
            User user = (User) req.getSession().getAttribute("user");
            accountList = accountDao.getAccountsByUser(user);

            req.setAttribute("accounts", accountList);
            resp.sendRedirect(Path.PROFILE_JSP);
            // req.getRequestDispatcher(Path.PROFILE_JSP).forward(req,resp);
            return;
        } else req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") != null) {
            doGet(req, resp);
            return;
        }

        String email = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            // TODO error

            System.out.println("email and pass is null");
            req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
        }

        UserDao userDao = new UserDaoImpl();

        User user = userDao.getUserByEmail(email);
        if (user == null) {
            // TODO error
            System.out.println("user is null");
            req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
        }
        if (!UserValidation.validateEmail(email)) {
            System.out.println("email is not valid");
            req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
        }
        if (!UserValidation.validatePassword(password)) {
            System.out.println("password is not valid");
            req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
        }

        if (!email.equals(user.getEmail())) {
            System.out.println("email is not correct");
            req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
        }

        try {
            if (!PasswordEncryption.isPasswordCorrect(password, user.getPassword())) {
                System.out.println("password is not correct");
                req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
            }
        } catch (DecoderException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("password is not correct exception");
            req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
        }

        HttpSession session = req.getSession();
        session.setAttribute("user", user);


        doGet(req, resp);

    }
}
