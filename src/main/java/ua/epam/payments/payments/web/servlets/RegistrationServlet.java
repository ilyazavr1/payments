package ua.epam.payments.payments.web.servlets;

import ua.epam.payments.payments.model.dao.UserDao;
import ua.epam.payments.payments.model.dao.impl.UserDaoImpl;
import ua.epam.payments.payments.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "registrationServlet", value = "/registration")
public class RegistrationServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getSession().getAttribute("user") != null) {
            resp.sendRedirect("profile.jsp");
        } else {
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
        }
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") != null) {
            resp.sendRedirect("profile.jsp");
        }

        String firstName = req.getParameter("firstName").trim();
        String lastName = req.getParameter("lastName").trim();
        String surname = req.getParameter("surname").trim();
        String email = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();

        User user = new User.Builder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withSurname(surname)
                .withEmail(email)
                .withPassword(password)
                .build();

        UserDao userDao = new UserDaoImpl();

        userDao.createUser(user);


        HttpSession session = req.getSession();

        System.out.println(session.getAttribute("user"));
        session.setAttribute("user", user);



        resp.sendRedirect("profile.jsp");

    }
}
