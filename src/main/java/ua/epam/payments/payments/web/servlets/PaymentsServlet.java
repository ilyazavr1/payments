package ua.epam.payments.payments.web.servlets;

import ua.epam.payments.payments.dao.PaymentsDao;
import ua.epam.payments.payments.dao.impl.PaymentsDaoImpl;
import ua.epam.payments.payments.model.dto.FullPaymentDto;
import ua.epam.payments.payments.model.entity.Payment;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "PaymentsServlet", value = Path.PAYMENTS_PATH)
public class PaymentsServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PaymentsDao paymentsDao = new PaymentsDaoImpl();

        User user = (User) req.getSession().getAttribute("user");

        List<FullPaymentDto> paymentList = paymentsDao.getFullPaymentsByUser(user);
        req.setAttribute("payments", paymentList);

        req.getRequestDispatcher(Path.PAYMENTS_JSP).forward(req, resp);

    }
}
