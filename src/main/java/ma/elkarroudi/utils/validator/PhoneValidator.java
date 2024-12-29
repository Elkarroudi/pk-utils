package ma.elkarroudi.utils.validator;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class PhoneValidator {

    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    /**
     * Validates if the provided phone number is valid for the given region.
     *
     * @param phoneNumber the phone number to validate
     * @param regionCode the region code (e.g., "US" for United States)
     * @return true if the phone number is valid, false otherwise
     */
    public static boolean isValidPhoneNumber(String phoneNumber, String regionCode) {
        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phoneNumber, regionCode);
            return phoneNumberUtil.isValidNumber(number);
        } catch (NumberParseException e) {
            return false;
        }
    }

    /**
     * Validates if the provided phone number prefix is valid.
     *
     * @param phoneNumber the phone number to validate
     * @param regionCode the region code (e.g., "US" for United States)
     * @return true if the phone number prefix is valid, false otherwise
     */
    public static boolean isValidPhoneNumberPrefix(String phoneNumber, String regionCode) {
        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phoneNumber, regionCode);
            return phoneNumberUtil.isPossibleNumber(number);
        } catch (NumberParseException e) {
            return false;
        }
    }

}
