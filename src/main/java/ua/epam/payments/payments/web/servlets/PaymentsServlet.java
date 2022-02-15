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
import java.util.List;

@WebServlet(name = "PaymentsServlet", value = Path.PAYMENT_PATH)
public class PaymentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // UserDao userDao = new UserDaoImpl();
        AccountDao accountDao = new AccountDaoImpl();

        User user = (User) req.getSession().getAttribute("user");

        List<Account> accounts = accountDao.getAccountsByUser(user);

        req.setAttribute("accounts", accounts);

        req.getRequestDispatcher(Path.PAYMENT_JSP).forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountDao accountDao = new AccountDaoImpl();

        int accountSenderNumber = Integer.parseInt(req.getParameter("accountSenderId"));
        int accountDestinationNumber = Integer.parseInt(req.getParameter("accountDestinationId"));
        int money= Integer.parseInt(req.getParameter("money"));

        Account accountFrom = accountDao.getAccountById(accountSenderNumber);
        Account accountTo = accountDao.getAccountById(accountDestinationNumber);

        if (accountFrom.getMoney() < money){
            System.out.println("out of money");
            resp.sendRedirect(Path.PAYMENT_PATH);;
            return;
        }

        System.out.println(accountDao.transferMoneyFromAccToAcc(accountFrom.getId(), accountTo.getId(), 20));



        resp.sendRedirect(Path.PROFILE_PATH);
    }
}
