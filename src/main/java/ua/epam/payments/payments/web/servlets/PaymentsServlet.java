package ua.epam.payments.payments.web.servlets;

import ua.epam.payments.payments.dao.PaymentsDao;
import ua.epam.payments.payments.dao.impl.PaymentsDaoImpl;
import ua.epam.payments.payments.model.dto.FullPaymentDto;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.util.sorting.PaymentService;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@WebServlet(name = "PaymentsServlet", value = Path.PAYMENTS_PATH)
public class PaymentsServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PaymentsDao paymentsDao = new PaymentsDaoImpl();
        User user = (User) req.getSession().getAttribute("user");
        PaymentService paymentService = new PaymentService();

        req.setAttribute("invalidPayment", req.getParameter("invalidPayment"));
        int limit;
        int offset;
        String sortingType = req.getParameter("sortingType");
        String sortingOrder = req.getParameter("sortingOrder");
        String records = req.getParameter("records");
        String page = req.getParameter("page");

        if (sortingType == null || sortingType.isEmpty()) {
            sortingType = "creation_timestamp";
        } else req.setAttribute("sortingType", sortingType);
        if (sortingOrder == null || sortingOrder.isEmpty()) {
            sortingOrder = "asc";
        } else req.setAttribute("sortingOrder", sortingOrder);
        if (records == null || records.isEmpty()) {
            req.setAttribute("records", 9);
        } else req.setAttribute("records", records);
        if (page == null || page.isEmpty()) {
            req.setAttribute("page", 1);
        } else req.setAttribute("page", page);

        if (records != null && !records.isEmpty()) {
            limit = Integer.parseInt(req.getParameter("records"));
        } else limit = 9;
        if (page != null && !page.isEmpty()) {
            offset = Integer.parseInt(req.getParameter("page"));
        } else offset = 1;





        List<FullPaymentDto> paymentList = paymentService.sort(paymentsDao, user, sortingType, sortingOrder, limit,(limit * (offset - 1)));


        req.setAttribute("payments", paymentList);

        req.getRequestDispatcher(Path.PAYMENTS_JSP).forward(req, resp);
    }
}
