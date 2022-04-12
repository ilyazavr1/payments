package ua.epam.payments.payments.web.servlets.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.UserDao;
import ua.epam.payments.payments.model.dao.impl.UserDaoImpl;
import ua.epam.payments.payments.model.services.UserService;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminUnblockUser", value = Path.ADMIN_UNBLOCK_USER_PATH)
public class AdminUnblockUser extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AdminCardUnblockServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Path.ADMIN_BLOCK_USER_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("AdminUnblockUser started");

        UserService userService = new UserService(new UserDaoImpl());
        String userId = req.getParameter("userId");

        if ( userId.isEmpty()){
            resp.sendRedirect(Path.ADMIN_ALL_USERS_PATH);
            return;
        }

        if (userService.unblockUserById(Integer.parseInt(userId))){
            logger.info("User with Id = {} is unblocked", userId);
            resp.sendRedirect(Path.ADMIN_ALL_USERS_PATH);
            return;
        }
        logger.info("User with Id = {} was not unblocked", userId);

        req.setAttribute("userId", userId);
        doGet(req, resp);
    }
}
