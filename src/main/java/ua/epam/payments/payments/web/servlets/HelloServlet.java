package ua.epam.payments.payments.web.servlets;

import ua.epam.payments.payments.model.dao.impl.UserDaoImpl;
import ua.epam.payments.payments.model.entity.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDaoImpl userDao = new UserDaoImpl();
        User user = userDao.getUserById(2);
        System.out.println(user.getId() +" \n" +
                user.getFirstName());


        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1></br>");
        out.println("<h1>" + user.getId() +" " + user.getFirstName() + "</h1>");
        out.println("</body></html>");



    }



    public void destroy() {
    }
}