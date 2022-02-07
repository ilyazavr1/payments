package ua.epam.payments.payments.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        System.out.println("asdasdasdasdasdasdasdasdasdas");
        System.out.println(s.getId());
        System.out.println(s.isNew());


        req.getRequestDispatcher("test.jsp").forward(req, resp);
    }
}
