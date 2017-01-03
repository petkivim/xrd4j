package com.pkrete.xrd4j.common.security;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This abstract class serves as a base class for symmetric and asymmetric
 * encrypter classes. This class implements the encrypt method that takes care
 * of converting the data in the right format before and after the encryption.
 * Child classes have to implement the actual encryption.
 *
 * @author Petteri Kivimäki
 */
public abstract class AbstractEncrypter {

    private static final Logger logger = LoggerFactory.getLogger(AbstractEncrypter.class);

    protected abstract byte[] encrypt(byte[] plaintext) throws NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException;

    /**
     * Encrypts the given string and returns it as a base 64 encoded string.
     *
     * @param plainText string to be encrypted
     * @return encrypted plainText as base 64 encoded string
     */
    public String encrypt(String plainText) {
        try {
            byte[] decrypted = plainText.getBytes(StandardCharsets.UTF_8);
            byte[] encrypted = encrypt(decrypted);
            return CryptoHelper.encodeBase64(encrypted);
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }
}
