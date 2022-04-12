package ua.epam.payments.payments.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.UserDao;
import ua.epam.payments.payments.db.DBManager;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.mapper.UserMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    public static final String SQL_GET_USER_BY_ID = "SELECT * FROM \"user\" WHERE id=?";
    public static final String SQL_GET_USER_BY_EMAIL = "SELECT * FROM \"user\" WHERE email=?";
    public static final String SQL_GET_ALL_USERS = "SELECT * FROM \"user\" ORDER BY id";
    public static final String SQL_CREATE_USER = "INSERT INTO \"user\" values (default, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_BLOCK_USER_BY_ID = "UPDATE \"user\" SET blocked=true WHERE id =?";
    public static final String SQL_UNBLOCK_USER_BY_ID = "UPDATE \"user\" SET blocked=false WHERE id =?";
    public static final String SQL_GET_USER_ROLE_BY_ROLE_ID = "SELECT name FROM role WHERE id=?";

    public static final String USER_ID = "id";

    private final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    @Override
    public User getUserById(long id) {
        User user = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_USER_BY_ID)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                UserMapper userMapper = new UserMapper();
                while (rs.next()) {
                    user = userMapper.mapRSToUser(rs);
                }
            }

        } catch (SQLException throwables) {
            logger.error("{}, when trying to get User by Id = {}", throwables.getMessage(), id);
            throw new RuntimeException(throwables);
        }

        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_USER_BY_EMAIL)) {
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                UserMapper userMapper = new UserMapper();
                while (rs.next()) {
                    user = userMapper.mapRSToUser(rs);
                }
            }

        } catch (SQLException throwables) {
            logger.error("{}, when trying to get User by email = {}", throwables.getMessage(), email);
            throw new RuntimeException(throwables);
        }

        return user;
    }

    @Override
    public String getUserRoleByUserRoleId(long roleId) {
        String role = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_USER_ROLE_BY_ROLE_ID)) {
            stmt.setLong(1, roleId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                 role = rs.getString(1);
                }
            }

        } catch (SQLException throwables) {
            logger.error("{}, when trying to get UserRole by roleId = {}", throwables.getMessage(), roleId);
            throw new RuntimeException(throwables);
        }

        return role;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             Statement stmt = con.createStatement()) {


            try (ResultSet rs = stmt.executeQuery(SQL_GET_ALL_USERS)) {
                UserMapper userMapper = new UserMapper();
                while (rs.next()) {
                    userList.add(userMapper.mapRSToUser(rs));
                }
            }

        } catch (SQLException throwables) {
            logger.error("{}, when trying to get User list", throwables.getMessage());
            throw new RuntimeException(throwables);
        }

        return userList;
    }

    @Override
    public boolean blockUserById(long id) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_BLOCK_USER_BY_ID)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException throwables) {
            logger.error("{}, when trying to block User by Id = {}", throwables.getMessage(), id);
            throw new RuntimeException(throwables);
        }

    }

    @Override
    public boolean unblockUserById(long id) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_UNBLOCK_USER_BY_ID)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException throwables) {
            logger.error("{}, when trying to unblock User by Id = {}", throwables.getMessage(), id);
            throw new RuntimeException(throwables);
        }
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
            logger.error("{}, when trying to create User = {}", throwables.getMessage(), user);
            throw new RuntimeException(throwables);
        }

    }


}
