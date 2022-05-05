package ua.epam.payments.payments.web.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.impl.UserDaoImpl;
import ua.epam.payments.payments.model.entity.Role;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.exception.AuthenticationException;
import ua.epam.payments.payments.model.exception.UserIsBlockedException;
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

@WebServlet(name = "LoginServlet", value = Path.LOGIN_PATH)
public class LoginServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(LoginServlet.class);



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("LoginServlet started");
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

        UserValidator userValidator = new UserValidator();

        String email = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();

        if (!userValidator.isUserInputValid(email, password)) {
            userValidator.getErrors().forEach(err -> req.setAttribute(err, err));
            req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
            return;
        }

        UserService userService = new UserService(new UserDaoImpl());
        User user;
        try {
            user = userService.authenticateUser(email, password);
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userRole", userService.getUserRoleByUserRoleId(user.getRolesId()));

            logger.info("User {} is authenticated", user.getEmail());
        } catch (AuthenticationException e) {
            logger.info("User {} does not exist", email);
            req.setAttribute(Constants.AUTHENTICATION_FAILED, Constants.AUTHENTICATION_FAILED);
            req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
            return;
        } catch (UserIsBlockedException e) {
            logger.info("User {} is blocked", email);
            req.setAttribute(Constants.USER_IS_BLOCKED, Constants.USER_IS_BLOCKED);
            req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);
            return;
        }


        if (userService.getUserRoleByUserRoleId(user.getRolesId()).equals(Role.CLIENT.name())) {
            resp.sendRedirect(Path.CARDS_PATH);
        } else resp.sendRedirect(Path.ADMIN_ALL_USERS_PATH);
    }
}
