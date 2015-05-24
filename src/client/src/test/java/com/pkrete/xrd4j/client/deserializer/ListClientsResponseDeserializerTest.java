package com.pkrete.xrd4j.client.deserializer;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.member.ConsumerMember;
import com.pkrete.xrd4j.common.member.ObjectType;
import java.util.List;
import javax.xml.soap.SOAPException;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

/**
 * Test cases for ListClientsResponseDeserializer class.
 *
 * @author Petteri Kivimäki
 */
public class ListClientsResponseDeserializerTest extends TestCase {

    /**
     * Client list with 2 members (MEMBER, SUBSYSTEM)
     *
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test1() throws XRd4JException, SOAPException {
        String soapString = "<?xml version=\"1.0\"?><xrd:clientList xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\" xmlns:id=\"http://x-road.eu/xsd/identifiers\"><xrd:member id:objectType=\"MEMBER\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>12345</id:memberCode></xrd:member><xrd:member id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>54321</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:member></xrd:clientList>";
        List<ConsumerMember> list = new ListClientsResponseDeserializer().deserializeConsumerList(soapString);

        assertEquals("FI", list.get(0).getXRoadInstance());
        assertEquals("GOV", list.get(0).getMemberClass());
        assertEquals("12345", list.get(0).getMemberCode());
        assertEquals(null, list.get(0).getSubsystemCode());
        assertEquals(ObjectType.MEMBER, list.get(0).getObjectType());

        assertEquals("FI", list.get(1).getXRoadInstance());
        assertEquals("COM", list.get(1).getMemberClass());
        assertEquals("54321", list.get(1).getMemberCode());
        assertEquals("subsystem", list.get(1).getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, list.get(1).getObjectType());
    }

    /**
     * Client list with 3 members (MEMBER, 2 * SUBSYSTEM)
     *
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test2() throws XRd4JException, SOAPException {
        String soapString = "<?xml version=\"1.0\"?><xrd:clientList xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\" xmlns:id=\"http://x-road.eu/xsd/identifiers\"><xrd:member id:objectType=\"MEMBER\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>12345</id:memberCode></xrd:member><xrd:member id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>54321</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:member><xrd:member id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>PRI</id:memberClass><id:memberCode>00000-1</id:memberCode><id:subsystemCode>testSystem</id:subsystemCode></xrd:member></xrd:clientList>";
        List<ConsumerMember> list = new ListClientsResponseDeserializer().deserializeConsumerList(soapString);

        assertEquals("FI", list.get(0).getXRoadInstance());
        assertEquals("GOV", list.get(0).getMemberClass());
        assertEquals("12345", list.get(0).getMemberCode());
        assertEquals(null, list.get(0).getSubsystemCode());
        assertEquals(ObjectType.MEMBER, list.get(0).getObjectType());

        assertEquals("FI", list.get(1).getXRoadInstance());
        assertEquals("COM", list.get(1).getMemberClass());
        assertEquals("54321", list.get(1).getMemberCode());
        assertEquals("subsystem", list.get(1).getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, list.get(1).getObjectType());

        assertEquals("FI", list.get(2).getXRoadInstance());
        assertEquals("PRI", list.get(2).getMemberClass());
        assertEquals("00000-1", list.get(2).getMemberCode());
        assertEquals("testSystem", list.get(2).getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, list.get(2).getObjectType());
    }

    /**
     * Client list with no members
     *
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test3() throws XRd4JException, SOAPException {
        String soapString = "<?xml version=\"1.0\"?><xrd:clientList xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\" xmlns:id=\"http://x-road.eu/xsd/identifiers\"></xrd:clientList>";
        List<ConsumerMember> list = new ListClientsResponseDeserializer().deserializeConsumerList(soapString);

        assertEquals(0, list.size());
    }

    /**
     * Client list with no members
     *
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test4() throws XRd4JException, SOAPException {
        String soapString = "<?xml version=\"1.0\"?><xrd:clientList xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\" xmlns:id=\"http://x-road.eu/xsd/identifiers\"/>";
        List<ConsumerMember> list = new ListClientsResponseDeserializer().deserializeConsumerList(soapString);

        assertEquals(0, list.size());
    }
}
