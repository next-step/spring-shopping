package shopping.infrastructure;

import org.springframework.stereotype.Component;
import shopping.domain.user.PasswordEncoder;
import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class SHA256PasswordEncoder implements PasswordEncoder {

    private static final String SHA_256_ALGORITHM = "SHA-256";

    @Override
    public String encode(final String password) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance(SHA_256_ALGORITHM);
            messageDigest.update(password.getBytes());

            return bytesToHex(messageDigest.digest());
        } catch (final NoSuchAlgorithmException exception) {
            throw new ShoppingException(ErrorType.DECODING_FAIL, exception);
        }
    }

    private String bytesToHex(byte[] bytes) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
