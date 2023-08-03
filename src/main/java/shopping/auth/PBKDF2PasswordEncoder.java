package shopping.auth;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.springframework.stereotype.Component;
import shopping.exception.HashFailException;

@Component
public class PBKDF2PasswordEncoder implements PasswordEncoder {

    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 128;
    private static final String PBKDF2_ALGORITHM_WITH_HMAC_SHA1 = "PBKDF2WithHmacSHA1";
    private static final int SALT_BEGIN_INDEX = 0;
    private static final int SALT_END_INDEX = 24;

    @Override
    public boolean match(String planePassword, String digest) {
        byte[] salt = getSalt(digest);
        String hashedPassword = encode(planePassword, salt);

        return digest.equals(hashedPassword);
    }

    private String encode(String password, byte[] salt) {
        try {
            KeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    salt,
                    ITERATION_COUNT,
                    KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory
                    .getInstance(PBKDF2_ALGORITHM_WITH_HMAC_SHA1);

            byte[] hash = factory.generateSecret(spec).getEncoded();

            String base64Hash = Base64.getEncoder().encodeToString(hash);
            String base64Salt = Base64.getEncoder().encodeToString(salt);
            return base64Salt + base64Hash;
        } catch (Exception e) {
            throw new HashFailException();
        }
    }

    @Override
    public String encode(String password) {
        return encode(password, getSalt());
    }

    private byte[] getSalt() {
        Random random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private byte[] getSalt(String digest) {
        return Base64.getDecoder().decode(digest.substring(SALT_BEGIN_INDEX, SALT_END_INDEX));
    }
}
