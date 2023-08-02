package shopping.infrastructure;

import org.springframework.stereotype.Component;
import shopping.exception.ShoppingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class SHA256PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());

            return bytesToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new ShoppingException("복호화 실패", e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
