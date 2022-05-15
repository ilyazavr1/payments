package ua.epam.payments.payments.web.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.impl.RoleDaoImpl;
import ua.epam.payments.payments.model.dao.impl.UserDaoImpl;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.exception.RegisteredEmailException;
import ua.epam.payments.payments.model.services.UserService;
import ua.epam.payments.payments.model.util.validation.UserValidator;
import ua.epam.payments.payments.web.Constants;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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

        UserValidator userValidator = new UserValidator();

        String firstName = req.getParameter("firstName").trim();
        String lastName = req.getParameter("lastName").trim();
        String surname = req.getParameter("surname").trim();
        String email = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();


        if (!userValidator.isUserInputValid(firstName, lastName, surname, email, password)) {
            userValidator.getErrors().forEach(err -> req.setAttribute(err, err));
            req.getRequestDispatcher(Path.REGISTRATION_JSP).forward(req, resp);
            return;
        }


        UserService userService = new UserService(new UserDaoImpl());
        User user;
        try {
            if (userService.registerUser(firstName, lastName, surname, email, password, new RoleDaoImpl())) {
                user = userService.getUserByEmail(email);
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userRole", userService.getUserRoleByUserRoleId(user.getRolesId()));
                logger.info("User registered {}", email);
            }
        } catch (RegisteredEmailException e) {
            logger.info("User failed to register");
            req.setAttribute(Constants.EMAIL_EXISTS, Constants.EMAIL_EXISTS);
            req.getRequestDispatcher(Path.REGISTRATION_JSP).forward(req, resp);
            return;
        }


        resp.sendRedirect(Path.CARDS_PATH);
    }
}
