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
 * This class implements symmetric encryption. The same key and initialization
 * vector (IV) must be used for both encryption and decryption. This means that
 * sender and receiver must exchange the key and initialization vector in
 * addition to the data. The key and IV can be sent together with the data if
 * they are encrypted using asymmetric encryption.
 *
 * @author Petteri Kivimäki
 */
public class SymmetricEncrypter extends AbstractEncrypter implements Encrypter {

    private final Key key;
    private final byte[] iv;

    /**
     * Constructs and initializes a new SymmetricEncrypter object.
     *
     * @param key Key object that's used for encryption
     * @param iv initialization vector for encryption. The same initialization
     * vector must be used for the decryption too.
     */
    public SymmetricEncrypter(Key key, byte[] iv) {
        this.key = key;
        this.iv = iv;
    }

    /**
     * Encrypts the given byte array using "AES/CBC/PKCS5Padding" cipher.
     *
     * @param plaintext byte array to be encrypted
     * @return encrypted byte array
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    @Override
    protected byte[] encrypt(byte[] plaintext) throws NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(this.key.getAlgorithm() + "/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, this.key, new IvParameterSpec(this.iv));
        return cipher.doFinal(plaintext);
    }
}
