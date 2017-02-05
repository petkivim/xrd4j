package com.pkrete.xrd4j.common.security;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * This class implements asymmetric decryption. The decryption should be made
 * using the private key of the receiver. The sender has encrypted the data
 * using the public key of the receiver.
 *
 * @author Petteri Kivimäki
 */
public class AsymmetricDecrypter extends AbstractDecrypter implements Decrypter {

    /**
     * Private key that's used for decryption.
     */
    private final PrivateKey privateKey;
    /**
     * Transformation that the cipher uses, e.g. "RSA/ECB/PKCS1Padding"
     */
    private String transformation;

    /**
     * Constructs and initializes a new AsymmetricDecrypter object. During the
     * initialization the private key used for decryption is fetched from the
     * defined key store. If fetching the key fails for some reason, an
     * exception is thrown. The default transformation is
     * "RSA/ECB/PKCS1Padding".
     *
     * @param path absolute path of the key store file
     * @param storePassword password of the key store
     * @param privateKeyAlias alias of the private key in the key store
     * @param keyPassword password of the private key
     * @throws FileNotFoundException
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws UnrecoverableEntryException
     */
    public AsymmetricDecrypter(String path, String storePassword, String privateKeyAlias, String keyPassword) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException {
        this.privateKey = CryptoHelper.getPrivateKey(path, storePassword, privateKeyAlias, keyPassword);
        this.transformation = "RSA/ECB/PKCS1Padding";
    }

    /**
     * Constructs and initializes a new AsymmetricDecrypter object. During the
     * initialization the private key used for decryption is fetched from the
     * defined key store. If fetching the key fails for some reason, an
     * exception is thrown.
     *
     * @param path absolute path of the key store file
     * @param storePassword password of the key store
     * @param privateKeyAlias alias of the private key in the key store
     * @param keyPassword password of the private key
     * @param transformation transformation that the cipher uses
     * @throws FileNotFoundException
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws UnrecoverableEntryException
     */
    public AsymmetricDecrypter(String path, String storePassword, String privateKeyAlias, String keyPassword, String transformation) throws FileNotFoundException, KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException {
        this(path, storePassword, privateKeyAlias, keyPassword);
        this.transformation = transformation;
    }

    /**
     * Decrypts the given byte array using "RSA/ECB/PKCS1Padding" cipher.
     *
     * @param cipherText byte array to be decrypted
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
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(cipherText);
    }

    /**
     * Returns the private key used by this decrypter.
     *
     * @return the private key used by this decrypter
     */
    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }
}
