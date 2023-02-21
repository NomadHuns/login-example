package shop.mtcoding.loginexam.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

public class ShaTest {

    @Test
    public void sha_test() {
        String passwordSha = "";
        try {
            passwordSha = ShaTest.encode("1234");
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println("테스트 : " + passwordSha);
    }

    public static String encode(String password) throws NoSuchAlgorithmException {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
    }
}
