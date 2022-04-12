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

@WebServlet(name = "AdminBlockUser", value = Path.ADMIN_BLOCK_USER_PATH)
public class AdminBlockUser extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(AdminAllCardUnblockRequestServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Path.ADMIN_BLOCK_USER_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("AdminAllUsers started");

        UserService userService = new UserService(new UserDaoImpl());
        String userId = req.getParameter("userId");

        if (userId == null || userId.isEmpty()){
            resp.sendRedirect(Path.ADMIN_ALL_USERS_PATH);
            return;
        }

        if (userService.blockUserById(Integer.parseInt(userId))){
            logger.info("User with Idd = {} is blocked", userId);
            resp.sendRedirect(Path.ADMIN_ALL_USERS_PATH);
            return;
        }

        req.setAttribute("userId", userId);
        doGet(req, resp);
    }
}
