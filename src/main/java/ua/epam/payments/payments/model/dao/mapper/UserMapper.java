package ua.epam.payments.payments.model.dao.mapper;

import ua.epam.payments.payments.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    public User mapRSToUser(ResultSet rs) throws SQLException {
        return new User.Builder()
                .withId(rs.getInt(1))
                .withFirstName(rs.getString(2))
                .withLastName(rs.getString(3))
                .withSurname(rs.getString(4))
                .withEmail(rs.getString(5))
                .withPassword(rs.getString(6))
                .withBlocked(rs.getBoolean(7))
                .withRolesId(rs.getInt(8))
                .build();

    }


}


