package ua.epam.payments.payments.model.dao.impl;

import ua.epam.payments.payments.model.dao.UserDao;
import ua.epam.payments.payments.model.db.DBManager;
import ua.epam.payments.payments.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    public static final String SQL_GET_USER_BY_ID = "SELECT * FROM users WHERE id=?";
    public static final String SQL_CREATE_USER = "INSERT INTO users values (default, ?, ?, ?, ?, ?, ?, ?)";

    public static final String USER_ID = "id";


    @Override
    public User getUserById(long id) {
        User user = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_USER_BY_ID)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
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

    //user data: 1 - first_name, 2 - last_name, 3 - surname, 4 - email, 5 - password, 6 - blocked, 7 - role_id
    @Override
    public boolean createUser(User user) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_CREATE_USER)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getSurname());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());
            stmt.setBoolean(6, false);
            stmt.setInt(7, 2);

            return stmt.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


}
