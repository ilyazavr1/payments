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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AccountsServlet", value = Path.ACCOUNTS_PATH)
public class AccountsServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") != null) {
            AccountDao accountDao = new AccountDaoImpl();
            User user = (User) req.getSession().getAttribute("user");
            List<Account> accountList = accountDao.getAccountsByUser(user);
            System.out.println(accountList);
            req.setAttribute("accounts", accountDao.getAccountsByUser(user));

            req.getRequestDispatcher(Path.ACCOUNTS_JSP).forward(req,resp);
        } else req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("1");
        AccountDao accountDao = new AccountDaoImpl();

        String accountNumber;
        Account newAccount;
        List<Account> accountList;
        User user;

        //add account to db
      /*  do {
        } while (!accountDao.isExistAccount(accountNumber));*/
        accountNumber = AccountGeneration.generateAccountNumber();

        user = (User) req.getSession().getAttribute("user");
        System.out.println("2");
        //add account to user in db
        if (accountDao.createAccount(new Account.Builder().withNumber(accountNumber).build())) {
            newAccount = accountDao.getAccountByNumber(accountNumber);
            accountDao.addAccountToUser(newAccount, user);
        } else System.out.println("accounts do ont added to db");

        //add accounts to session
        accountList = accountDao.getAccountsByUser(user);
        req.setAttribute("accounts", accountList);


        //TODO вывести аккаунты юзера

        resp.sendRedirect(Path.ACCOUNTS_PATH);

    }
}
