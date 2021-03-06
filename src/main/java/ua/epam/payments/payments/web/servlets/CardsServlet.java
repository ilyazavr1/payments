package ua.epam.payments.payments.web.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.services.CardService;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CardsServlet", value = Path.CARDS_PATH)
public class CardsServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(CardsServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("CardsServlet started");
        CardService cardService = new CardService(new CardDaoImpl());

        User user = (User) req.getSession().getAttribute("user");

        int limit;
        int offset;

        String records = req.getParameter("records");
        String page = req.getParameter("page");
        String sortingType = req.getParameter("sortingType");
        String sortingOrder = req.getParameter("sortingOrder");

        if (records == null || records.isEmpty()) {
            req.setAttribute("records", 9);
        } else req.setAttribute("records", records);

        if (page == null || page.isEmpty()) {
            req.setAttribute("page", 1);
        } else req.setAttribute("page", page);

        if (sortingType == null || sortingType.isEmpty()) {
            sortingType = "name";
        } else req.setAttribute("sortingType", sortingType);


        if (sortingOrder == null || sortingOrder.isEmpty()) {
            sortingOrder = "asc";
        } else req.setAttribute("sortingOrder", sortingOrder);


        if (records != null && !records.isEmpty()) {
            limit = Integer.parseInt(req.getParameter("records"));
        } else limit = 9;
        if (page != null && !page.isEmpty()) {
            offset = Integer.parseInt(req.getParameter("page"));
        } else offset = 1;


        List<Card> cards = cardService.sort(user.getId(), sortingType, sortingOrder, limit, (limit * (offset - 1)));





        req.setAttribute("cards", cards);
        req.setAttribute("loopPagination", (int) Math.ceil((double) cardService.countCardsByUser(user) / limit));

        req.getRequestDispatcher(Path.CARDS_JSP).forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(Path.CARDS_PATH + "?page" + req.getParameter("page") + "&records=" + req.getParameter("re"));
    }
}


















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


        //TODO ?????????????? ???????????????? ??????????*/

 /*int loopPagination = 2;
            System.out.println(cardsCount);
            System.out.println(limit);
            System.out.println(loopPagination+1);*/
//TODO ploho sdelano dobavlenie knopok
//System.out.println(offset);
//  System.out.println(limit);
 /*   if (req.getParameter("page") != null) {
            req.setAttribute("page", req.getParameter("page"));
        } else req.setAttribute("page", 1);
      */