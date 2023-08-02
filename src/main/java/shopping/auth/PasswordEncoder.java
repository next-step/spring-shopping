package shopping.auth;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    public boolean match(String planePassword, String digest) {
        byte[] salt = getSalt(digest);
        String hashedPassword = encode(planePassword, salt);

        return digest.equals(hashedPassword);
    }

    private String encode(String password, byte[] salt) {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] hash = factory.generateSecret(spec).getEncoded();

            String base64Hash = Base64.getEncoder().encodeToString(hash);
            String base64Salt = Base64.getEncoder().encodeToString(salt);
            return base64Salt + base64Hash;
        } catch (Exception e) {
            throw new IllegalStateException("패스워드 해시 과정에서 에러가 발생하였습니다.");
        }
    }

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
        return Base64.getDecoder().decode(digest.substring(0, 24));
    }
}
