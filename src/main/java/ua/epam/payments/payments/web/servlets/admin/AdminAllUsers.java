package ua.epam.payments.payments.web.servlets.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.UserDao;
import ua.epam.payments.payments.model.dao.impl.UserDaoImpl;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.services.UserService;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "AdminAllUsers", value = Path.ADMIN_ALL_USERS_PATH)
public class AdminAllUsers extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AdminAllCardUnblockRequestServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("AdminAllUsers started");


        UserService userService = new UserService(new UserDaoImpl());

        List<User> userList = userService.getAllUsers();

        req.setAttribute("usersList", userList);
        req.getRequestDispatcher(Path.ADMIN_ALL_USERS_JSP).forward(req, resp);
    }
}
