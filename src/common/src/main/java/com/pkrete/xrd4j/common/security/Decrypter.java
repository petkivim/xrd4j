package com.pkrete.xrd4j.common.security;

/**
 * This interface defines operations for decrypting encrypted strings.
 *
 * @author Petteri Kivimäki
 */
@FunctionalInterface
public interface Decrypter {

    /**
     * Decrypt the given encrypted string.
     *
     * @param cipherText encrypted string
     * @return plain text string
     */
    public String decrypt(String cipherText);
}
