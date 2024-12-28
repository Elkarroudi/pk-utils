package ma.elkarroudi.utils.validator;

import java.util.regex.Pattern;

public class PhoneValidator {

    private static final String PHONE_REGEX = "^\\+?[1-9][0-9]{7,14}$";

    public static boolean isValidPhone(String phone) {
        return phone != null && Pattern.matches(PHONE_REGEX, phone);
    }

}
