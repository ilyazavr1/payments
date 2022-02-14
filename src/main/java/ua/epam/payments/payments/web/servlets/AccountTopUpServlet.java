package ua.epam.payments.payments.web.servlets;


import ua.epam.payments.payments.dao.AccountDao;
import ua.epam.payments.payments.dao.impl.AccountDaoImpl;
import ua.epam.payments.payments.model.entity.Account;
import ua.epam.payments.payments.model.entity.User;
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


        req.getRequestDispatcher(Path.ACCOUNT_TOP_UP_JSP).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountDao accountDao = new AccountDaoImpl();
        int money = Integer.parseInt(req.getParameter("money"));
        long accountId = Long.parseLong(req.getParameter("id"));

        User user = (User) req.getSession().getAttribute("user");
        Account account = accountDao.getAccountById(accountId);

        if (money < 0 || user == null || account == null){
            System.out.println("user or money or account is absent");
            resp.sendRedirect(Path.ACCOUNTS_PATH);
        }

        accountDao.updateAccountWithMoney(account, money);


        //TODO top up account
        resp.sendRedirect(Path.ACCOUNTS_PATH);
    }
}
