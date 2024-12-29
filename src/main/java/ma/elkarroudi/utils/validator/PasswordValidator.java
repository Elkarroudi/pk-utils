package ma.elkarroudi.utils.validator;

import java.security.SecureRandom;
import java.util.regex.Pattern;

public class PasswordValidator {

    private static final int DEFAULT_PASSWORD_LENGTH = 20;
    private static final String PASSWORD_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
    private static final String STRONG_PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";

    /**
     * Generates a random password of the specified length.
     *
     * @param length the length of the password to generate
     * @return a randomly generated password
     */
    public static String generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(PASSWORD_CHARS.length());
            password.append(PASSWORD_CHARS.charAt(index));
        }
        return password.toString();
    }

    /**
     * Generates a random password of the default length.
     *
     * @return a randomly generated password
     */
    public static String generateRandomPassword() {
        return generateRandomPassword(DEFAULT_PASSWORD_LENGTH);
    }

    /**
     * Validates if the provided password is strong based on the regex pattern.
     *
     * @param password the password to validate
     * @return true if the password is strong, false otherwise
     */
    public static boolean isStrongPassword(String password) {
        return password != null && Pattern.matches(STRONG_PASSWORD_REGEX, password);
    }

}
