package ua.epam.payments.payments.dao;


import ua.epam.payments.payments.model.entity.User;



public interface UserDao {

    User getUserById(long id);

    User getUserByEmail(String email);

    boolean createUser(User user);


}
