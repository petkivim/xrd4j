package com.pkrete.xrd4j.server.serializer;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.member.ConsumerMember;
import com.pkrete.xrd4j.common.member.ProducerMember;
import com.pkrete.xrd4j.common.message.ErrorMessage;
import com.pkrete.xrd4j.common.message.ServiceRequest;
import com.pkrete.xrd4j.common.message.ServiceResponse;
import com.pkrete.xrd4j.common.util.SOAPHelper;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import junit.framework.TestCase;

/**
 * Test cases for ServiceResponseSerializer class.
 *
 * @author Petteri Kivimäki
 */
public class ServiceResponseSerializerTest extends TestCase {

    /**
     * Subsystem level service call. No NS on request. NS prefix on response.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test1() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><request><data>Request data</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><xxprod:getRandomResponse xmlns:xxprod=\"http://foobar.x-road.ee/producer\"><xxprod:request><xxprod:data>Request data</xxprod:data></xxprod:request><xxprod:response><xxprod:data>Response data</xxprod:data></xxprod:response></xxprod:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("xxprod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, String> response = new ServiceResponse<String, String>(request.getConsumer(), request.getProducer(), request.getId());
        response.setResponseData("Response data");

        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Subsystem level service call. No NS on request. NS prefix on response.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test1WithWrappers() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><request><data>Request data</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><xxprod:getRandomResponse xmlns:xxprod=\"http://foobar.x-road.ee/producer\"><xxprod:request><xxprod:data>Request data</xxprod:data></xxprod:request><xxprod:response><xxprod:data>Response data</xxprod:data></xxprod:response></xxprod:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("xxprod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, String> response = new ServiceResponse<String, String>(request.getConsumer(), request.getProducer(), request.getId());
        response.setResponseData("Response data");

        request.setProcessingWrappers(true);
        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Subsystem level service call. No NS on request. NS prefix on response.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test1WithoutWrappers() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><data>Request data</data></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><xxprod:getRandomResponse xmlns:xxprod=\"http://foobar.x-road.ee/producer\"><xxprod:data>Response data</xxprod:data></xxprod:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("xxprod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, String> response = new ServiceResponse<String, String>(request.getConsumer(), request.getProducer(), request.getId());
        response.setResponseData("Response data");

        request.setProcessingWrappers(false);
        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Member level service call. No NS prefix on request. No NS prefix on
     * response, set with an empty string.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test2() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"MEMBER\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.5</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><getRandom xmlns=\"http://consumer.x-road.ee\"><request><data>Request data</data></request></getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"MEMBER\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.5</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><getRandomResponse xmlns=\"http://foobar.x-road.ee/producer\"><request><data>Request data</data></request><response><data>Response data</data></response></getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setProtocolVersion("4.5");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, String> response = new ServiceResponse<String, String>(request.getConsumer(), request.getProducer(), request.getId());
        response.setResponseData("Response data");

        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Calling service without subsystem. No NS on request. No NS prefix on
     * response, set with null value.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test3() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"MEMBER\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>6.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><request><data>Request data</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"MEMBER\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>6.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><getRandomResponse xmlns=\"http://foobar.x-road.ee/producer\"><request><data>Request data</data></request><response><data>Response data</data></response></getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "getRandom");
        producer.setServiceVersion("v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setProtocolVersion("6.0");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix(null);
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, String> response = new ServiceResponse<String, String>(request.getConsumer(), request.getProducer(), request.getId());
        response.setResponseData("Response data");

        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Subsystem level service call. Response data in a Map. NS on request.
     * No NS prefix on response.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test4() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:serviceCode>getRandom</id:serviceCode></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.5</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><ns1:request><ns1:data>Request data</ns1:data></ns1:request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:serviceCode>getRandom</id:serviceCode></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.5</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><getRandomResponse xmlns=\"http://foobar.x-road.ee/producer\"><request><data>Request data</data></request><response><field1>Field1</field1><field2>Field2</field2><field3>Field3</field3></response></getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "getRandom");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setProtocolVersion("4.5");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, Map> response = new ServiceResponse<String, Map>(request.getConsumer(), request.getProducer(), request.getId());
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("field1", "Field1");
        map.put("field2", "Field2");
        map.put("field3", "Field3");
        response.setResponseData(map);

        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl1();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Subsystem level service call. Response data in a Map. NS on request.
     * NS prefix on response.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test5() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><ns1:request><ns1:data>Request data</ns1:data></ns1:request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><prod:getRandomResponse xmlns:prod=\"http://foobar.x-road.ee/producer\"><prod:request><prod:data>Request data</prod:data></prod:request><prod:response><prod:field1>Field1</prod:field1><prod:field2>Field2</prod:field2><prod:field3>Field3</prod:field3></prod:response></prod:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("prod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, Map> response = new ServiceResponse<String, Map>(request.getConsumer(), request.getProducer(), request.getId());
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("field1", "Field1");
        map.put("field2", "Field2");
        map.put("field3", "Field3");
        response.setResponseData(map);

        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl1();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Subsystem level service call. Use producer namespace prefix in response.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test6() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><request><data>Request data</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><xxprod:getRandomResponse xmlns:xxprod=\"http://foobar.x-road.ee/producer\"><xxprod:request><xxprod:data>Request data</xxprod:data></xxprod:request><xxprod:response><xxprod:data>Response data</xxprod:data></xxprod:response></xxprod:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("xxprod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, String> response = new ServiceResponse<String, String>(request.getConsumer(), request.getProducer(), request.getId());
        response.setResponseData("Response data");

        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Subsystem level service call. Use namespace prefix in request's and
     * response's child elements.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test7() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>7.5</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><ns1:request><ns1:data>Request data</ns1:data></ns1:request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>7.5</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://consumer.x-road.ee\"><ns1:request><ns1:data>Request data</ns1:data></ns1:request><ns1:response><ns1:data>Response data</ns1:data></ns1:response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setProtocolVersion("7.5");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("ns1");
        request.getProducer().setNamespaceUrl("http://consumer.x-road.ee");

        ServiceResponse<String, String> response = new ServiceResponse<String, String>(request.getConsumer(), request.getProducer(), request.getId());
        response.setResponseData("Response data");

        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl2();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Subsystem level service call. Use namespace prefix in request's and
     * response's child elements. Different namespace and prefix in service
     * request and service response.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test8() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ts1:getRandom xmlns:ts1=\"http://test.x-road.ee\"><ts1:request><ts1:data>Request data</ts1:data></ts1:request></ts1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandomResponse xmlns:ns1=\"http://consumer.x-road.ee\"><ns1:request><ns1:data>Request data</ns1:data></ns1:request><ns1:response><ns1:data>Response data</ns1:data></ns1:response></ns1:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("ns1");
        request.getProducer().setNamespaceUrl("http://consumer.x-road.ee");

        ServiceResponse<String, String> response = new ServiceResponse<String, String>(request.getConsumer(), request.getProducer(), request.getId());
        response.setResponseData("Response data");

        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl2();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Subsystem level service call. Provider namespace is empty and serializer
     * returns SOAP Fault.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test9() throws XRd4JException, SOAPException {
        try {
            String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><request><data>Request data</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
            String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header/><SOAP-ENV:Body><SOAP-ENV:Fault><faultcode>SOAP-ENV:Server</faultcode><faultstring>Internal server error.</faultstring><faultactor/><detail/></SOAP-ENV:Fault></SOAP-ENV:Body></SOAP-ENV:Envelope>";
            ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
            ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
            ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
            request.setUserId("EE1234567890");
            request.setRequestData("Request data");
            request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

            request.getProducer().setNamespacePrefix("xxprod");
            request.getProducer().setNamespaceUrl("");

            ServiceResponse<String, String> response = new ServiceResponse<String, String>(request.getConsumer(), request.getProducer(), request.getId());
            response.setResponseData("Response data");

            ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl();
            SOAPMessage msg = serializer.serialize(response, request);

            assertEquals(correctResponse, SOAPHelper.toString(msg));
        } catch (XRd4JException ex) {
            // OK
        }
    }

    /**
     * Subsystem level service call. Provider namespace is null and serializer
     * returns SOAP Fault.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test10() throws XRd4JException, SOAPException {
        try {
            String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><request><data>Request data</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
            String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header/><SOAP-ENV:Body><SOAP-ENV:Fault><faultcode>SOAP-ENV:Server</faultcode><faultstring>Internal server error.</faultstring><faultactor/><detail/></SOAP-ENV:Fault></SOAP-ENV:Body></SOAP-ENV:Envelope>";
            ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
            ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
            ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
            request.setUserId("EE1234567890");
            request.setRequestData("Request data");
            request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

            request.getProducer().setNamespacePrefix("xxprod");
            request.getProducer().setNamespaceUrl(null);

            ServiceResponse<String, String> response = new ServiceResponse<String, String>(request.getConsumer(), request.getProducer(), request.getId());
            response.setResponseData("Response data");

            ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl();
            SOAPMessage msg = serializer.serialize(response, request);

            assertEquals(correctResponse, SOAPHelper.toString(msg));
        } catch (XRd4JException ex) {
            // OK
        }
    }

    /**
     * Subsystem level service call. No NS on request. NS prefix on response.
     * Request element with no children (<request></request>).
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test11() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><request></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><xxprod:getRandomResponse xmlns:xxprod=\"http://foobar.x-road.ee/producer\"><xxprod:request/><xxprod:response><xxprod:data>Response data</xxprod:data></xxprod:response></xxprod:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("xxprod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, String> response = new ServiceResponse<String, String>(request.getConsumer(), request.getProducer(), request.getId());
        response.setResponseData("Response data");

        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Subsystem level service call. No NS on request. NS prefix on response.
     * Request element with no children (<request/>).
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test12() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><request/></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><xxprod:getRandomResponse xmlns:xxprod=\"http://foobar.x-road.ee/producer\"><xxprod:request/><xxprod:response><xxprod:data>Response data</xxprod:data></xxprod:response></xxprod:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("xxprod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, String> response = new ServiceResponse<String, String>(request.getConsumer(), request.getProducer(), request.getId());
        response.setResponseData("Response data");

        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Subsystem level service call. No NS on request. NS prefix on response.
     * Request element missing.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test13() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"/></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><xxprod:getRandomResponse xmlns:xxprod=\"http://foobar.x-road.ee/producer\"><xxprod:request/><xxprod:response><xxprod:data>Response data</xxprod:data></xxprod:response></xxprod:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("xxprod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, String> response = new ServiceResponse<String, String>(request.getConsumer(), request.getProducer(), request.getId());
        response.setResponseData("Response data");

        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Subsystem level service call. No NS on request. Multiple NSs prefix on
     * response.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test14() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><request><data>Request data</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><xxprod:getRandomResponse xmlns:xxprod=\"http://foobar.x-road.ee/producer\"><xxprod:request><xxprod:data>Request data</xxprod:data></xxprod:request><xxprod:response><ns1:responseData xmlns:ns1=\"http://ns1.com\" xmlns:ns2=\"http://ns2.com\"><ns1:data>Response data</ns1:data><ns2:data2>Response data 2</ns2:data2></ns1:responseData></xxprod:response></xxprod:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("xxprod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, SOAPElement> response = new ServiceResponse<String, SOAPElement>(request.getConsumer(), request.getProducer(), request.getId());
        response.setResponseData(SOAPHelper.xmlStrToSOAPElement("<ns1:responseData xmlns:ns1=\"http://ns1.com\" xmlns:ns2=\"http://ns2.com\"><ns1:data>Response data</ns1:data><ns2:data2>Response data 2</ns2:data2></ns1:responseData>"));
        response.setForceNamespaceToResponseChildren(false);

        ServiceResponseSerializer serializer = new XMLServiceResponseSerializer();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Subsystem level service call. No NS on request. Multiple NSs prefix on
     * response. No NS on response element.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test15() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><request><data>Request data</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><xxprod:getRandomResponse xmlns:xxprod=\"http://foobar.x-road.ee/producer\"><xxprod:request><xxprod:data>Request data</xxprod:data></xxprod:request><response><ns1:responseData xmlns:ns1=\"http://ns1.com\" xmlns:ns2=\"http://ns2.com\"><ns1:data>Response data</ns1:data><ns2:data2>Response data 2</ns2:data2></ns1:responseData></response></xxprod:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("xxprod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, SOAPElement> response = new ServiceResponse<String, SOAPElement>(request.getConsumer(), request.getProducer(), request.getId());
        response.setResponseData(SOAPHelper.xmlStrToSOAPElement("<ns1:responseData xmlns:ns1=\"http://ns1.com\" xmlns:ns2=\"http://ns2.com\"><ns1:data>Response data</ns1:data><ns2:data2>Response data 2</ns2:data2></ns1:responseData>"));
        response.setAddNamespaceToResponse(false);
        response.setForceNamespaceToResponseChildren(false);

        ServiceResponseSerializer serializer = new XMLServiceResponseSerializer();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Subsystem level service call. No NS on request. Multiple NSs prefix on
     * response. No NS on response element. No NS on service response.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test16() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><request><data>Request data</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><getRandomResponse><xxprod:request xmlns:xxprod=\"http://foobar.x-road.ee/producer\"><xxprod:data>Request data</xxprod:data></xxprod:request><response><ns1:responseData xmlns:ns1=\"http://ns1.com\" xmlns:ns2=\"http://ns2.com\"><ns1:data>Response data</ns1:data><ns2:data2>Response data 2</ns2:data2></ns1:responseData></response></getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("xxprod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, SOAPElement> response = new ServiceResponse<String, SOAPElement>(request.getConsumer(), request.getProducer(), request.getId());
        response.setResponseData(SOAPHelper.xmlStrToSOAPElement("<ns1:responseData xmlns:ns1=\"http://ns1.com\" xmlns:ns2=\"http://ns2.com\"><ns1:data>Response data</ns1:data><ns2:data2>Response data 2</ns2:data2></ns1:responseData>"));
        response.setAddNamespaceToServiceResponse(false);
        response.setAddNamespaceToResponse(false);
        response.setForceNamespaceToResponseChildren(false);

        ServiceResponseSerializer serializer = new XMLServiceResponseSerializer();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Subsystem level service call. No NS on request. Multiple NSs prefix on
     * response. No NS on response element. No NS on service response's request
     * element. No NS on service response.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test17() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><request><data>Request data</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><getRandomResponse><request><data>Request data</data></request><response><ns1:responseData xmlns:ns1=\"http://ns1.com\" xmlns:ns2=\"http://ns2.com\"><ns1:data>Response data</ns1:data><ns2:data2>Response data 2</ns2:data2></ns1:responseData></response></getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("xxprod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, SOAPElement> response = new ServiceResponse<String, SOAPElement>(request.getConsumer(), request.getProducer(), request.getId());
        response.setResponseData(SOAPHelper.xmlStrToSOAPElement("<ns1:responseData xmlns:ns1=\"http://ns1.com\" xmlns:ns2=\"http://ns2.com\"><ns1:data>Response data</ns1:data><ns2:data2>Response data 2</ns2:data2></ns1:responseData>"));
        response.setAddNamespaceToServiceResponse(false);
        response.setAddNamespaceToRequest(false);
        response.setAddNamespaceToResponse(false);
        response.setForceNamespaceToResponseChildren(false);

        ServiceResponseSerializer serializer = new XMLServiceResponseSerializer();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Subsystem level service call. NS on request. Multiple NSs prefix on
     * response. No NS on response element. No NS on service response's request
     * element. No NS on service response.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test18() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns:getRandom xmlns:ns=\"http://consumer.x-road.ee\"><ns:request><ns:data>Request data</ns:data></ns:request></ns:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><getRandomResponse><ns:request xmlns:ns=\"http://consumer.x-road.ee\"><ns:data>Request data</ns:data></ns:request><response><ns1:responseData xmlns:ns1=\"http://ns1.com\" xmlns:ns2=\"http://ns2.com\"><ns1:data>Response data</ns1:data><ns2:data2>Response data 2</ns2:data2></ns1:responseData></response></getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("xxprod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, SOAPElement> response = new ServiceResponse<String, SOAPElement>(request.getConsumer(), request.getProducer(), request.getId());
        response.setResponseData(SOAPHelper.xmlStrToSOAPElement("<ns1:responseData xmlns:ns1=\"http://ns1.com\" xmlns:ns2=\"http://ns2.com\"><ns1:data>Response data</ns1:data><ns2:data2>Response data 2</ns2:data2></ns1:responseData>"));
        response.setAddNamespaceToServiceResponse(false);
        response.setAddNamespaceToRequest(false);
        response.setAddNamespaceToResponse(false);
        response.setForceNamespaceToResponseChildren(false);

        ServiceResponseSerializer serializer = new XMLServiceResponseSerializer();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * SOAP Fault with all the elements.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testSOAPFault1() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><request><data>Request data</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header/><SOAP-ENV:Body><SOAP-ENV:Fault><faultcode>Fault code</faultcode><faultstring>Fault string</faultstring><faultactor>Fault actor</faultactor><detail>Fault detail</detail></SOAP-ENV:Fault></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("xxprod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, String> response = new ServiceResponse<String, String>();
        response.setResponseData("Response data");

        ErrorMessage errorMsg = new ErrorMessage("Fault code", "Fault string", "Fault actor", "Fault detail");
        response.setErrorMessage(errorMsg);

        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * SOAP Fault with empty elements.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testSOAPFault2() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.5</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><request><data>Request data</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header/><SOAP-ENV:Body><SOAP-ENV:Fault><faultcode/><faultstring/><faultactor/><detail/></SOAP-ENV:Fault></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setProtocolVersion("4.5");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("xxprod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, String> response = new ServiceResponse<String, String>();
        response.setResponseData("Response data");

        ErrorMessage errorMsg = new ErrorMessage("", "", "", "");
        response.setErrorMessage(errorMsg);

        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * SOAP Fault with detail null.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testSOAPFault3() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><request><data>Request data</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header/><SOAP-ENV:Body><SOAP-ENV:Fault><faultcode>Fault code</faultcode><faultstring>Fault string</faultstring><faultactor>Fault actor</faultactor></SOAP-ENV:Fault></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("xxprod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, String> response = new ServiceResponse<String, String>();
        response.setResponseData("Response data");

        ErrorMessage errorMsg = new ErrorMessage("Fault code", "Fault string", "Fault actor", null);
        response.setErrorMessage(errorMsg);

        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * SOAP Fault with complex detail element.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testSOAPFault4() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><request><data>Request data</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header/><SOAP-ENV:Body><SOAP-ENV:Fault><faultcode>Fault code</faultcode><faultstring>Fault string</faultstring><faultactor>Fault actor</faultactor><detail><field1>Field1</field1><field2>Field2</field2></detail></SOAP-ENV:Fault></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("xxprod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, String> response = new ServiceResponse<String, String>();
        response.setResponseData("Response data");

        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("field1", "Field1");
        map.put("field2", "Field2");
        ErrorMessage errorMsg = new ErrorMessage("Fault code", "Fault string", "Fault actor", map);
        response.setErrorMessage(errorMsg);

        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl2();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * SOAP Fault with null request.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testSOAPFault5() throws XRd4JException, SOAPException {
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header/><SOAP-ENV:Body><SOAP-ENV:Fault><faultcode>Fault code</faultcode><faultstring>Fault string</faultstring><faultactor>Fault actor</faultactor><detail>Fault detail</detail></SOAP-ENV:Fault></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ServiceResponse<String, String> response = new ServiceResponse<String, String>();
        ErrorMessage errorMsg = new ErrorMessage("Fault code", "Fault string", "Fault actor", "Fault detail");
        response.setErrorMessage(errorMsg);

        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl();
        SOAPMessage msg = serializer.serialize(response, null);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Non-technical SOAP error.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testSOAPNonTechErr1() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.5</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><request><data>Request data</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.5</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><xxprod:getRandomResponse xmlns:xxprod=\"http://foobar.x-road.ee/producer\"><xxprod:request><xxprod:data>Request data</xxprod:data></xxprod:request><xxprod:response><faultcode>Fault code</faultcode><faultstring>Fault string</faultstring></xxprod:response></xxprod:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setProtocolVersion("4.5");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("xxprod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, String> response = new ServiceResponse<String, String>(request.getConsumer(), request.getProducer(), request.getId());
        response.setResponseData("Response data");

        ErrorMessage errorMsg = new ErrorMessage("Fault code", "Fault string");
        response.setErrorMessage(errorMsg);

        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Non-technical SOAP error with empty values.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testSOAPNonTechErr2() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>6.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><request><data>Request data</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>6.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><xxprod:getRandomResponse xmlns:xxprod=\"http://foobar.x-road.ee/producer\"><xxprod:request><xxprod:data>Request data</xxprod:data></xxprod:request><xxprod:response><faultcode/><faultstring/></xxprod:response></xxprod:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setProtocolVersion("6.0");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("xxprod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, String> response = new ServiceResponse<String, String>(request.getConsumer(), request.getProducer(), request.getId());
        response.setResponseData("Response data");

        ErrorMessage errorMsg = new ErrorMessage("", "");
        response.setErrorMessage(errorMsg);

        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    /**
     * Non-technical SOAP error with null values. Response data null.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testSOAPNonTechErr3() throws XRd4JException, SOAPException {
        String requestStr = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><request><data>Request data</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String correctResponse = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><xxprod:getRandomResponse xmlns:xxprod=\"http://foobar.x-road.ee/producer\"><xxprod:request><xxprod:data>Request data</xxprod:data></xxprod:request><xxprod:response/></xxprod:getRandomResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("Request data");
        request.setSoapMessage(SOAPHelper.toSOAP(requestStr));

        request.getProducer().setNamespacePrefix("xxprod");
        request.getProducer().setNamespaceUrl("http://foobar.x-road.ee/producer");

        ServiceResponse<String, String> response = new ServiceResponse<String, String>(request.getConsumer(), request.getProducer(), request.getId());
        response.setResponseData(null);

        ErrorMessage errorMsg = new ErrorMessage(null, null);
        response.setErrorMessage(errorMsg);

        ServiceResponseSerializer serializer = new ServiceResponseSerializerImpl();
        SOAPMessage msg = serializer.serialize(response, request);

        assertEquals(correctResponse, SOAPHelper.toString(msg));
    }

    private class ServiceResponseSerializerImpl extends AbstractServiceResponseSerializer {

        public void serializeResponse(ServiceResponse response, SOAPElement soapResponse, SOAPEnvelope envelope) throws SOAPException {
            SOAPElement data = soapResponse.addChildElement(envelope.createName("data"));
            data.addTextNode((String) response.getResponseData());
        }
    }

    private class ServiceResponseSerializerImpl1 extends AbstractServiceResponseSerializer {

        public void serializeResponse(ServiceResponse response, SOAPElement soapResponse, SOAPEnvelope envelope) throws SOAPException {
            for (String key : ((Map<String, String>) response.getResponseData()).keySet()) {
                SOAPElement element = soapResponse.addChildElement(key);
                element.addTextNode(((Map<String, String>) response.getResponseData()).get(key));
            }
        }
    }

    private class XMLServiceResponseSerializer extends AbstractServiceResponseSerializer {

        @Override
        public void serializeResponse(ServiceResponse response, SOAPElement soapResponse, SOAPEnvelope envelope) throws SOAPException {
            SOAPElement data = soapResponse.addChildElement((SOAPElement) response.getResponseData());
        }
    }

    private class ServiceResponseSerializerImpl2 extends AbstractServiceResponseSerializer {

        public void serializeResponse(ServiceResponse response, SOAPElement soapResponse, SOAPEnvelope envelope) throws SOAPException {
            SOAPElement data = soapResponse.addChildElement("data", "ns1");
            data.addTextNode((String) response.getResponseData());
        }

        @Override
        protected void serializeSOAPFaultDetail(ErrorMessage errorMessage, SOAPElement faultDetail) throws SOAPException {
            for (String key : ((Map<String, String>) errorMessage.getDetail()).keySet()) {
                SOAPElement element = faultDetail.addChildElement(key);
                element.addTextNode(((Map<String, String>) errorMessage.getDetail()).get(key));
            }
        }
    }
}
