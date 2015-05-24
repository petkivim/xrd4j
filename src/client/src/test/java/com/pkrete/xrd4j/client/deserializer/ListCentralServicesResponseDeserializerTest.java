package com.pkrete.xrd4j.client.deserializer;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.member.ObjectType;
import com.pkrete.xrd4j.common.member.ProducerMember;
import java.util.List;
import javax.xml.soap.SOAPException;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

/**
 * Test cases for ListCentralServicesResponseDeserializer class.
 *
 * @author Petteri Kivimäki
 */
public class ListCentralServicesResponseDeserializerTest extends TestCase {

    /**
     * Central service list with 1 service
     *
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test1() throws XRd4JException, SOAPException {
        String soapString = "<?xml version=\"1.0\"?><xrd:centralServiceList xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\" xmlns:id=\"http://x-road.eu/xsd/identifiers\"><xrd:centralService id:objectType=\"CENTRALSERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:serviceCode>testService</id:serviceCode></xrd:centralService></xrd:centralServiceList>";
        List<ProducerMember> list = new ListCentralServicesResponseDeserializer().deserializeProducerList(soapString);

        assertEquals("FI", list.get(0).getXRoadInstance());
        assertEquals(null, list.get(0).getMemberClass());
        assertEquals(null, list.get(0).getMemberCode());
        assertEquals(null, list.get(0).getSubsystemCode());
        assertEquals("testService", list.get(0).getServiceCode());
        assertEquals(null, list.get(0).getSubsystemCode());
        assertEquals(ObjectType.CENTRALSERVICE, list.get(0).getObjectType());
    }

    /**
     * Central service list with 3 services
     *
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test2() throws XRd4JException, SOAPException {
        String soapString = "<?xml version=\"1.0\"?><xrd:centralServiceList xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\" xmlns:id=\"http://x-road.eu/xsd/identifiers\"><xrd:centralService id:objectType=\"CENTRALSERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:serviceCode>testService</id:serviceCode></xrd:centralService><xrd:centralService id:objectType=\"CENTRALSERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:serviceCode>demo</id:serviceCode></xrd:centralService><xrd:centralService id:objectType=\"CENTRALSERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:serviceCode>service</id:serviceCode></xrd:centralService></xrd:centralServiceList>";
        List<ProducerMember> list = new ListCentralServicesResponseDeserializer().deserializeProducerList(soapString);

        assertEquals("FI", list.get(0).getXRoadInstance());
        assertEquals(null, list.get(0).getMemberClass());
        assertEquals(null, list.get(0).getMemberCode());
        assertEquals(null, list.get(0).getSubsystemCode());
        assertEquals("testService", list.get(0).getServiceCode());
        assertEquals(null, list.get(0).getSubsystemCode());
        assertEquals(ObjectType.CENTRALSERVICE, list.get(0).getObjectType());

        assertEquals("FI", list.get(1).getXRoadInstance());
        assertEquals(null, list.get(1).getMemberClass());
        assertEquals(null, list.get(1).getMemberCode());
        assertEquals(null, list.get(1).getSubsystemCode());
        assertEquals("demo", list.get(1).getServiceCode());
        assertEquals(null, list.get(1).getSubsystemCode());
        assertEquals(ObjectType.CENTRALSERVICE, list.get(1).getObjectType());

        assertEquals("FI", list.get(2).getXRoadInstance());
        assertEquals(null, list.get(2).getMemberClass());
        assertEquals(null, list.get(2).getMemberCode());
        assertEquals(null, list.get(2).getSubsystemCode());
        assertEquals("service", list.get(2).getServiceCode());
        assertEquals(null, list.get(2).getSubsystemCode());
        assertEquals(ObjectType.CENTRALSERVICE, list.get(2).getObjectType());
    }

    /**
     * Central service list with 0 services
     *
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test3() throws XRd4JException, SOAPException {
        String soapString = "<?xml version=\"1.0\"?><xrd:centralServiceList xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\" xmlns:id=\"http://x-road.eu/xsd/identifiers\"></xrd:centralServiceList>";
        List<ProducerMember> list = new ListCentralServicesResponseDeserializer().deserializeProducerList(soapString);

        assertEquals(0, list.size());
    }

    /**
     * Central service list with 0 services
     *
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test4() throws XRd4JException, SOAPException {
        String soapString = "<?xml version=\"1.0\"?><xrd:centralServiceList xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\" xmlns:id=\"http://x-road.eu/xsd/identifiers\"/>";
        List<ProducerMember> list = new ListCentralServicesResponseDeserializer().deserializeProducerList(soapString);

        assertEquals(0, list.size());
    }
}
