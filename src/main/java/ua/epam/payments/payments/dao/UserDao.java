package ua.epam.payments.payments.dao;


import ua.epam.payments.payments.model.entity.User;

import java.util.List;


public interface UserDao {

    boolean createUser(User user);

    User getUserById(long id);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    boolean blockUserById(long id);

    boolean unblockUserById(long id);


}
