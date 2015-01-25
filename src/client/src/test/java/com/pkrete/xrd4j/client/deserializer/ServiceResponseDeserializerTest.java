/**
 * MIT License
 *
 * Copyright (C) 2014 Petteri Kivimäki
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.pkrete.xrd4j.client.deserializer;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.member.MemberClass;
import com.pkrete.xrd4j.common.member.ObjectType;
import com.pkrete.xrd4j.common.member.SDSBInstance;
import com.pkrete.xrd4j.common.message.ErrorMessageType;
import com.pkrete.xrd4j.common.message.ServiceResponse;
import com.pkrete.xrd4j.common.util.SOAPHelper;
import java.util.Map;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import junit.framework.TestCase;

/**
 * Test cases for ServiceRequestDeserializerImpl class.
 *
 * @author Petteri Kivimäki
 */
public class ServiceResponseDeserializerTest extends TestCase {

    /**
     * Response to subsystem -> service level service call
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test1() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:userId>EE1234567890</sdsb:userId><sdsb:id>ID-1234567890</sdsb:id><sdsb:requestHash algorithmId=\"SHA-512\">ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==</sdsb:requestHash></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890</data></request><response><data>9876543210</data></response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer();
        ServiceResponse<String, String> response = deserializer.deserialize(msg);


        assertEquals(SDSBInstance.FI, response.getConsumer().getSdsbInstance());
        assertEquals(MemberClass.GOV, response.getConsumer().getMemberClass());
        assertEquals("MEMBER1", response.getConsumer().getMemberCode());
        assertEquals("subsystem", response.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, response.getConsumer().getObjectType());

        assertEquals(SDSBInstance.FI, response.getProducer().getSdsbInstance());
        assertEquals(MemberClass.COM, response.getProducer().getMemberClass());
        assertEquals("MEMBER2", response.getProducer().getMemberCode());
        assertEquals("subsystem", response.getProducer().getSubsystemCode());
        assertEquals("getRandom", response.getProducer().getServiceCode());
        assertEquals("v1", response.getProducer().getServiceVersion());
        assertEquals("ID-1234567890", response.getId());
        assertEquals("EE1234567890", response.getUserId());
        assertEquals(ObjectType.SERVICE, response.getProducer().getObjectType());
        assertEquals("1234567890", response.getRequestData());
        assertEquals("9876543210", response.getResponseData());

        assertEquals("SHA-512", response.getRequestHashAlgorithm());
        assertEquals("ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==", response.getRequestHash());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to subsystem -> service level service call. Request and
     * response with namespace prefix.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test2() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:userId>EE1234567890</sdsb:userId><sdsb:id>ID-1234567890</sdsb:id><sdsb:requestHash algorithmId=\"SHA-512\">ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==</sdsb:requestHash></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><ns1:request><ns1:data>1234567890</ns1:data></ns1:request><ns1:response><ns1:data>9876543210</ns1:data></ns1:response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer();
        ServiceResponse<String, String> response = deserializer.deserialize(msg);


        assertEquals(SDSBInstance.FI, response.getConsumer().getSdsbInstance());
        assertEquals(MemberClass.GOV, response.getConsumer().getMemberClass());
        assertEquals("MEMBER1", response.getConsumer().getMemberCode());
        assertEquals("subsystem", response.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, response.getConsumer().getObjectType());

        assertEquals(SDSBInstance.FI, response.getProducer().getSdsbInstance());
        assertEquals(MemberClass.COM, response.getProducer().getMemberClass());
        assertEquals("MEMBER2", response.getProducer().getMemberCode());
        assertEquals("subsystem", response.getProducer().getSubsystemCode());
        assertEquals("getRandom", response.getProducer().getServiceCode());
        assertEquals("v1", response.getProducer().getServiceVersion());
        assertEquals("ID-1234567890", response.getId());
        assertEquals("EE1234567890", response.getUserId());
        assertEquals(ObjectType.SERVICE, response.getProducer().getObjectType());
        assertEquals("1234567890", response.getRequestData());
        assertEquals("9876543210", response.getResponseData());

        assertEquals("SHA-512", response.getRequestHashAlgorithm());
        assertEquals("ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==", response.getRequestHash());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to subsystem -> central service level service call
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test3() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"CENTRALSERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:serviceCode>getRandom</id:serviceCode></sdsb:service><sdsb:userId>EE1234567890</sdsb:userId><sdsb:id>ID-1234567890</sdsb:id><sdsb:requestHash algorithmId=\"SHA-512\">ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==</sdsb:requestHash></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890</data></request><response><data>9876543210</data></response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer();
        ServiceResponse<String, String> response = deserializer.deserialize(msg);


        assertEquals(SDSBInstance.FI, response.getConsumer().getSdsbInstance());
        assertEquals(MemberClass.GOV, response.getConsumer().getMemberClass());
        assertEquals("MEMBER1", response.getConsumer().getMemberCode());
        assertEquals("subsystem", response.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, response.getConsumer().getObjectType());

        assertEquals(SDSBInstance.FI, response.getProducer().getSdsbInstance());
        assertEquals(null, response.getProducer().getMemberClass());
        assertEquals(null, response.getProducer().getMemberCode());
        assertEquals(null, response.getProducer().getSubsystemCode());
        assertEquals("getRandom", response.getProducer().getServiceCode());
        assertEquals(null, response.getProducer().getServiceVersion());
        assertEquals("ID-1234567890", response.getId());
        assertEquals("EE1234567890", response.getUserId());
        assertEquals(ObjectType.CENTRALSERVICE, response.getProducer().getObjectType());
        assertEquals("1234567890", response.getRequestData());
        assertEquals("9876543210", response.getResponseData());

        assertEquals("SHA-512", response.getRequestHashAlgorithm());
        assertEquals("ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==", response.getRequestHash());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to member -> central service level service call
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test4() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"MEMBER\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode></sdsb:client><sdsb:service id:objectType=\"CENTRALSERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:serviceCode>getRandom</id:serviceCode></sdsb:service><sdsb:userId>EE1234567890</sdsb:userId><sdsb:id>ID-1234567890</sdsb:id><sdsb:requestHash algorithmId=\"SHA-512\">ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==</sdsb:requestHash></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890</data></request><response><data>9876543210</data></response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer();
        ServiceResponse<String, String> response = deserializer.deserialize(msg);


        assertEquals(SDSBInstance.FI, response.getConsumer().getSdsbInstance());
        assertEquals(MemberClass.GOV, response.getConsumer().getMemberClass());
        assertEquals("MEMBER1", response.getConsumer().getMemberCode());
        assertEquals(null, response.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.MEMBER, response.getConsumer().getObjectType());

        assertEquals(SDSBInstance.FI, response.getProducer().getSdsbInstance());
        assertEquals(null, response.getProducer().getMemberClass());
        assertEquals(null, response.getProducer().getMemberCode());
        assertEquals(null, response.getProducer().getSubsystemCode());
        assertEquals("getRandom", response.getProducer().getServiceCode());
        assertEquals(null, response.getProducer().getServiceVersion());
        assertEquals("ID-1234567890", response.getId());
        assertEquals("EE1234567890", response.getUserId());
        assertEquals(ObjectType.CENTRALSERVICE, response.getProducer().getObjectType());
        assertEquals("1234567890", response.getRequestData());
        assertEquals("9876543210", response.getResponseData());

        assertEquals("SHA-512", response.getRequestHashAlgorithm());
        assertEquals("ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==", response.getRequestHash());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to member -> service level service call. No service version.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test5() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"MEMBER\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode></sdsb:service><sdsb:userId>EE1234567890</sdsb:userId><sdsb:id>ID-1234567890</sdsb:id><sdsb:requestHash algorithmId=\"SHA-512\">ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==</sdsb:requestHash></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890</data></request><response><data>9876543210</data></response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer();
        ServiceResponse<String, String> response = deserializer.deserialize(msg);


        assertEquals(SDSBInstance.FI, response.getConsumer().getSdsbInstance());
        assertEquals(MemberClass.GOV, response.getConsumer().getMemberClass());
        assertEquals("MEMBER1", response.getConsumer().getMemberCode());
        assertEquals(null, response.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.MEMBER, response.getConsumer().getObjectType());

        assertEquals(SDSBInstance.FI, response.getProducer().getSdsbInstance());
        assertEquals(MemberClass.COM, response.getProducer().getMemberClass());
        assertEquals("MEMBER2", response.getProducer().getMemberCode());
        assertEquals("subsystem", response.getProducer().getSubsystemCode());
        assertEquals("getRandom", response.getProducer().getServiceCode());
        assertEquals(null, response.getProducer().getServiceVersion());
        assertEquals("ID-1234567890", response.getId());
        assertEquals("EE1234567890", response.getUserId());
        assertEquals(ObjectType.SERVICE, response.getProducer().getObjectType());
        assertEquals("1234567890", response.getRequestData());
        assertEquals("9876543210", response.getResponseData());

        assertEquals("SHA-512", response.getRequestHashAlgorithm());
        assertEquals("ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==", response.getRequestHash());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to subsystem -> service level service call. No service
     * subsystem and version.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test6() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:serviceCode>getRandom</id:serviceCode></sdsb:service><sdsb:userId>EE1234567890</sdsb:userId><sdsb:id>ID-1234567890</sdsb:id><sdsb:issue>Test issue</sdsb:issue><sdsb:requestHash algorithmId=\"SHA-512\">ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==</sdsb:requestHash></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890</data></request><response><data>9876543210</data></response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer();
        ServiceResponse<String, String> response = deserializer.deserialize(msg);


        assertEquals(SDSBInstance.FI, response.getConsumer().getSdsbInstance());
        assertEquals(MemberClass.GOV, response.getConsumer().getMemberClass());
        assertEquals("MEMBER1", response.getConsumer().getMemberCode());
        assertEquals("subsystem", response.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, response.getConsumer().getObjectType());

        assertEquals(SDSBInstance.FI, response.getProducer().getSdsbInstance());
        assertEquals(MemberClass.COM, response.getProducer().getMemberClass());
        assertEquals("MEMBER2", response.getProducer().getMemberCode());
        assertEquals(null, response.getProducer().getSubsystemCode());
        assertEquals("getRandom", response.getProducer().getServiceCode());
        assertEquals(null, response.getProducer().getServiceVersion());
        assertEquals("ID-1234567890", response.getId());
        assertEquals("EE1234567890", response.getUserId());
        assertEquals(ObjectType.SERVICE, response.getProducer().getObjectType());
        assertEquals("1234567890", response.getRequestData());
        assertEquals("9876543210", response.getResponseData());
        assertEquals("Test issue", response.getIssue());

        assertEquals("SHA-512", response.getRequestHashAlgorithm());
        assertEquals("ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==", response.getRequestHash());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to subsystem -> service level service call. No service
     * subsystem.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test7() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:userId>EE1234567890</sdsb:userId><sdsb:id>ID-1234567890</sdsb:id><sdsb:requestHash algorithmId=\"SHA-512\">ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==</sdsb:requestHash></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890</data></request><response><data>9876543210</data></response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer();
        ServiceResponse<String, String> response = deserializer.deserialize(msg);


        assertEquals(SDSBInstance.FI, response.getConsumer().getSdsbInstance());
        assertEquals(MemberClass.GOV, response.getConsumer().getMemberClass());
        assertEquals("MEMBER1", response.getConsumer().getMemberCode());
        assertEquals("subsystem", response.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, response.getConsumer().getObjectType());

        assertEquals(SDSBInstance.FI, response.getProducer().getSdsbInstance());
        assertEquals(MemberClass.COM, response.getProducer().getMemberClass());
        assertEquals("MEMBER2", response.getProducer().getMemberCode());
        assertEquals(null, response.getProducer().getSubsystemCode());
        assertEquals("getRandom", response.getProducer().getServiceCode());
        assertEquals("v1", response.getProducer().getServiceVersion());
        assertEquals("ID-1234567890", response.getId());
        assertEquals("EE1234567890", response.getUserId());
        assertEquals(ObjectType.SERVICE, response.getProducer().getObjectType());
        assertEquals("1234567890", response.getRequestData());
        assertEquals("9876543210", response.getResponseData());

        assertEquals("SHA-512", response.getRequestHashAlgorithm());
        assertEquals("ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==", response.getRequestHash());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to subsystem -> service level service call. No user id and
     * request hash.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test8() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:id>ID-1234567890</sdsb:id></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890</data></request><response><data>9876543210</data></response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer();
        ServiceResponse<String, String> response = deserializer.deserialize(msg);


        assertEquals(SDSBInstance.FI, response.getConsumer().getSdsbInstance());
        assertEquals(MemberClass.GOV, response.getConsumer().getMemberClass());
        assertEquals("MEMBER1", response.getConsumer().getMemberCode());
        assertEquals("subsystem", response.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, response.getConsumer().getObjectType());

        assertEquals(SDSBInstance.FI, response.getProducer().getSdsbInstance());
        assertEquals(MemberClass.COM, response.getProducer().getMemberClass());
        assertEquals("MEMBER2", response.getProducer().getMemberCode());
        assertEquals("subsystem", response.getProducer().getSubsystemCode());
        assertEquals("getRandom", response.getProducer().getServiceCode());
        assertEquals("v1", response.getProducer().getServiceVersion());
        assertEquals("ID-1234567890", response.getId());
        assertEquals(null, response.getUserId());
        assertEquals(ObjectType.SERVICE, response.getProducer().getObjectType());
        assertEquals("1234567890", response.getRequestData());
        assertEquals("9876543210", response.getResponseData());

        assertEquals(null, response.getRequestHashAlgorithm());
        assertEquals(null, response.getRequestHash());

        assertEquals(false, response.hasError());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to subsystem -> service level service call. Producer namespace
     * URI parameter used.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test9() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:userId>EE1234567890</sdsb:userId><sdsb:id>ID-1234567890</sdsb:id><sdsb:requestHash algorithmId=\"SHA-512\">ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==</sdsb:requestHash></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890</data></request><response><data>9876543210</data></response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer();
        ServiceResponse<String, String> response = deserializer.deserialize(msg, "http://producer.x-road.ee");


        assertEquals(SDSBInstance.FI, response.getConsumer().getSdsbInstance());
        assertEquals(MemberClass.GOV, response.getConsumer().getMemberClass());
        assertEquals("MEMBER1", response.getConsumer().getMemberCode());
        assertEquals("subsystem", response.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, response.getConsumer().getObjectType());

        assertEquals(SDSBInstance.FI, response.getProducer().getSdsbInstance());
        assertEquals(MemberClass.COM, response.getProducer().getMemberClass());
        assertEquals("MEMBER2", response.getProducer().getMemberCode());
        assertEquals("subsystem", response.getProducer().getSubsystemCode());
        assertEquals("getRandom", response.getProducer().getServiceCode());
        assertEquals("v1", response.getProducer().getServiceVersion());
        assertEquals("ID-1234567890", response.getId());
        assertEquals("EE1234567890", response.getUserId());
        assertEquals(ObjectType.SERVICE, response.getProducer().getObjectType());
        assertEquals("1234567890", response.getRequestData());
        assertEquals("9876543210", response.getResponseData());

        assertEquals("SHA-512", response.getRequestHashAlgorithm());
        assertEquals("ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==", response.getRequestHash());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to subsystem -> service level service call. Erroneous Producer
     * namespace URI parameter used.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test10() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:userId>EE1234567890</sdsb:userId><sdsb:id>ID-1234567890</sdsb:id><sdsb:requestHash algorithmId=\"SHA-512\">ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==</sdsb:requestHash></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890</data></request><response><data>9876543210</data></response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer();
        ServiceResponse<String, String> response = deserializer.deserialize(msg, "http://test.com");


        assertEquals(SDSBInstance.FI, response.getConsumer().getSdsbInstance());
        assertEquals(MemberClass.GOV, response.getConsumer().getMemberClass());
        assertEquals("MEMBER1", response.getConsumer().getMemberCode());
        assertEquals("subsystem", response.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, response.getConsumer().getObjectType());

        assertEquals(SDSBInstance.FI, response.getProducer().getSdsbInstance());
        assertEquals(MemberClass.COM, response.getProducer().getMemberClass());
        assertEquals("MEMBER2", response.getProducer().getMemberCode());
        assertEquals("subsystem", response.getProducer().getSubsystemCode());
        assertEquals("getRandom", response.getProducer().getServiceCode());
        assertEquals("v1", response.getProducer().getServiceVersion());
        assertEquals("ID-1234567890", response.getId());
        assertEquals("EE1234567890", response.getUserId());
        assertEquals(ObjectType.SERVICE, response.getProducer().getObjectType());
        assertEquals(null, response.getRequestData());
        assertEquals(null, response.getResponseData());

        assertEquals("SHA-512", response.getRequestHashAlgorithm());
        assertEquals("ZPbWPAOcJxzE81EmSk//R3DUQtqwMcuMMF9tsccJypdNcukzICQtlhhr3a/bTmexDrn8e/BrBVyl2t0ni/cUvw==", response.getRequestHash());
        assertEquals(false, response.hasError());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to subsystem -> service level service call. Technical error
     * with header.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testTechError1() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:id>ID-1234567890</sdsb:id></SOAP-ENV:Header><SOAP-ENV:Body><SOAP-ENV:Fault><faultcode>fault code</faultcode><faultstring>fault string</faultstring><faultactor>fault actor</faultactor><detail>fault details</detail></SOAP-ENV:Fault></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer();
        ServiceResponse<String, String> response = deserializer.deserialize(msg);


        assertEquals(SDSBInstance.FI, response.getConsumer().getSdsbInstance());
        assertEquals(MemberClass.GOV, response.getConsumer().getMemberClass());
        assertEquals("MEMBER1", response.getConsumer().getMemberCode());
        assertEquals("subsystem", response.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, response.getConsumer().getObjectType());

        assertEquals(SDSBInstance.FI, response.getProducer().getSdsbInstance());
        assertEquals(MemberClass.COM, response.getProducer().getMemberClass());
        assertEquals("MEMBER2", response.getProducer().getMemberCode());
        assertEquals("subsystem", response.getProducer().getSubsystemCode());
        assertEquals("getRandom", response.getProducer().getServiceCode());
        assertEquals("v1", response.getProducer().getServiceVersion());
        assertEquals("ID-1234567890", response.getId());
        assertEquals(null, response.getUserId());
        assertEquals(ObjectType.SERVICE, response.getProducer().getObjectType());
        assertEquals(null, response.getRequestData());
        assertEquals(null, response.getResponseData());

        assertEquals(null, response.getRequestHashAlgorithm());
        assertEquals(null, response.getRequestHash());

        assertEquals(true, response.hasError());
        assertEquals("fault code", response.getErrorMessage().getFaultCode());
        assertEquals("fault string", response.getErrorMessage().getFaultString());
        assertEquals("fault actor", response.getErrorMessage().getFaultActor());
        assertEquals("fault details", response.getErrorMessage().getDetail());
        assertEquals(ErrorMessageType.STANDARD_SOAP_ERROR_MESSAGE, response.getErrorMessage().getErrorMessageType());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to subsystem -> service level service call. Technical error
     * without header.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testTechError2() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header /><SOAP-ENV:Body><SOAP-ENV:Fault><faultcode>fault code</faultcode><faultstring>fault string</faultstring><faultactor>fault actor</faultactor><detail>fault details</detail></SOAP-ENV:Fault></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer();
        ServiceResponse<String, String> response = deserializer.deserialize(msg);

        assertEquals(null, response.getConsumer());
        assertEquals(null, response.getProducer());
        assertEquals(null, response.getId());
        assertEquals(null, response.getUserId());
        assertEquals(null, response.getRequestData());
        assertEquals(null, response.getResponseData());
        assertEquals(null, response.getRequestHashAlgorithm());
        assertEquals(null, response.getRequestHash());

        assertEquals(true, response.hasError());
        assertEquals("fault code", response.getErrorMessage().getFaultCode());
        assertEquals("fault string", response.getErrorMessage().getFaultString());
        assertEquals("fault actor", response.getErrorMessage().getFaultActor());
        assertEquals("fault details", response.getErrorMessage().getDetail());
        assertEquals(ErrorMessageType.STANDARD_SOAP_ERROR_MESSAGE, response.getErrorMessage().getErrorMessageType());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to subsystem -> service level service call. Technical error
     * with header.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testTechError3() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:id>ID-1234567890</sdsb:id></SOAP-ENV:Header><SOAP-ENV:Body><SOAP-ENV:Fault><faultcode>fault code</faultcode><faultstring>fault string</faultstring><faultactor>fault actor</faultactor><detail>fault details</detail></SOAP-ENV:Fault></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer();
        ServiceResponse<String, String> response = deserializer.deserialize(msg);

        assertEquals(SDSBInstance.FI, response.getConsumer().getSdsbInstance());
        assertEquals(MemberClass.GOV, response.getConsumer().getMemberClass());
        assertEquals("MEMBER1", response.getConsumer().getMemberCode());
        assertEquals("subsystem", response.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, response.getConsumer().getObjectType());

        assertEquals(SDSBInstance.FI, response.getProducer().getSdsbInstance());
        assertEquals(MemberClass.COM, response.getProducer().getMemberClass());
        assertEquals("MEMBER2", response.getProducer().getMemberCode());
        assertEquals("subsystem", response.getProducer().getSubsystemCode());
        assertEquals("getRandom", response.getProducer().getServiceCode());
        assertEquals("v1", response.getProducer().getServiceVersion());
        assertEquals("ID-1234567890", response.getId());
        assertEquals(null, response.getUserId());
        assertEquals(ObjectType.SERVICE, response.getProducer().getObjectType());
        assertEquals(null, response.getRequestData());
        assertEquals(null, response.getResponseData());

        assertEquals(null, response.getRequestHashAlgorithm());
        assertEquals(null, response.getRequestHash());

        assertEquals(true, response.hasError());
        assertEquals("fault code", response.getErrorMessage().getFaultCode());
        assertEquals("fault string", response.getErrorMessage().getFaultString());
        assertEquals("fault actor", response.getErrorMessage().getFaultActor());
        assertEquals("fault details", response.getErrorMessage().getDetail());
        assertEquals(ErrorMessageType.STANDARD_SOAP_ERROR_MESSAGE, response.getErrorMessage().getErrorMessageType());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to subsystem -> service level service call. Technical error
     * without header. Camel case error elements.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testTechError4() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header /><SOAP-ENV:Body><SOAP-ENV:Fault><faultCode>fault code</faultCode><faultString>fault string</faultString><faultActor>fault actor</faultActor><detail>fault details</detail></SOAP-ENV:Fault></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer();
        ServiceResponse<String, String> response = deserializer.deserialize(msg);

        assertEquals(null, response.getConsumer());
        assertEquals(null, response.getProducer());
        assertEquals(null, response.getId());
        assertEquals(null, response.getUserId());
        assertEquals(null, response.getRequestData());
        assertEquals(null, response.getResponseData());
        assertEquals(null, response.getRequestHashAlgorithm());
        assertEquals(null, response.getRequestHash());

        assertEquals(true, response.hasError());
        assertEquals("fault code", response.getErrorMessage().getFaultCode());
        assertEquals("fault string", response.getErrorMessage().getFaultString());
        assertEquals("fault actor", response.getErrorMessage().getFaultActor());
        assertEquals("fault details", response.getErrorMessage().getDetail());
        assertEquals(ErrorMessageType.STANDARD_SOAP_ERROR_MESSAGE, response.getErrorMessage().getErrorMessageType());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to subsystem -> service level service call. Technical error
     * without header. Camel case error elements. Detail with child elements.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testTechError5() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header /><SOAP-ENV:Body><SOAP-ENV:Fault><faultCode>fault code</faultCode><faultString>fault string</faultString><faultActor>fault actor</faultActor><detail><order>order element</order><confirmation>confirmation element</confirmation></detail></SOAP-ENV:Fault></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer2();
        ServiceResponse<String, String> response = deserializer.deserialize(msg);

        assertEquals(null, response.getConsumer());
        assertEquals(null, response.getProducer());
        assertEquals(null, response.getId());
        assertEquals(null, response.getUserId());
        assertEquals(null, response.getRequestData());
        assertEquals(null, response.getResponseData());
        assertEquals(null, response.getRequestHashAlgorithm());
        assertEquals(null, response.getRequestHash());

        assertEquals(true, response.hasError());
        assertEquals("fault code", response.getErrorMessage().getFaultCode());
        assertEquals("fault string", response.getErrorMessage().getFaultString());
        assertEquals("fault actor", response.getErrorMessage().getFaultActor());
        assertEquals("order element", ((Map) response.getErrorMessage().getDetail()).get("order"));
        assertEquals("confirmation element", ((Map) response.getErrorMessage().getDetail()).get("confirmation"));
        assertEquals(ErrorMessageType.STANDARD_SOAP_ERROR_MESSAGE, response.getErrorMessage().getErrorMessageType());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to subsystem -> service level service call. Technical error
     * without header. Camel case error elements. Detail missing.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testTechError6() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header /><SOAP-ENV:Body><SOAP-ENV:Fault><faultCode>fault code</faultCode><faultString>fault string</faultString><faultActor>fault actor</faultActor></SOAP-ENV:Fault></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer2();
        ServiceResponse<String, String> response = deserializer.deserialize(msg);

        assertEquals(null, response.getConsumer());
        assertEquals(null, response.getProducer());
        assertEquals(null, response.getId());
        assertEquals(null, response.getUserId());
        assertEquals(null, response.getRequestData());
        assertEquals(null, response.getResponseData());
        assertEquals(null, response.getRequestHashAlgorithm());
        assertEquals(null, response.getRequestHash());

        assertEquals(true, response.hasError());
        assertEquals("fault code", response.getErrorMessage().getFaultCode());
        assertEquals("fault string", response.getErrorMessage().getFaultString());
        assertEquals("fault actor", response.getErrorMessage().getFaultActor());
        assertEquals(null, response.getErrorMessage().getDetail());
        assertEquals(ErrorMessageType.STANDARD_SOAP_ERROR_MESSAGE, response.getErrorMessage().getErrorMessageType());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to subsystem -> service level service call. Non technical error.
     * Camel case element names.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testNonTechError1() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:id>ID-1234567890</sdsb:id></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890</data></request><response><faultCode>fault code</faultCode><faultString>fault string</faultString></response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer();
        ServiceResponse<String, String> response = deserializer.deserialize(msg);


        assertEquals(SDSBInstance.FI, response.getConsumer().getSdsbInstance());
        assertEquals(MemberClass.GOV, response.getConsumer().getMemberClass());
        assertEquals("MEMBER1", response.getConsumer().getMemberCode());
        assertEquals("subsystem", response.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, response.getConsumer().getObjectType());

        assertEquals(SDSBInstance.FI, response.getProducer().getSdsbInstance());
        assertEquals(MemberClass.COM, response.getProducer().getMemberClass());
        assertEquals("MEMBER2", response.getProducer().getMemberCode());
        assertEquals("subsystem", response.getProducer().getSubsystemCode());
        assertEquals("getRandom", response.getProducer().getServiceCode());
        assertEquals("v1", response.getProducer().getServiceVersion());
        assertEquals("ID-1234567890", response.getId());
        assertEquals(null, response.getUserId());
        assertEquals(ObjectType.SERVICE, response.getProducer().getObjectType());
        assertEquals("1234567890", response.getRequestData());
        assertEquals(null, response.getResponseData());

        assertEquals(null, response.getRequestHashAlgorithm());
        assertEquals(null, response.getRequestHash());

        assertEquals(true, response.hasError());
        assertEquals("fault code", response.getErrorMessage().getFaultCode());
        assertEquals("fault string", response.getErrorMessage().getFaultString());
        assertEquals(ErrorMessageType.NON_TECHNICAL_SOAP_ERROR_MESSAGE, response.getErrorMessage().getErrorMessageType());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to subsystem -> service level service call. Non technical error.
     * Lower case element names.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testNonTechError2() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:id>ID-1234567890</sdsb:id></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890</data></request><response><faultcode>fault code</faultcode><faultstring>fault string</faultstring></response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer();
        ServiceResponse<String, String> response = deserializer.deserialize(msg);


        assertEquals(SDSBInstance.FI, response.getConsumer().getSdsbInstance());
        assertEquals(MemberClass.GOV, response.getConsumer().getMemberClass());
        assertEquals("MEMBER1", response.getConsumer().getMemberCode());
        assertEquals("subsystem", response.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, response.getConsumer().getObjectType());

        assertEquals(SDSBInstance.FI, response.getProducer().getSdsbInstance());
        assertEquals(MemberClass.COM, response.getProducer().getMemberClass());
        assertEquals("MEMBER2", response.getProducer().getMemberCode());
        assertEquals("subsystem", response.getProducer().getSubsystemCode());
        assertEquals("getRandom", response.getProducer().getServiceCode());
        assertEquals("v1", response.getProducer().getServiceVersion());
        assertEquals("ID-1234567890", response.getId());
        assertEquals(null, response.getUserId());
        assertEquals(ObjectType.SERVICE, response.getProducer().getObjectType());
        assertEquals("1234567890", response.getRequestData());
        assertEquals(null, response.getResponseData());

        assertEquals(null, response.getRequestHashAlgorithm());
        assertEquals(null, response.getRequestHash());

        assertEquals(true, response.hasError());
        assertEquals("fault code", response.getErrorMessage().getFaultCode());
        assertEquals("fault string", response.getErrorMessage().getFaultString());
        assertEquals(ErrorMessageType.NON_TECHNICAL_SOAP_ERROR_MESSAGE, response.getErrorMessage().getErrorMessageType());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to subsystem -> service level service call. Non technical error.
     * Camel case element names. Fault code only.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testNonTechError3() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:id>ID-1234567890</sdsb:id></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890</data></request><response><faultCode>fault code</faultCode></response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer();
        ServiceResponse<String, String> response = deserializer.deserialize(msg);


        assertEquals(SDSBInstance.FI, response.getConsumer().getSdsbInstance());
        assertEquals(MemberClass.GOV, response.getConsumer().getMemberClass());
        assertEquals("MEMBER1", response.getConsumer().getMemberCode());
        assertEquals("subsystem", response.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, response.getConsumer().getObjectType());

        assertEquals(SDSBInstance.FI, response.getProducer().getSdsbInstance());
        assertEquals(MemberClass.COM, response.getProducer().getMemberClass());
        assertEquals("MEMBER2", response.getProducer().getMemberCode());
        assertEquals("subsystem", response.getProducer().getSubsystemCode());
        assertEquals("getRandom", response.getProducer().getServiceCode());
        assertEquals("v1", response.getProducer().getServiceVersion());
        assertEquals("ID-1234567890", response.getId());
        assertEquals(null, response.getUserId());
        assertEquals(ObjectType.SERVICE, response.getProducer().getObjectType());
        assertEquals("1234567890", response.getRequestData());
        assertEquals(null, response.getResponseData());

        assertEquals(null, response.getRequestHashAlgorithm());
        assertEquals(null, response.getRequestHash());

        assertEquals(true, response.hasError());
        assertEquals("fault code", response.getErrorMessage().getFaultCode());
        assertEquals(null, response.getErrorMessage().getFaultString());
        assertEquals(ErrorMessageType.NON_TECHNICAL_SOAP_ERROR_MESSAGE, response.getErrorMessage().getErrorMessageType());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to subsystem -> service level service call. Non technical error.
     * Camel case element names. Fault string only.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testNonTechError4() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:id>ID-1234567890</sdsb:id></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890</data></request><response><faultString>fault string</faultString></response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer();
        ServiceResponse<String, String> response = deserializer.deserialize(msg);


        assertEquals(SDSBInstance.FI, response.getConsumer().getSdsbInstance());
        assertEquals(MemberClass.GOV, response.getConsumer().getMemberClass());
        assertEquals("MEMBER1", response.getConsumer().getMemberCode());
        assertEquals("subsystem", response.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, response.getConsumer().getObjectType());

        assertEquals(SDSBInstance.FI, response.getProducer().getSdsbInstance());
        assertEquals(MemberClass.COM, response.getProducer().getMemberClass());
        assertEquals("MEMBER2", response.getProducer().getMemberCode());
        assertEquals("subsystem", response.getProducer().getSubsystemCode());
        assertEquals("getRandom", response.getProducer().getServiceCode());
        assertEquals("v1", response.getProducer().getServiceVersion());
        assertEquals("ID-1234567890", response.getId());
        assertEquals(null, response.getUserId());
        assertEquals(ObjectType.SERVICE, response.getProducer().getObjectType());
        assertEquals("1234567890", response.getRequestData());
        assertEquals(null, response.getResponseData());

        assertEquals(null, response.getRequestHashAlgorithm());
        assertEquals(null, response.getRequestHash());

        assertEquals(true, response.hasError());
        assertEquals(null, response.getErrorMessage().getFaultCode());
        assertEquals("fault string", response.getErrorMessage().getFaultString());
        assertEquals(ErrorMessageType.NON_TECHNICAL_SOAP_ERROR_MESSAGE, response.getErrorMessage().getErrorMessageType());
        assertEquals(true, response.getSoapMessage() != null);
    }

    /**
     * Response to subsystem -> service level service call. Non technical error.
     * Lower case element names. Fault code and fault string empty.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testNonTechError5() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:id>ID-1234567890</sdsb:id></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890</data></request><response><faultcode></faultcode><faultstring></faultstring></response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new TestResponseDeserializer();
        ServiceResponse<String, String> response = deserializer.deserialize(msg);


        assertEquals(SDSBInstance.FI, response.getConsumer().getSdsbInstance());
        assertEquals(MemberClass.GOV, response.getConsumer().getMemberClass());
        assertEquals("MEMBER1", response.getConsumer().getMemberCode());
        assertEquals("subsystem", response.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, response.getConsumer().getObjectType());

        assertEquals(SDSBInstance.FI, response.getProducer().getSdsbInstance());
        assertEquals(MemberClass.COM, response.getProducer().getMemberClass());
        assertEquals("MEMBER2", response.getProducer().getMemberCode());
        assertEquals("subsystem", response.getProducer().getSubsystemCode());
        assertEquals("getRandom", response.getProducer().getServiceCode());
        assertEquals("v1", response.getProducer().getServiceVersion());
        assertEquals("ID-1234567890", response.getId());
        assertEquals(null, response.getUserId());
        assertEquals(ObjectType.SERVICE, response.getProducer().getObjectType());
        assertEquals("1234567890", response.getRequestData());
        assertEquals(null, response.getResponseData());

        assertEquals(null, response.getRequestHashAlgorithm());
        assertEquals(null, response.getRequestHash());

        assertEquals(true, response.hasError());
        assertEquals("", response.getErrorMessage().getFaultCode());
        assertEquals("", response.getErrorMessage().getFaultString());
        assertEquals(ErrorMessageType.NON_TECHNICAL_SOAP_ERROR_MESSAGE, response.getErrorMessage().getErrorMessageType());
        assertEquals(true, response.getSoapMessage() != null);
    }

    private class TestResponseDeserializer extends AbstractResponseDeserializer<String, String> {

        protected String deserializeRequestData(Node requestNode) throws SOAPException {
            for (int i = 0; i < requestNode.getChildNodes().getLength(); i++) {
                if (requestNode.getChildNodes().item(i).getLocalName().equals("data")) {
                    return requestNode.getChildNodes().item(i).getTextContent();
                }
            }
            return null;
        }

        protected String deserializeResponseData(Node responseNode, SOAPMessage message) throws SOAPException {
            for (int i = 0; i < responseNode.getChildNodes().getLength(); i++) {
                if (responseNode.getChildNodes().item(i).getLocalName().equals("data")) {
                    return responseNode.getChildNodes().item(i).getTextContent();
                }
            }
            return null;
        }
    }

    private class TestResponseDeserializer2 extends TestResponseDeserializer {

        @Override
        protected Object deserializeFaultDetail(Node detailNode) {
            if (detailNode == null) {
                return null;
            }
            return SOAPHelper.nodesToMap(detailNode.getChildNodes());
        }
    }
}
