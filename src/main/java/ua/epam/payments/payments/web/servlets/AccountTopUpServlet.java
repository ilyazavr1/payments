package ua.epam.payments.payments.web.servlets;


import ua.epam.payments.payments.model.dao.AccountDao;
import ua.epam.payments.payments.model.dao.impl.AccountDaoImpl;
import ua.epam.payments.payments.model.entity.Account;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AccountTopUpServlet", value = Path.ACCOUNT_TOP_UP_PATH)
public class AccountTopUpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountDao accountDao = new AccountDaoImpl();

        long accountId = Long.parseLong(req.getParameter("id"));

        Account account = accountDao.getAccountById(accountId);
        req.setAttribute("account", account);
        Account account1 = (Account) req.getAttribute("accountToTopUp");

        req.getRequestDispatcher(Path.ACCOUNT_TOP_UP_JSP).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String money = req.getParameter("money");
        req.getAttribute("user");
        AccountDao accountDao = new AccountDaoImpl();
        req.getAttribute("accountToTopUp");
        // accountDao.
//TODO top up account 
        resp.sendRedirect(Path.ACCOUNTS_PATH);
    }
}
