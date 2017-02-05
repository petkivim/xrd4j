package com.pkrete.xrd4j.common.security;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.exception.XRd4JRuntimeException;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This utility class provides helper methods for cryptographic operations.
 *
 * @author Petteri Kivimäki
 */
public class CryptoHelper {

    private static final Logger logger = LoggerFactory.getLogger(CryptoHelper.class);

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
        return generateKey(keyLength, "AES");
    }

    /**
     * Creates a new secret key using the specified key length and key
     * algorithm.
     *
     * @param keyLength key length in bits
     * @param algorithm key algorithm
     * @return new secret key
     * @throws NoSuchAlgorithmException
     */
    public static Key generateKey(int keyLength, String algorithm) throws NoSuchAlgorithmException {
        SecureRandom rand = new SecureRandom();
        KeyGenerator generator = KeyGenerator.getInstance(algorithm);
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

    /**
     * Creates a digital signature of the given data using the given private
     * key. By default SHA512withRSA algorithm is used.
     *
     * @param key the private key of the identity whose signature is going to be
     * generated
     * @param data data to be signed
     * @return signature as base 64 encoded string
     * @throws XRd4JException if there's an error
     */
    public static String createSignature(PrivateKey key, String data) throws XRd4JException {
        return createSignature(key, data, "SHA512withRSA");
    }

    /**
     * Creates a digital signature of the given data using the given private key
     * and defined signature algorithm.
     *
     * @param key the private key of the identity whose signature is going to be
     * generated
     * @param data data to be signed
     * @param algorithm the algorithm that's used for generating the signature
     * @return signature as base 64 encoded string
     */
    public static String createSignature(PrivateKey key, String data, String algorithm) {
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initSign(key);
            signature.update(data.getBytes());
            byte[] signedBytes = signature.sign();
            return encodeBase64(signedBytes);
        } catch (SignatureException | InvalidKeyException | NoSuchAlgorithmException ex) {
            logger.error(ex.getMessage(), ex);
            throw new XRd4JRuntimeException(ex.getMessage());
        }
    }

    /**
     * Verifies the signature using the public key and SHA512withRSA algorithm.
     *
     * @param key the public key of the identity whose signature is going to be
     * verified
     * @param data data for which a signature was generated
     * @param signatureStr base 64 encoded signature to be verified
     * @return true if the signature was verified, false if not
     */
    public static boolean verifySignature(PublicKey key, String data, String signatureStr) {
        return verifySignature(key, data, signatureStr, "SHA512withRSA");
    }

    /**
     * Verifies the signature using the public key and the given algorithm.
     *
     * @param key the public key of the identity whose signature is going to be
     * verified
     * @param data data for which a signature was generated
     * @param signatureStr base 64 encoded signature to be verified
     * @param algorithm
     * @return true if the signature was verified, false if not
     */
    public static boolean verifySignature(PublicKey key, String data, String signatureStr, String algorithm) {
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initVerify(key);
            signature.update(data.getBytes());
            byte[] signedBytes = decodeBase64(signatureStr);
            return signature.verify(signedBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException ex) {
            logger.error(ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * Fetches the public key matching the given alias from the defined key
     * store.
     *
     * @param path absolute path of the trust store file
     * @param password trust store password
     * @param publicKeyAlias alias of the public key in the trust store
     * @return public key with the given alias
     */
    public static PublicKey getPublicKey(String path, String password, String publicKeyAlias) {
        try (FileInputStream fis = new java.io.FileInputStream(path)) {
            KeyStore keyStore = KeyStore.getInstance("jks");
            keyStore.load(fis, password.toCharArray());
            Certificate cert;
            cert = keyStore.getCertificate(publicKeyAlias);
            return cert.getPublicKey();
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException ex) {
            logger.error(ex.getMessage(), ex);
            throw new XRd4JRuntimeException(ex.getMessage());
        }
    }

    /**
     * Fetches the private key matching the given alias from the defined key
     * store.
     *
     * @param path absolute path of the key store file
     * @param storePassword password of the key store
     * @param privateKeyAlias alias of the private key in the key store
     * @param keyPassword password of the private key
     * @return private key with the given alias
     */
    public static PrivateKey getPrivateKey(String path, String storePassword, String privateKeyAlias, String keyPassword) {
        try (FileInputStream fis = new java.io.FileInputStream(path)) {
            KeyStore keyStore = KeyStore.getInstance("jks");
            keyStore.load(fis, storePassword.toCharArray());
            KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(privateKeyAlias, new KeyStore.PasswordProtection(keyPassword.toCharArray()));
            return pkEntry.getPrivateKey();
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableEntryException ex) {
            logger.error(ex.getMessage(), ex);
            throw new XRd4JRuntimeException(ex.getMessage());
        }
    }
}
