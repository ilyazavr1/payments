package ua.epam.payments.payments.model.services;

import org.junit.Before;
import org.junit.Test;
import ua.epam.payments.payments.model.dao.impl.UserDaoImpl;

import static org.junit.Assert.*;

public class UserServiceTest {

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        userService = new UserService(new UserDaoImpl());
    }

    @Test
    public void getUserById() {
        assertNull(userService.getUserById(122));
    }
}