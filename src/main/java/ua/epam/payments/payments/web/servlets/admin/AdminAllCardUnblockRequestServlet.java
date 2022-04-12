package ua.epam.payments.payments.web.servlets.admin;


import ua.epam.payments.payments.model.dao.CardDao;
import ua.epam.payments.payments.model.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.model.dto.CardsUnblockRequestDto;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AdminAllCardUnblockRequestServlet", value = Path.ADMIN_UNBLOCK_USERS_CARDS_REQUESTS_PATH)
public class AdminAllCardUnblockRequestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CardDao cardDao = new CardDaoImpl();
        List<CardsUnblockRequestDto> listCards = new ArrayList<>();

        listCards = cardDao.getCardRequests();

        req.setAttribute("listCards", listCards);

        req.getRequestDispatcher(Path.ADMIN_UNBLOCK_CARDS_REQUEST_JSP).forward(req,resp);
    }
}
