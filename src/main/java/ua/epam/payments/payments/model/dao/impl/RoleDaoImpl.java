package ua.epam.payments.payments.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.db.DBManager;
import ua.epam.payments.payments.model.dao.RoleDao;
import ua.epam.payments.payments.model.entity.Role;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDaoImpl implements RoleDao {
    public static final String SQL_GET_ID_BY_ROLE_ENUM = "SELECT * FROM role WHERE name = ?";

    private final Logger logger = LogManager.getLogger(RoleDaoImpl.class);

    @Override
    public int getRoleIdByEnum(Role role) {
        int id = 0;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_ID_BY_ROLE_ENUM)) {
            stmt.setString(1, role.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    id = rs.getInt(1);
                }
            }

        } catch (SQLException throwables) {
            logger.error("{}, when trying to get Role id by Role enum = {}", throwables.getMessage(), role.toString());
            throw new RuntimeException(throwables);
        }

        return id;
    }
}
