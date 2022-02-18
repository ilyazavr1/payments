package ua.epam.payments.payments.web.servlets.admin;

import ua.epam.payments.payments.dao.UserDao;
import ua.epam.payments.payments.dao.impl.UserDaoImpl;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminUnblockUser", value = Path.ADMIN_UNBLOCK_USER_PATH)
public class AdminUnblockUser extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Path.ADMIN_BLOCK_USER_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDao userDao = new UserDaoImpl();
        String userId = req.getParameter("userId");

        if (userId == null && userId.isEmpty()){
            resp.sendRedirect(Path.ADMIN_ALL_USERS_PATH);
            return;
        }

        if (userDao.unblockUserById(Integer.parseInt(userId))){
            resp.sendRedirect(Path.ADMIN_ALL_USERS_PATH);
            return;
        }

        req.setAttribute("userId", userId);
        doGet(req, resp);
    }
}
