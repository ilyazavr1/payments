package ua.epam.payments.payments.model.services.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidation {
    public static final Pattern EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern NAME_REGEX = Pattern.compile("^([A-Za-z]){1}([ -]|[a-zA-Z]){1,49}$");
    public static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");



    public static boolean validateName(String name) {
        Matcher matcher = NAME_REGEX.matcher(name);
        return matcher.matches();
    }

    public static boolean validateEmail(String email) {
        Matcher matcher = EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.matches();
    }

    public static boolean validatePassword(String password) {
        Matcher matcher = PASSWORD_REGEX.matcher(password);
        return matcher.matches();
    }

}
