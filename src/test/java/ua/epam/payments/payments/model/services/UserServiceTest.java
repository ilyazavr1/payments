package ua.epam.payments.payments.model.services;

import org.junit.Before;
import org.junit.Test;
import ua.epam.payments.payments.model.dao.UserDao;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.exception.AuthenticationException;
import ua.epam.payments.payments.model.exception.RegisteredEmailException;
import ua.epam.payments.payments.model.exception.UserIsBlockedException;


import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserDao userDao;

    private final static String FIRST_NAME = "Vlad";
    private final static String LAST_NAME = "Lizogub";
    private final static String SURNAME = "Aleksandrovich";
    private final static String REGISTERED_EMAIL = "vlad@gmail.con";
    private final static String NOT_REGISTERED_EMAIL = "qweqwqweqwe@gmail.con";
    private final static String PASSWORD = "Qwerty12345";
    private final static String RANDOM_PASSWORD = "Qwerty12fas345";
    private final static String HASHED_PASSWORD = "5c66a97b2e8c9e9f1c90dcff9c5831675c497c4fd67b0964b98ffcc3bdc27711023f309b91500b084674952900c6d15358057e3b7f3a4a240b6b348318623f2929478e0d4a4bc3e8628b6d3ed581d8f6";

    private User USER_REGISTERED;
    private User USER_NOT_REGISTERED;

    @Before
    public void setUp() throws Exception {
        userDao = mock(UserDao.class);
        userService = new UserService(userDao);
        USER_REGISTERED = new User.Builder()
                .withFirstName(FIRST_NAME)
                .withLastName(LAST_NAME)
                .withSurname(SURNAME)
                .withEmail(REGISTERED_EMAIL)
                .withPassword(HASHED_PASSWORD)
                .withBlocked(false)
                .withRolesId(2)
                .build();
        USER_NOT_REGISTERED = new User.Builder()
                .withFirstName(FIRST_NAME)
                .withLastName(LAST_NAME)
                .withSurname(SURNAME)
                .withEmail(NOT_REGISTERED_EMAIL)
                .withPassword(PASSWORD)
                .withBlocked(false)
                .withRolesId(2)
                .build();
    }

    @Test
    public void getUserById() {
        when(userService.getUserById(122)).thenReturn(USER_REGISTERED);

        assertNotNull(userService.getUserById(122));
    }

    @Test
    public void registerUserShouldThrowRegisteredEmailException() {
        when(userDao.getUserByEmail(REGISTERED_EMAIL)).thenReturn(USER_REGISTERED);

        assertThrows(RegisteredEmailException.class, () -> userService.registerUser(FIRST_NAME, LAST_NAME, SURNAME, REGISTERED_EMAIL, PASSWORD));
    }

    @Test
    public void registerUserShouldNotThrowRegisteredEmailException() {
        when(userDao.getUserByEmail(NOT_REGISTERED_EMAIL)).thenReturn(null);

        assertDoesNotThrow(() -> userService.registerUser(FIRST_NAME, LAST_NAME, SURNAME, NOT_REGISTERED_EMAIL, PASSWORD));

    }

    @Test
    public void authenticateUserShouldThrowAuthenticationExceptionWhenUserNull() {
        when(userDao.getUserByEmail(NOT_REGISTERED_EMAIL)).thenReturn(null);
        assertThrows(AuthenticationException.class, () -> userService.authenticateUser(NOT_REGISTERED_EMAIL, PASSWORD));
    }

    @Test
    public void authenticateUserShouldThrowAuthenticationExceptionWhenPasswordNotCorrect() {
        when(userDao.getUserByEmail(REGISTERED_EMAIL)).thenReturn(USER_REGISTERED);
        assertThrows(AuthenticationException.class, () -> userService.authenticateUser(REGISTERED_EMAIL, RANDOM_PASSWORD));
    }

    @Test
    public void authenticateUserShouldThrowAuthenticationExceptionWhenUserIsBlocked() {
        USER_REGISTERED.setBlocked(true);
        when(userDao.getUserByEmail(REGISTERED_EMAIL)).thenReturn(USER_REGISTERED);
        assertThrows(UserIsBlockedException.class, () -> userService.authenticateUser(REGISTERED_EMAIL, PASSWORD));
    }

    @Test
    public void authenticateUserShouldNotThrowExceptions() {
        when(userDao.getUserByEmail(REGISTERED_EMAIL)).thenReturn(USER_REGISTERED);
        assertDoesNotThrow(() -> userService.authenticateUser(REGISTERED_EMAIL, PASSWORD));

    }

}