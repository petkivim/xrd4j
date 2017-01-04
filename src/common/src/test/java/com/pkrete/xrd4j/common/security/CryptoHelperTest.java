package com.pkrete.xrd4j.common.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import junit.framework.TestCase;

/**
 * Test cases for CryptoHelper class. The cases cover decryption too.
 *
 * @author Petteri Kivimäki
 */
public class CryptoHelperTest extends TestCase {

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
     * Test signature with a simple string.
     */
    public void testSiganture1() {
        String data = "This is a test string that contains strange charaters: ÄäÅåÖö Библиотека Каллио";
        assertEquals(true, this.createAndverifySignature(data));
    }

    /**
     * Test signature with a SOAP message.
     */
    public void testSiganture2() {
        String data = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>ID-1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion><xrd:requestHash algorithmId=\"SHA-512\">ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==</xrd:requestHash></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890 Каллио</data></request><response><data>9876543210</data></response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        assertEquals(true, this.createAndverifySignature(data));
    }

    /**
     * Test signature with a modified SOAP message.
     */
    public void testSiganture3() {
        String data = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>ID-1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion><xrd:requestHash algorithmId=\"SHA-512\">ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==</xrd:requestHash></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890 Каллио</data></request><response><data>9876543210</data></response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String dataVerify = data + ' ';
        assertEquals(false, this.createAndverifySignature(data, dataVerify));
    }

    /**
     * Test signature with a SOAP message and generate ECDSA keys during the
     * test.
     */
    public void testSiganture4() {
        String data = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>ID-1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion><xrd:requestHash algorithmId=\"SHA-512\">ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==</xrd:requestHash></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890 Каллио</data></request><response><data>9876543210</data></response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            keyGen.initialize(256, new SecureRandom());
            KeyPair keyPair = keyGen.generateKeyPair();
            String signature = CryptoHelper.createSignature(keyPair.getPrivate(), data, "SHA512withECDSA");
            assertEquals(true, CryptoHelper.verifySignature(keyPair.getPublic(), data, signature, "SHA512withECDSA"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private boolean createAndverifySignature(String data) {
        return this.createAndverifySignature(data, data);
    }

    private boolean createAndverifySignature(String signData, String verifyData) {
        try {
            PrivateKey privateKey = CryptoHelper.getPrivateKey(privateKeyFile, privateKeyFilePass, privateKeyAlias, privateKeyPass);
            PublicKey publicKey = CryptoHelper.getPublicKey(publicKeyFile, publicKeyFilePass, publicKeyAlias);
            String signature = CryptoHelper.createSignature(privateKey, signData);
            return CryptoHelper.verifySignature(publicKey, verifyData, signature);
        } catch (Exception e) {
            return false;
        }
    }
}
