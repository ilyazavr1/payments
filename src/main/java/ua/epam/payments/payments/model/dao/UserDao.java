package ua.epam.payments.payments.model.dao;


import ua.epam.payments.payments.model.entity.User;

import java.util.List;


public interface UserDao {

    boolean createUser(User user);

    User getUserById(long id);

    User getUserByEmail(String email);

    String getUserRoleByUser(User user);

    List<User> getAllUsers();

    boolean blockUserById(long id);

    boolean unblockUserById(long id);


}
