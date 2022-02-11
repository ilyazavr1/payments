package ua.epam.payments.payments.web.servlets;

import org.apache.commons.codec.DecoderException;
import ua.epam.payments.payments.model.dao.UserDao;
import ua.epam.payments.payments.model.dao.impl.UserDaoImpl;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.services.PasswordEncryption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(name = "registrationServlet", value = "/registration")
public class RegistrationServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getSession().getAttribute("user") != null) {
            req.getRequestDispatcher("profile.jsp").forward(req, resp);;
        } else {
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getSession().getAttribute("user") != null) {
            User test = (User) req.getSession().getAttribute("user");
            System.out.println(test.getFirstName());
            System.out.println(1);
            resp.sendRedirect("profile.jsp");
        }

        String firstName = req.getParameter("firstName").trim();
        String lastName = req.getParameter("lastName").trim();
        String surname = req.getParameter("surname").trim();
        String email = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();


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

        try {
            System.out.println(PasswordEncryption.isPasswordCorrect(password, user.getPassword()));
        } catch (DecoderException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        req.getSession().setAttribute("user", user);


        System.out.println(2);

        this.doGet(req, resp);
    }
}
