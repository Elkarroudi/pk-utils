package ma.elkarroudi.utils.helper;

import java.security.SecureRandom;
import java.util.Base64;

public abstract class TokenHelper {

    public String generateToken() {
        byte[] randomBytes = new byte[64];
        new SecureRandom().nextBytes(randomBytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

}
