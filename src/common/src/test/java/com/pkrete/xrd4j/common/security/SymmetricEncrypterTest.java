package com.pkrete.xrd4j.common.security;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import junit.framework.TestCase;

/**
 * Test cases for SymmetricEncrypter class. The cases cover decryption too.
 *
 * @author Petteri Kivimäki
 */
public class SymmetricEncrypterTest extends TestCase {

    /**
     * Test encrypting and decrypting a simple string containing diacritics.
     *
     * @throws NoSuchAlgorithmException
     */
    public void testEncryption1() throws NoSuchAlgorithmException {
        String data = "This is a test string. ÄäÅåÖö Библиотека Каллио";
        Key key = CryptoHelper.generateAESKey(128);
        byte[] iv = CryptoHelper.generateIV();
        Encrypter se = new SymmetricEncrypter(key, iv);
        String encrypted = se.encrypt(data);

        Decrypter sd = new SymmetricDecrypter(key, iv);
        String decrypted = sd.decrypt(encrypted);
        assertEquals(data, decrypted);
    }

    /**
     * Test encrypting and decrypting a complete SOAP message.
     *
     * @throws NoSuchAlgorithmException
     */
    public void testEncryption2() throws NoSuchAlgorithmException {
        String data = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>ID-1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion><xrd:requestHash algorithmId=\"SHA-512\">ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==</xrd:requestHash></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890</data></request><response><data>9876543210</data></response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        Key key = CryptoHelper.generateAESKey(128);
        byte[] iv = CryptoHelper.generateIV();
        Encrypter se = new SymmetricEncrypter(key, iv);
        String encrypted = se.encrypt(data);

        Decrypter sd = new SymmetricDecrypter(key, iv);
        String decrypted = sd.decrypt(encrypted);
        assertEquals(data, decrypted);
    }
}
