package com.pkrete.xrd4j.common.security;

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
 * decrypter classes. This class implements the decrypt method that takes care
 * of converting the data in the right format before and after the decryption.
 * Child classes have to implement the actual decryption.
 *
 * @author Petteri Kivimäki
 */
public abstract class AbstractDecrypter {
    
    private static final Logger logger = LoggerFactory.getLogger(AbstractDecrypter.class);
    
    protected abstract byte[] decrypt(byte[] cipherText) throws NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException;

    /**
     * Decrypts the given base 64 encoded string and returns it as plain text.
     *
     * @param cipherText base 64 encoded encrypted string to be decrypted
     * @return decrypted cipherText as plain text
     */
    public String decrypt(String cipherText) {
        try {
            byte[] encrypted = CryptoHelper.decodeBase64(cipherText);
            byte[] decrypted = decrypt(encrypted);
            return new String(decrypted);
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }
}
