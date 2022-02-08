package ua.epam.payments.payments.model.dao.impl;

import ua.epam.payments.payments.model.dao.UserDao;
import ua.epam.payments.payments.model.db.DBManager;
import ua.epam.payments.payments.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {


    @Override
    public User getUserById(long id) {
        User user = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmnt = con.prepareStatement("SELECT * FROM users WHERE id=?")) {
            stmnt.setLong(1, id);

            try (ResultSet rs = stmnt.executeQuery()) {
                while (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt(1));
                    user.setFirstName(rs.getString(2));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;
    }
}
