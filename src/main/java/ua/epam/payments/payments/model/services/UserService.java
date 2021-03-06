package ua.epam.payments.payments.model.services;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.RoleDao;
import ua.epam.payments.payments.model.dao.UserDao;
import ua.epam.payments.payments.model.entity.Role;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.exception.AuthenticationException;
import ua.epam.payments.payments.model.exception.RegisteredEmailException;
import ua.epam.payments.payments.model.exception.UserIsBlockedException;
import ua.epam.payments.payments.model.util.PasswordEncryption;

import java.util.List;

/**
 * Manages business logic related with User.
 *
 * @author Illia Smiian
 */
public class UserService {
    private final UserDao userDao;
    private static final Logger logger = LogManager.getLogger(UserService.class);

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public String getUserRoleByUserRoleId(long roleId) {
        return userDao.getUserRoleByUserRoleId(roleId);
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

    public boolean registerUser(String firstName, String lastName, String surname, String email, String password, RoleDao roleDao) throws RegisteredEmailException {

        if (userDao.getUserByEmail(email) != null) throw new RegisteredEmailException();
        PasswordEncryption passwordEncryption = new PasswordEncryption();

        return userDao.createUser(new User.Builder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withSurname(surname)
                .withEmail(email)
                .withPassword(passwordEncryption.encrypt(password))
                .withRolesId(roleDao.getRoleIdByEnum(Role.CLIENT))
                .build());
    }

    public User authenticateUser(String email, String password) throws AuthenticationException, UserIsBlockedException {
        User user = userDao.getUserByEmail(email);
        PasswordEncryption passwordEncryption = new PasswordEncryption();

        if (user == null || !passwordEncryption.isPasswordCorrect(password, user.getPassword()))
            throw new AuthenticationException();

        if (user.getBlocked()) throw new UserIsBlockedException();


        return user;
    }
}


















