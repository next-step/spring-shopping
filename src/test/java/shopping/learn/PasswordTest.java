package shopping.learn;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Disabled
@DisplayName("비밀번호 암호화 학습 테스트")
class PasswordTest {

    @Test
    void test()
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // given
        String encryptionKeyString = "thisisa128bitkey";
        String originalMessage = "암호화 메시지 입니다.";

        String planeText = "hello";
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] encryptionKeyBytes = encryptionKeyString.getBytes(StandardCharsets.UTF_8);

        SecretKey secretKey = new SecretKeySpec(encryptionKeyBytes, "AES");
        // when
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrpyedMessageBytes = cipher.doFinal(originalMessage.getBytes());

        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedMessageBytes = cipher.doFinal(encrpyedMessageBytes);

        // then
        assert (originalMessage).equals(new String(decryptedMessageBytes));
    }

    @Test
    void name2() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "1234";

        String secretKey = "aaaa";
        byte[] salt = secretKey.getBytes();

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();
        String db1 = Base64.getEncoder().encodeToString(hash);
        System.out.println(db1);

        KeySpec spec2 = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        byte[] hash2 = factory.generateSecret(spec2).getEncoded();
        String db2 = Base64.getEncoder().encodeToString(hash2);

        assertThat(db1).isEqualTo(db2);

    }

    @Test
    void name3() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "1234";

        Random random = new SecureRandom();

        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();

        String base64Hash = Base64.getEncoder().encodeToString(hash);
        String base64Salt = Base64.getEncoder().encodeToString(salt);
        String db = base64Salt + base64Hash;

        System.out.println(db);

        byte[] decodedSalt = Base64.getDecoder().decode(db.substring(0, 24));
        KeySpec spec2 = new PBEKeySpec(password.toCharArray(), decodedSalt, 65536, 128);
        byte[] hash2 = factory.generateSecret(spec2).getEncoded();

        String base64Hash2 = Base64.getEncoder().encodeToString(hash2);

        assertThat(base64Hash2).isEqualTo(base64Hash);
        System.out.println(base64Hash2);


    }
}
