package ma.elkarroudi.utils.validator;

import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    /**
     * Validates if the provided email matches the regex pattern.
     *
     * @param email the email address to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return email != null && Pattern.matches(EMAIL_REGEX, email);
    }

}
