package ua.epam.payments.payments.model.services.mapper;

import ua.epam.payments.payments.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    public User mapRSToUser(ResultSet rs) throws SQLException {
        User user = null;
        while (rs.next()) {
            user = new User.Builder()
                    .withId(rs.getInt(1))
                    .withFirstName(rs.getString(2))
                    .withLastName(rs.getString(3))
                    .withSurname(rs.getString(4))
                    .withEmail(rs.getString(5))
                    .withPassword(rs.getString(6))
                    .withBlocked(rs.getBoolean(7))
                    .withRolesId(rs.getLong(8))
                    .build();

        }

        return user;
    }

}
