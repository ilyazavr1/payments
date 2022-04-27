package ua.epam.payments.payments.model.util;


import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.web.servlets.CardBlock;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class PasswordEncryption {
    private static final int ITERATIONS = 200;
    private static final int KEY_LENGTH = 512;
    private static final int SALT_SIZE = 16;

    private static final Logger logger = LogManager.getLogger(PasswordEncryption.class);

    public String encrypt(String password)   {
        byte[] salt = getSalt();
        try {
            return Hex.encodeHexString(hashPassword(password, salt)) + Hex.encodeHexString(salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("{}, when trying to encrypt user password", e.getMessage());
            throw new RuntimeException();
        }
    }

    private byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return factory.generateSecret(spec).getEncoded();
    }

    private byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);
        return salt;
    }

    public boolean isPasswordCorrect(String inputPassword, String hashedPassword) {
        String storedHexPassword = hashedPassword.substring(0, (KEY_LENGTH / 8) * 2);
        byte[] salt = new byte[0];
        String hashedPassToCheck = "";
        try {
            salt = Hex.decodeHex(hashedPassword.substring(KEY_LENGTH / 8 * 2).toCharArray());
            hashedPassToCheck = Hex.encodeHexString(hashPassword(inputPassword, salt));
        } catch (DecoderException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("{}, when trying to authenticate user password", e.getMessage());
            throw new RuntimeException();
        }

        return storedHexPassword.equals(hashedPassToCheck);
    }


}
