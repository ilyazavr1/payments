package ua.epam.payments.payments.model.util.validation;

import ua.epam.payments.payments.web.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Validates User input during registration.
 *
 * @author Illia Smiian
 */
public class UserValidator {
    public static final Pattern EMAIL_ADDRESS_REGEX = Pattern.compile("^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern NAME_REGEX = Pattern.compile("^(\\p{L}){1}( |[\\p{L}]){2,50}$");
    public static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");

    private List<String> errors = new ArrayList<>();

    public boolean isUserInputValid(String firstName, String lastName, String surname, String email, String password) {
        validateFirstName(firstName);
        validateLastName(lastName);
        validateSurnameName(surname);
        validateEmail(email);
        validatePassword(password);

        return errors.isEmpty();
    }
    public boolean isUserInputValid(String email, String password) {
        validateEmail(email);
        validatePassword(password);
        return errors.isEmpty();
    }

    public boolean validateFirstName(String name) {
        Matcher matcher = NAME_REGEX.matcher(name);
        if (!matcher.matches()) {
            errors.add(Constants.INVALID_FIRST_NAME);
            return false;
        }
        return true;
    }
    public boolean validateLastName(String name) {
        Matcher matcher = NAME_REGEX.matcher(name);
        if (!matcher.matches()) {
            errors.add(Constants.INVALID_LAST_NAME);
            return false;
        }
        return true;
    }
    public boolean validateSurnameName(String name) {
        Matcher matcher = NAME_REGEX.matcher(name);
        if (!matcher.matches()) {
            errors.add(Constants.INVALID_SURNAME);
            return false;
        }
        return true;
    }
    public boolean validateEmail(String email) {
        Matcher matcher = EMAIL_ADDRESS_REGEX.matcher(email);
        if (!matcher.matches()) {
            errors.add(Constants.INVALID_EMAIL);
            return false;
        }
        return true;
    }

    public boolean validatePassword(String password) {
        Matcher matcher = PASSWORD_REGEX.matcher(password);
        if (!matcher.matches()) {
            errors.add(Constants.INVALID_PASSWORD);
            return false;
        }
        return true;
    }

    public List<String> getErrors(){
        return errors;
    }

}
