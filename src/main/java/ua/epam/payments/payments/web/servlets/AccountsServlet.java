package ua.epam.payments.payments.web.servlets;

import ua.epam.payments.payments.model.dao.AccountDao;
import ua.epam.payments.payments.model.dao.impl.AccountDaoImpl;
import ua.epam.payments.payments.model.entity.Account;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.services.AccountGeneration;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AccountsServlet", value = Path.ACCOUNTS_PATH)
public class AccountsServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher(Path.ACCOUNTS_JSP).forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountDao accountDao = new AccountDaoImpl();

        String accountNumber;
        Account newAccount;
        do {
            accountNumber = AccountGeneration.generateAccountNumber();
        } while (!accountDao.isExistAccount(accountNumber));

        User user = (User) req.getSession().getAttribute("user");

        if (accountDao.createAccount(new Account.Builder().withNumber(accountNumber).build())) {
            newAccount = accountDao.getAccountByNumber(accountNumber);
            accountDao.addAccountToUser(newAccount, user);
        }

        resp.sendRedirect(Path.ACCOUNTS_JSP);

        //TODO вывести аккаунты юзера 

    }
}
