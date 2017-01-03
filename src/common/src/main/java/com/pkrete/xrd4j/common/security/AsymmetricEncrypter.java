package com.pkrete.xrd4j.common.security;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * This class implements asymmetric encryption. The encryption should be made
 * using the public key of the receiver. The receiver then decrypts the data
 * using his private key.
 *
 * @author Petteri Kivimäki
 */
public class AsymmetricEncrypter extends AbstractEncrypter implements Encrypter {

    /**
     * Receivers public key that's used for encryption.
     */
    private final PublicKey publicKey;
    /**
     * Transformation that the cipher uses, e.g. "RSA/ECB/PKCS1Padding"
     */
    private String transformation;

    /**
     * Constructs and initializes a new AsymmetricEncrypter object. During the
     * initialization the public key used for decryption is fetched from the
     * defined trust store. If fetching the key fails for some reason, an
     * exception is thrown.
     *
     * @param path absolute path of the trust store file
     * @param password trust store password
     * @param publicKeyAlias alias of the public key in the trust store
     * @throws FileNotFoundException
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     */
    public AsymmetricEncrypter(String path, String password, String publicKeyAlias) throws FileNotFoundException, KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        FileInputStream fis = new java.io.FileInputStream(path);
        KeyStore keyStore = KeyStore.getInstance("jks");
        keyStore.load(fis, password.toCharArray());
        Certificate cert;
        cert = keyStore.getCertificate(publicKeyAlias);
        this.publicKey = cert.getPublicKey();
        this.transformation = "RSA/ECB/PKCS1Padding";
    }

    /**
     * Constructs and initializes a new AsymmetricEncrypter object. During the
     * initialization the public key used for decryption is fetched from the
     * defined trust store. If fetching the key fails for some reason, an
     * exception is thrown. The default transformation is
     * "RSA/ECB/PKCS1Padding".
     *
     * @param path absolute path of the trust store file
     * @param password trust store password
     * @param publicKeyAlias alias of the public key in the trust store
     * @param transformation transformation that the cipher uses
     * @throws FileNotFoundException
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     */
    public AsymmetricEncrypter(String path, String password, String publicKeyAlias, String transformation) throws FileNotFoundException, KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        this(path, password, publicKeyAlias);
        this.transformation = transformation;
    }

    /**
     * Encrypts the given byte array using "RSA/ECB/PKCS1Padding" cipher.
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
        Cipher cipher = Cipher.getInstance(this.transformation);
        cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
        return cipher.doFinal(plaintext);
    }
}
