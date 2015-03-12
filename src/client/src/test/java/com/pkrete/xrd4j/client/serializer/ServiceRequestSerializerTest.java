package com.pkrete.xrd4j.client.serializer;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.member.ConsumerMember;
import com.pkrete.xrd4j.common.member.ProducerMember;
import com.pkrete.xrd4j.common.message.ServiceRequest;
import com.pkrete.xrd4j.common.util.SOAPHelper;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import junit.framework.TestCase;

/**
 * Test cases for ServiceRequestSerializer class.
 *
 * @author Petteri Kivimäki
 */
public class ServiceRequestSerializerTest extends TestCase {

    /**
     * Subsystem level service call. No NS prefix.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test1() throws XRd4JException, SOAPException {
        String correctRequest = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:userId>EE1234567890</sdsb:userId><sdsb:id>1234567890</sdsb:id></SOAP-ENV:Header><SOAP-ENV:Body><getRandom xmlns=\"http://consumer.x-road.ee\"><request><data>1234567890</data></request></getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        producer.setNamespacePrefix("");
        producer.setNamespaceUrl("http://consumer.x-road.ee");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("1234567890");

        ServiceRequestSerializer serializer = new TestRequestSerializer();
        SOAPMessage msg = serializer.serialize(request);

        assertEquals(correctRequest, SOAPHelper.toString(msg));
    }

    /**
     * Member level service call. NS prefix set.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test2() throws XRd4JException, SOAPException {
        String correctRequest = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"MEMBER\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:userId>EE1234567890</sdsb:userId><sdsb:id>1234567890</sdsb:id></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><ns1:request><ns1:data>1234567890</ns1:data></ns1:request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        producer.setNamespacePrefix("ns1");
        producer.setNamespaceUrl("http://consumer.x-road.ee");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("1234567890");

        ServiceRequestSerializer serializer = new TestRequestSerializer();
        SOAPMessage msg = serializer.serialize(request);

        assertEquals(correctRequest, SOAPHelper.toString(msg));
    }

    /**
     * Calling service without subsystem. NS prefix set.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test3() throws XRd4JException, SOAPException {
        String correctRequest = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:userId>EE1234567890</sdsb:userId><sdsb:id>1234567890</sdsb:id></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><ns1:request><ns1:data>1234567890</ns1:data></ns1:request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "getRandom");
        producer.setNamespacePrefix("ns1");
        producer.setNamespaceUrl("http://consumer.x-road.ee");
        producer.setServiceVersion("v1");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("1234567890");

        ServiceRequestSerializer serializer = new TestRequestSerializer();
        SOAPMessage msg = serializer.serialize(request);

        assertEquals(correctRequest, SOAPHelper.toString(msg));
    }

    /**
     * Calling central service. No NS prefix.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test4() throws XRd4JException, SOAPException {
        String correctRequest = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"CENTRALSERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:serviceCode>getRandom</id:serviceCode></sdsb:service><sdsb:userId>EE1234567890</sdsb:userId><sdsb:id>1234567890</sdsb:id></SOAP-ENV:Header><SOAP-ENV:Body><getRandom xmlns=\"http://consumer.x-road.ee\"><request><data>1234567890</data></request></getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "getRandom");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        producer.setNamespacePrefix(null);
        producer.setNamespaceUrl("http://consumer.x-road.ee");
        request.setUserId("EE1234567890");
        request.setRequestData("1234567890");

        ServiceRequestSerializer serializer = new TestRequestSerializer();
        SOAPMessage msg = serializer.serialize(request);

        assertEquals(correctRequest, SOAPHelper.toString(msg));
    }

    /**
     * Data with special characters. NS prefix set.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test5() throws XRd4JException, SOAPException {
        String correctRequest = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:id>1234567890</sdsb:id></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><ns1:request><ns1:data>Test data. Special characters: äöå</ns1:data></ns1:request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        producer.setNamespacePrefix("ns1");
        producer.setNamespaceUrl("http://consumer.x-road.ee");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setRequestData("Test data. Special characters: äöå");

        ServiceRequestSerializer serializer = new TestRequestSerializer();
        SOAPMessage msg = serializer.serialize(request);

        assertEquals(correctRequest, SOAPHelper.toString(msg));
    }

    /**
     * Without consumer namespace prefix.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test6() throws XRd4JException, SOAPException {
        String correctRequest = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:userId>EE1234567890</sdsb:userId><sdsb:id>1234567890</sdsb:id></SOAP-ENV:Header><SOAP-ENV:Body><getRandom xmlns=\"http://consumer.x-road.ee\"><request><data>1234567890</data></request></getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        producer.setNamespaceUrl("http://consumer.x-road.ee");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("1234567890");

        ServiceRequestSerializer serializer = new TestRequestSerializer();
        SOAPMessage msg = serializer.serialize(request);

        assertEquals(correctRequest, SOAPHelper.toString(msg));
    }

    /**
     * Subsystem level service call. Use Consumer namespace with request.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test7() throws XRd4JException, SOAPException {
        String correctRequest = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:userId>EE1234567890</sdsb:userId><sdsb:id>1234567890</sdsb:id></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><ns1:request><ns1:data>1234567890</ns1:data></ns1:request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        producer.setNamespacePrefix("ns1");
        producer.setNamespaceUrl("http://consumer.x-road.ee");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("1234567890");

        ServiceRequestSerializer serializer = new TestRequestSerializer1();
        SOAPMessage msg = serializer.serialize(request);

        assertEquals(correctRequest, SOAPHelper.toString(msg));
    }

    /**
     * Member level service call.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test8() throws XRd4JException, SOAPException {
        String correctRequest = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"MEMBER\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:userId>EE1234567890</sdsb:userId><sdsb:id>1234567890</sdsb:id></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><ns1:request><ns1:data>1234567890</ns1:data></ns1:request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        producer.setNamespacePrefix("ns1");
        producer.setNamespaceUrl("http://consumer.x-road.ee");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("1234567890");

        ServiceRequestSerializer serializer = new TestRequestSerializer1();
        SOAPMessage msg = serializer.serialize(request);

        assertEquals(correctRequest, SOAPHelper.toString(msg));
    }

    /**
     * Subsystem level service call. Producer namespace and prefix already set
     * by the serializer implementation.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test9() throws XRd4JException, SOAPException {
        String correctRequest = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:userId>EE1234567890</sdsb:userId><sdsb:id>1234567890</sdsb:id></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><ns1:request><ns1:data>1234567890</ns1:data></ns1:request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        producer.setNamespacePrefix("ns1");
        producer.setNamespaceUrl("http://consumer.x-road.ee");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("1234567890");

        ServiceRequestSerializer serializer = new TestRequestSerializer1();
        SOAPMessage msg = serializer.serialize(request);

        assertEquals(correctRequest, SOAPHelper.toString(msg));
    }

    /**
     * Subsystem level service call. Wrong namespace and prefix already set
     * by the serializer implementation.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test10() throws XRd4JException, SOAPException {
        String correctRequest = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:userId>EE1234567890</sdsb:userId><sdsb:id>1234567890</sdsb:id></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://consumer.x-road.ee\"><ns1:request><ns1:data xmlns:ts=\"http://www.test.com/ns\">1234567890</ns1:data></ns1:request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        producer.setNamespacePrefix("ns1");
        producer.setNamespaceUrl("http://consumer.x-road.ee");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("1234567890");

        ServiceRequestSerializer serializer = new TestRequestSerializer2();
        SOAPMessage msg = serializer.serialize(request);

        assertEquals(correctRequest, SOAPHelper.toString(msg));
    }

    /**
     * Provider namespace null and serializer returns null.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test11() throws XRd4JException, SOAPException {
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        producer.setNamespacePrefix("");
        producer.setNamespaceUrl(null);
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("1234567890");

        ServiceRequestSerializer serializer = new TestRequestSerializer();
        SOAPMessage msg = serializer.serialize(request);
        assertEquals(null, msg);
    }

    /**
     * Provider namespace empty and serializer returns null.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test12() throws XRd4JException, SOAPException {
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        producer.setNamespacePrefix("");
        producer.setNamespaceUrl("");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");
        request.setRequestData("1234567890");

        ServiceRequestSerializer serializer = new TestRequestSerializer();
        SOAPMessage msg = serializer.serialize(request);
        assertEquals(null, msg);
    }

    /**
     * Subsystem level service call. No NS prefix.
     * Use DefaultServiceRequestSerializer.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test13() throws XRd4JException, SOAPException {
        String correctRequest = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:sdsb=\"http://x-road.eu/xsd/sdsb.xsd\"><SOAP-ENV:Header><sdsb:client id:objectType=\"SUBSYSTEM\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></sdsb:client><sdsb:service id:objectType=\"SERVICE\"><id:sdsbInstance>FI</id:sdsbInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></sdsb:service><sdsb:userId>EE1234567890</sdsb:userId><sdsb:id>1234567890</sdsb:id></SOAP-ENV:Header><SOAP-ENV:Body><getRandom xmlns=\"http://consumer.x-road.ee\"/></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        ConsumerMember consumer = new ConsumerMember("FI", "GOV", "MEMBER1", "subsystem");
        ProducerMember producer = new ProducerMember("FI", "COM", "MEMBER2", "subsystem", "getRandom", "v1");
        producer.setNamespacePrefix("");
        producer.setNamespaceUrl("http://consumer.x-road.ee");
        ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, "1234567890");
        request.setUserId("EE1234567890");

        ServiceRequestSerializer serializer = new DefaultServiceRequestSerializer();
        SOAPMessage msg = serializer.serialize(request);

        assertEquals(correctRequest, SOAPHelper.toString(msg));
    }

    private class TestRequestSerializer extends AbstractServiceRequestSerializer {

        protected void serializeRequest(ServiceRequest request, SOAPElement soapRequest, SOAPEnvelope envelope) throws SOAPException {
            SOAPElement data = soapRequest.addChildElement(envelope.createName("data"));
            data.addTextNode((String) request.getRequestData());
        }
    }

    private class TestRequestSerializer1 extends AbstractServiceRequestSerializer {

        protected void serializeRequest(ServiceRequest request, SOAPElement soapRequest, SOAPEnvelope envelope) throws SOAPException {
            SOAPElement data = soapRequest.addChildElement("data", request.getProducer().getNamespacePrefix());
            data.addTextNode((String) request.getRequestData());
        }
    }

    private class TestRequestSerializer2 extends AbstractServiceRequestSerializer {

        protected void serializeRequest(ServiceRequest request, SOAPElement soapRequest, SOAPEnvelope envelope) throws SOAPException {
            SOAPElement data = soapRequest.addChildElement("data", "ts", "http://www.test.com/ns");
            data.addTextNode((String) request.getRequestData());
        }
    }
}
