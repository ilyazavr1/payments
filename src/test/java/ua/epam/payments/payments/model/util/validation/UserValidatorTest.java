package ua.epam.payments.payments.model.util.validation;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserValidatorTest {

    private static final String CORRECT_FIRST_NAME = "Vlad";
    private static final String CORRECT_LAST_NAME = "Redyko";
    private static final String CORRECT_SURNAME = "Vasilevich";
    private static final String CORRECT_EMAIL = "vlad@gmail.com";
    private static final String CORRECT_PASSWORD = "Qwerty12345";

    private static final String INVALID_USERNAME = "Vlad1";
    private static final String INVALID_FIRST_NAME = "123";
    private static final String INVALID_LAST_NAME = "   ";
    private static final String INVALID_EMAIL = "fabio.gmail.com";
    private static final String INVALID_PASSWORD = "123";


   private UserValidator userValidator = new UserValidator();


    @Test
    public void isUserInputValidFiveParametersReturnTrue() {
        assertTrue(userValidator.isUserInputValid(CORRECT_FIRST_NAME,CORRECT_LAST_NAME,CORRECT_SURNAME,CORRECT_EMAIL,CORRECT_PASSWORD));
    }
    @Test
    public void isUserInputValidReturnFalse() {
        assertFalse(userValidator.isUserInputValid(INVALID_FIRST_NAME,CORRECT_LAST_NAME,CORRECT_SURNAME,CORRECT_EMAIL,CORRECT_PASSWORD));
        assertFalse(userValidator.isUserInputValid(CORRECT_FIRST_NAME,CORRECT_LAST_NAME,CORRECT_SURNAME,INVALID_EMAIL,CORRECT_PASSWORD));
    }
    @Test
    public void isUserInputValidTwoParametersReturnTrue() {
        assertTrue(userValidator.isUserInputValid(CORRECT_EMAIL,CORRECT_PASSWORD));
    }

    @Test
    public void testGetErrorsWithFiveInvalidDataInput() {
        assertFalse(userValidator.isUserInputValid(INVALID_FIRST_NAME,INVALID_LAST_NAME,INVALID_USERNAME,INVALID_EMAIL,INVALID_PASSWORD));
        assertEquals(5, userValidator.getErrors().size());
    }




}