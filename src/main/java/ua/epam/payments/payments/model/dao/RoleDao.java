package ua.epam.payments.payments.model.dao;

import ua.epam.payments.payments.model.entity.Role;

public interface RoleDao {

    int getRoleIdByEnum(Role role);

}
