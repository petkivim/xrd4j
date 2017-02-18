package com.pkrete.xrd4j.client.serializer;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.member.ConsumerMember;
import com.pkrete.xrd4j.common.member.ProducerMember;
import com.pkrete.xrd4j.common.member.SecurityServer;
import com.pkrete.xrd4j.common.message.ServiceRequest;
import com.pkrete.xrd4j.common.util.Constants;
import com.pkrete.xrd4j.common.util.MessageHelper;
import com.pkrete.xrd4j.common.util.SOAPHelper;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

/**
 * Test cases for DefaultServiceRequestSerializer class.
 *
 * @author Petteri Kivimäki
 */
public class DefaultServiceRequestSerializerTest extends TestCase {

    /**
     * allowedMethos : member - member level service call.
     *
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test1() throws XRd4JException, SOAPException {
        String id = MessageHelper.generateId();
        String correctRequest = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"MEMBER\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:serviceCode>allowedMethods</id:serviceCode></xrd:service><xrd:userId>user</xrd:userId><xrd:id>" + id + "</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><xrd:allowedMethods/></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", Constants.META_SERVICE_ALLOWED_METHODS);
        producer.setSubsystemCode(null);
        producer.setNamespacePrefix(Constants.NS_XRD_PREFIX);
        producer.setNamespaceUrl(Constants.NS_XRD_URL);
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, id);
        request.setUserId("user");

        ServiceRequestSerializer serializer = new DefaultServiceRequestSerializer();
        SOAPMessage msg = serializer.serialize(request);

        assertEquals(correctRequest, SOAPHelper.toString(msg));
    }

    /**
     * allowedMethos : member - subsystem level service call.
     *
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test2() throws XRd4JException, SOAPException {
        String id = MessageHelper.generateId();
        String correctRequest = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"MEMBER\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>service</id:subsystemCode><id:serviceCode>allowedMethods</id:serviceCode></xrd:service><xrd:userId>user</xrd:userId><xrd:id>" + id + "</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><xrd:allowedMethods/></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "service", Constants.META_SERVICE_ALLOWED_METHODS);
        producer.setNamespacePrefix(Constants.NS_XRD_PREFIX);
        producer.setNamespaceUrl(Constants.NS_XRD_URL);
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, id);
        request.setUserId("user");

        ServiceRequestSerializer serializer = new DefaultServiceRequestSerializer();
        SOAPMessage msg = serializer.serialize(request);

        assertEquals(correctRequest, SOAPHelper.toString(msg));
    }

    /**
     * listMethos : subsystem - member level service call.
     *
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test3() throws XRd4JException, SOAPException {
        String id = MessageHelper.generateId();
        String correctRequest = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>client</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:serviceCode>listMethods</id:serviceCode></xrd:service><xrd:userId>user</xrd:userId><xrd:id>" + id + "</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><xrd:listMethods/></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "client");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", Constants.META_SERVICE_LIST_METHODS, "v1");
        producer.setSubsystemCode(null);
        producer.setServiceVersion(null);
        producer.setNamespacePrefix(Constants.NS_XRD_PREFIX);
        producer.setNamespaceUrl(Constants.NS_XRD_URL);
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, id);
        request.setUserId("user");

        ServiceRequestSerializer serializer = new DefaultServiceRequestSerializer();
        SOAPMessage msg = serializer.serialize(request);

        assertEquals(correctRequest, SOAPHelper.toString(msg));
    }

    /**
     * listMethos : subsystem - subsystem level service call.
     *
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test4() throws XRd4JException, SOAPException {
        String id = MessageHelper.generateId();
        String correctRequest = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>client</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>service</id:subsystemCode><id:serviceCode>listMethods</id:serviceCode></xrd:service><xrd:userId>user</xrd:userId><xrd:id>" + id + "</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><xrd:listMethods/></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "client");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "service", Constants.META_SERVICE_LIST_METHODS);
        producer.setNamespacePrefix(Constants.NS_XRD_PREFIX);
        producer.setNamespaceUrl(Constants.NS_XRD_URL);
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, id);
        request.setUserId("user");

        ServiceRequestSerializer serializer = new DefaultServiceRequestSerializer();
        SOAPMessage msg = serializer.serialize(request);

        assertEquals(correctRequest, SOAPHelper.toString(msg));
    }

    /**
     * getSecurityServerMetrics.
     *
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test5() throws XRd4JException, SOAPException {
        String id = MessageHelper.generateId();
        String correctRequest = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>client</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>service</id:subsystemCode><id:serviceCode>getSecurityServerMetrics</id:serviceCode></xrd:service><xrd:securityServer id:objectType=\"SERVER\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:serverCode>server1</id:serverCode></xrd:securityServer><xrd:userId>user</xrd:userId><xrd:id>" + id + "</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><m:getSecurityServerMetrics xmlns:m=\"http://x-road.eu/xsd/monitoring\"/></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "client");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "service", Constants.ENV_MONITORING_GET_SECURITY_SERVER_METRICS);
        SecurityServer securityServer = new SecurityServer("FI", "COM", "MEMBER2", "server1");
        producer.setNamespacePrefix(Constants.NS_ENV_MONITORING_PREFIX);
        producer.setNamespaceUrl(Constants.NS_ENV_MONITORING_URL);
        ServiceRequest<String> request = new ServiceRequest<>(consumer, producer, id);
        request.setSecurityServer(securityServer);
        request.setUserId("user");

        ServiceRequestSerializer serializer = new DefaultServiceRequestSerializer();
        SOAPMessage msg = serializer.serialize(request);

        assertEquals(correctRequest, SOAPHelper.toString(msg));
    }
}
