package com.pkrete.xrd4j.common.security;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import junit.framework.TestCase;

/**
 * Test cases for AsymmetricEncrypter class. The cases cover decryption too.
 *
 * @author Petteri Kivimäki
 */
public class AsymmetricEncrypterTest extends TestCase {

    // Public key
    private final static String publicKeyFile = "src/test/resources/mytruststore1.jks";
    private final static String publicKeyFilePass = "truststore1";
    private final static String publicKeyAlias = "key2";
    // Private key
    private final static String privateKeyFile = "src/test/resources/mykeystore2.jks";
    private final static String privateKeyFilePass = "storepass2";
    private final static String privateKeyAlias = "selfsigned";
    private final static String privateKeyPass = "keypass2";

    /**
     * Test encrypting and decrypting 128 bit AES key that's used for encrypting
     * data.
     *
     * @throws KeyStoreException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws UnrecoverableEntryException
     */
    public void testEncryption1() throws KeyStoreException, IOException, FileNotFoundException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException {
        Encrypter ae = new AsymmetricEncrypter(publicKeyFile, publicKeyFilePass, publicKeyAlias);
        Key key = CryptoHelper.generateAESKey(128);
        String sessionKey = CryptoHelper.encodeBase64(key.getEncoded());
        String encryptedSessionKey = ae.encrypt(sessionKey);
        Decrypter ad = new AsymmetricDecrypter(privateKeyFile, privateKeyFilePass, privateKeyAlias, privateKeyPass);
        String decryptedSessionKey = ad.decrypt(encryptedSessionKey);
        Key decodedKey = CryptoHelper.strToKey(decryptedSessionKey);

        assertEquals(sessionKey, decryptedSessionKey);
        assertEquals(key, decodedKey);
    }

    /**
     * Test encrypting and decrypting 128 bit AES key that's used for encrypting
     * data. In addition encrypt and decrypt a SOAP message using
     * encrypted/decrypted AES key.
     *
     * @throws KeyStoreException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws UnrecoverableEntryException
     */
    public void testEncryption2() throws KeyStoreException, IOException, FileNotFoundException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException {
        String msg = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>ID-1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion><xrd:requestHash algorithmId=\"SHA-512\">ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==</xrd:requestHash></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890 Каллио</data></request><response><data>9876543210</data></response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        //##############################
        // BEGIN: encrypt data
        Key key = CryptoHelper.generateAESKey(128);
        byte[] iv = CryptoHelper.generateIV();
        Encrypter se = new SymmetricEncrypter(key, iv);
        String encrypted = se.encrypt(msg);
        // END: encrypt data

        // BEGIN: encrypt symmetric key
        Encrypter ae = new AsymmetricEncrypter(publicKeyFile, publicKeyFilePass, publicKeyAlias);
        String sessionKey = CryptoHelper.encodeBase64(key.getEncoded());
        String encryptedSessionKey = ae.encrypt(sessionKey);
        String ivString = CryptoHelper.encodeBase64(iv);
        // END: encrypt symmetric key

        //################################################################
        // BEGIN: decrypt symmetric key
        Decrypter ad = new AsymmetricDecrypter(privateKeyFile, privateKeyFilePass, privateKeyAlias, privateKeyPass);
        String decryptedSessionKey = ad.decrypt(encryptedSessionKey);
        Key decodedKey = CryptoHelper.strToKey(decryptedSessionKey);
        byte[] decodedIv = CryptoHelper.decodeBase64(ivString);
        // END: decrypt symmetric key

        // BEGIN: decrypt data
        Decrypter sd = new SymmetricDecrypter(decodedKey, decodedIv);
        String decrypted = sd.decrypt(encrypted);
        // END: decrypt data

        assertEquals(sessionKey, decryptedSessionKey);
        assertEquals(key, decodedKey);
        assertEquals(msg, decrypted);
    }
}
