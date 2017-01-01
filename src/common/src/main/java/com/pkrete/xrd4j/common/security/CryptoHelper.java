package com.pkrete.xrd4j.common.security;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * This utility class provides helper methods for cryptographic operations.
 *
 * @author Petteri Kivimäki
 */
public class CryptoHelper {

    /**
     * No instances if this class should be created.
     */
    private CryptoHelper() {

    }

    /**
     * Generates symmetric AES key which length is defined by keyLength. Valid
     * values are 128, 192 and 256.
     *
     * @param keyLength key length in bits
     * @return symmetric AES key
     * @throws NoSuchAlgorithmException
     */
    public static Key generateAESKey(int keyLength) throws NoSuchAlgorithmException {
        SecureRandom rand = new SecureRandom();
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(rand);
        generator.init(keyLength);
        return generator.generateKey();
    }

    /**
     * Generates a new initialization vector (IV).
     *
     * @return new initialization vector (IV)
     */
    public static byte[] generateIV() {
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[16];
        random.nextBytes(iv);
        return iv;
    }

    /**
     * Converts the given byte array to base 64 encoded string.
     *
     * @param source byte array
     * @return base 64 encoded string
     */
    public static String encodeBase64(byte[] source) {
        return Base64.getEncoder().encodeToString(source);
    }

    /**
     * Converts the given base 64 encoded string to byte array.
     *
     * @param source base 64 encoded string
     * @return byte array
     */
    public static byte[] decodeBase64(String source) {
        return Base64.getDecoder().decode(source);
    }

    /**
     * Converts the given base 64 base encoded string to a Key object.
     *
     * @param keyStr Key object as 64 base encoded string
     * @return Key object
     */
    public static Key strToKey(String keyStr) {
        byte[] keyBytes = decodeBase64(keyStr);
        return new SecretKeySpec(keyBytes, "AES");
    }
}
