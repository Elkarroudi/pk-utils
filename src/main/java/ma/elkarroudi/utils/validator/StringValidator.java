package ma.elkarroudi.utils.validator;

import java.util.UUID;

public class StringValidator {

    /**
     * Checks if the provided string is null or empty.
     *
     * @param str the string to check
     * @return true if the string is null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Generates a random UUID string.
     *
     * @return a randomly generated UUID string
     */
    public static String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }

}
