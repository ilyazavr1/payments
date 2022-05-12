package ua.epam.payments.payments.db;

import ua.epam.payments.payments.model.dao.impl.RoleDaoImpl;
import ua.epam.payments.payments.model.entity.Role;

public class Testtt {
    public static void main(String[] args) {

        RoleDaoImpl roleDao = new RoleDaoImpl();


        System.out.println(roleDao.getRoleIdByEnum(Role.ADMINISTRATOR));
        System.out.println(roleDao.getRoleIdByEnum(Role.CLIENT));



    }
}
