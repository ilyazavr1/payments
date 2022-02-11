package ua.epam.payments.payments.web.servlets;

import org.apache.commons.codec.DecoderException;
import ua.epam.payments.payments.model.dao.UserDao;
import ua.epam.payments.payments.model.dao.impl.UserDaoImpl;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.services.PasswordEncryption;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(name = "RegistrationServlet", value = Path.REGISTRATION_PATH)
public class RegistrationServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getSession().getAttribute("user") != null) {
            resp.sendRedirect(Path.PROFILE_PATH);
        }else req.getRequestDispatcher(Path.REGISTRATION_JSP).forward(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getSession().getAttribute("user") != null) {
            resp.sendRedirect(Path.PROFILE_PATH);
           return;
        }

        String firstName = req.getParameter("firstName").trim();
        String lastName = req.getParameter("lastName").trim();
        String surname = req.getParameter("surname").trim();
        String email = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();

        System.out.println("regis" + password);

        User user = null;
        try {
            user = new User.Builder()
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withSurname(surname)
                    .withEmail(email)
                    .withPassword(PasswordEncryption.encrypt(password))
                    .build();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        UserDao userDao = new UserDaoImpl();
        userDao.createUser(user);

        user = userDao.getUserByEmail(email);


        HttpSession session = req.getSession();
        session.setAttribute("user", user);

        resp.sendRedirect(Path.PROFILE_PATH);
    }
}
