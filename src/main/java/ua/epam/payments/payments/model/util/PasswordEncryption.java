package ua.epam.payments.payments.model.util;


import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class PasswordEncryption {
    private static final int ITERATIONS = 200;
    private static final int KEY_LENGTH = 512;
    private static final int SALT_SIZE = 16;


    public  String encrypt(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt =getSalt();
        return Hex.encodeHexString(hashPassword(password, salt))  + Hex.encodeHexString(salt);
    }

    private  byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec  spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return factory.generateSecret(spec).getEncoded();
    }

    private  byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);
        return salt;
    }

    public  boolean isPasswordCorrect(String inputPassword, String hashedPassword) throws DecoderException, NoSuchAlgorithmException, InvalidKeySpecException {
        String storedHexPassword = hashedPassword.substring(0, (KEY_LENGTH / 8) * 2);
        byte[] salt = Hex.decodeHex(hashedPassword.substring(KEY_LENGTH / 8 * 2).toCharArray());
        String hashedPassToCheck = Hex.encodeHexString(hashPassword(inputPassword, salt));

        return storedHexPassword.equals(hashedPassToCheck);
    }



}
