package ua.epam.payments.payments.web.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.dao.UserDao;
import ua.epam.payments.payments.dao.impl.UserDaoImpl;
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

@WebServlet(name = "RegistrationServlet", value = Path.REGISTRATION_PATH)
public class RegistrationServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(RegistrationServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getSession().getAttribute("user") != null) {
            resp.sendRedirect(Path.CARDS_PATH);
        } else req.getRequestDispatcher(Path.REGISTRATION_JSP).forward(req, resp);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getSession().getAttribute("user") != null) {
            resp.sendRedirect(Path.CARDS_PATH);
            return;
        }

        UserService userService = new UserService();

        String firstName = req.getParameter("firstName").trim();
        String lastName = req.getParameter("lastName").trim();
        String surname = req.getParameter("surname").trim();
        String email = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();


        if (firstName.isEmpty() || !userService.validateName(firstName)) {
            req.setAttribute(Constants.INVALID_FIRST_NAME, Constants.INVALID_FIRST_NAME);
            req.getRequestDispatcher(Path.REGISTRATION_JSP).forward(req, resp);
        }

        if (lastName.isEmpty() || !userService.validateName(lastName)) {
            req.setAttribute(Constants.INVALID_LAST_NAME, Constants.INVALID_LAST_NAME);
            req.getRequestDispatcher(Path.REGISTRATION_JSP).forward(req, resp);
        }

        if (surname.isEmpty() || !userService.validateName(surname)) {
            req.setAttribute(Constants.INVALID_SURNAME, Constants.INVALID_SURNAME);
            req.getRequestDispatcher(Path.REGISTRATION_JSP).forward(req, resp);
        }

        if (email.isEmpty() || !userService.validateEmail(email)) {
            req.setAttribute(Constants.INVALID_EMAIL, Constants.INVALID_EMAIL);
            req.getRequestDispatcher(Path.REGISTRATION_JSP).forward(req, resp);
        }
        if (password.isEmpty() || !userService.validatePassword(password)) {
            req.setAttribute(Constants.INVALID_PASSWORD, Constants.INVALID_PASSWORD);
            req.getRequestDispatcher(Path.REGISTRATION_JSP).forward(req, resp);
        }
        UserDao userDao = new UserDaoImpl();

        if (userDao.getUserByEmail(email) != null) {
            req.setAttribute(Constants.EMAIL_EXISTS, Constants.EMAIL_EXISTS);
            req.getRequestDispatcher(Path.REGISTRATION_JSP).forward(req, resp);
            return;
        }

        User user = null;
        PasswordEncryption passwordEncryption = new PasswordEncryption();
        try {
            user = new User.Builder()
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withSurname(surname)
                    .withEmail(email)
                    .withPassword(passwordEncryption.encrypt(password))
                    .build();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {

            //TODO in registration exception
            e.printStackTrace();
        }


        userDao.createUser(user);

        user = userDao.getUserByEmail(email);


        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        session.setAttribute("userRole", userDao.getUserRoleByRoleId(user));

        resp.sendRedirect(Path.PROFILE_PATH);
    }
}
