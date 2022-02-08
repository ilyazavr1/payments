package ua.epam.payments.payments.model.dao;

import ua.epam.payments.payments.model.entity.User;

public interface UserDao {

    public User getUserById(long id);

    public boolean createUser(User user);

}
