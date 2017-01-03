package com.pkrete.xrd4j.common.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

/**
 * This class implements symmetric decryption. The same key and initialization
 * vector (IV) must be used for both encryption and decryption. This means that
 * sender and receiver must exchange the key and initialization vector in
 * addition to the data. The key and IV can be sent together with the data if
 * they are encrypted using asymmetric encryption.
 *
 * @author Petteri Kivimäki
 */
public class SymmetricDecrypter extends AbstractDecrypter implements Decrypter {

    private final Key key;
    private final byte[] iv;
    private String transformation;

    /**
     * Constructs and initializes a new SymmetricDecrypter object. The default
     * transformation is "AES/CBC/PKCS5Padding".
     *
     * @param key Key object that's used for decryption
     * @param iv initialization vector for decryption. The same initialization
     * vector that was used for encrypting the data must be used for decryption.
     */
    public SymmetricDecrypter(Key key, byte[] iv) {
        this.key = key;
        this.iv = iv;
        this.transformation = "AES/CBC/PKCS5Padding";
    }

    /**
     * Constructs and initializes a new SymmetricDecrypter object.
     *
     * @param key Key object that's used for decryption
     * @param iv initialization vector for decryption. The same initialization
     * vector that was used for encrypting the data must be used for decryption.
     * @param transformation transformation that the cipher uses
     */
    public SymmetricDecrypter(Key key, byte[] iv, String transformation) {
        this.key = key;
        this.iv = iv;
        this.transformation = transformation;
    }

    /**
     * Decrypts the given byte array using "AES/CBC/PKCS5Padding" cipher.
     *
     * @param cipherText encrypted byte array
     * @return decrypted byte array
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    @Override
    protected byte[] decrypt(byte[] cipherText) throws NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(this.transformation);
        cipher.init(Cipher.DECRYPT_MODE, this.key, new IvParameterSpec(this.iv));
        return cipher.doFinal(cipherText);
    }
}
