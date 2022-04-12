package ua.epam.payments.payments.model.services;

import org.apache.commons.codec.DecoderException;
import ua.epam.payments.payments.model.dao.UserDao;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.exception.AuthenticationException;
import ua.epam.payments.payments.model.exception.RegisteredEmailException;
import ua.epam.payments.payments.model.util.PasswordEncryption;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public class UserService {
    private final UserDao userDao;


    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public String getUserRoleByUser(User user) {
        return userDao.getUserRoleByUser(user);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public boolean blockUserById(long id) {
        return userDao.blockUserById(id);
    }

    public boolean unblockUserById(long id) {
        return userDao.unblockUserById(id);
    }

    public boolean registerUser(String firstName, String lastName, String surname, String email, String password) throws RegisteredEmailException {
        if (userDao.getUserByEmail(email) != null) throw new RegisteredEmailException();
        PasswordEncryption passwordEncryption = new PasswordEncryption();
        User user = null;

        try {
            user = new User.Builder()
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withSurname(surname)
                    .withEmail(email)
                    .withPassword(passwordEncryption.encrypt(password))
                    .build();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            //TODO in registration exception
            e.printStackTrace();
        }

        return userDao.createUser(user);
    }

    public User authenticateUser(String email, String password) throws AuthenticationException {
        User user = userDao.getUserByEmail(email);
        PasswordEncryption passwordEncryption = new PasswordEncryption();

        try {
            if (user == null || !passwordEncryption.isPasswordCorrect(password, user.getPassword())) throw new AuthenticationException();
        } catch (DecoderException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }


        return user;
    }
}


















