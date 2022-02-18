package ua.epam.payments.payments.web.servlets;

import ua.epam.payments.payments.dao.CardDao;
import ua.epam.payments.payments.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.services.CardGeneration;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CardsServlet", value = Path.CARDS_PATH, initParams = {
        @WebInitParam(name = "records", value = "9"),
        @WebInitParam(name = "page", value = "1")
})
public class CardsServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String recordsOnPage = req.getParameter("recordsOnPage");
        String records = req.getParameter("records");
        System.out.println("[" + recordsOnPage + "]");
        System.out.println("[" + records + "]");

        if (recordsOnPage != null) {
            req.setAttribute("recordsOnPage", recordsOnPage);
          //  req.setAttribute("records", recordsOnPage);
        } else recordsOnPage = records;

        if (recordsOnPage == null) {
            System.out.println("---------");
            recordsOnPage = getInitParameter("records");
        }

//save recordsOnPage
        if (records != null) {
            req.setAttribute("recordsOnPage", records);
        }
        //req.setAttribute("records", 9);

        if (req.getParameter("page") != null) {
            req.setAttribute("page", req.getParameter("page"));
        } else req.setAttribute("page", 1);




        if (req.getSession().getAttribute("user") != null) {
            CardDao cardDao = new CardDaoImpl();
            User user = (User) req.getSession().getAttribute("user");

            System.out.println("r - " + recordsOnPage);
            System.out.println("p - " + req.getParameter("records"));

          // int limit = Integer.parseInt(recordsOnPage);
           int limit = Integer.parseInt(getInitParameter("records"));

            List<Card> cards = cardDao.getCardByUserLimit(user, limit, 0);

            req.setAttribute("cards", cards);
            //req.setAttribute("cards", cardDao.getCardByUser(user));

            req.getRequestDispatcher(Path.CARDS_JSP).forward(req, resp);
        } else req.getRequestDispatcher(Path.LOGIN_JSP).forward(req, resp);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

/*        CardDao cardDao = new CardDaoImpl();
        List<Card> cards;
        User user;

        user = (User) req.getSession().getAttribute("user");

        //add account to user in db
        if (user == null) {
            System.out.println("user is null:CardServlet");
            resp.sendRedirect(Path.CARD_PATH);
        }


        //add accounts to session
        cards = cardDao.getCardByUser(user);
        req.setAttribute("cards", cards);


        //TODO вывести аккаунты юзера*/

        resp.sendRedirect(Path.CARDS_PATH + "?page" + req.getParameter("page") + "&records=" + req.getParameter("re"));

    }
}
