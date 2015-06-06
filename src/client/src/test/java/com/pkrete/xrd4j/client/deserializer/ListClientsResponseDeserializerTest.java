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
        String soapString = "<?xml version=\"1.0\"?><xrd:clientList xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\" xmlns:id=\"http://x-road.eu/xsd/identifiers\"><xrd:member><xrd:id id:objectType=\"MEMBER\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>12345</id:memberCode></xrd:id><xrd:name>Test org</xrd:name></xrd:member><xrd:member><xrd:id id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI-DEV</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>54321</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:id><xrd:name>Another org</xrd:name></xrd:member></xrd:clientList>";
        List<ConsumerMember> list = new ListClientsResponseDeserializer().deserializeConsumerList(soapString);

        assertEquals("FI", list.get(0).getXRoadInstance());
        assertEquals("GOV", list.get(0).getMemberClass());
        assertEquals("12345", list.get(0).getMemberCode());
        assertEquals(null, list.get(0).getSubsystemCode());
        assertEquals(ObjectType.MEMBER, list.get(0).getObjectType());

        assertEquals("FI-DEV", list.get(1).getXRoadInstance());
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
        String soapString = "<?xml version=\"1.0\"?><xrd:clientList xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\" xmlns:id=\"http://x-road.eu/xsd/identifiers\"><xrd:member><xrd:id id:objectType=\"MEMBER\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>12345</id:memberCode></xrd:id><xrd:name>Org 1</xrd:name></xrd:member><xrd:member><xrd:id id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>54321</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:id><xrd:name>Org 2</xrd:name></xrd:member><xrd:member><xrd:id id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>PRI</id:memberClass><id:memberCode>00000-1</id:memberCode><id:subsystemCode>testSystem</id:subsystemCode></xrd:id><xrd:name>Org 3</xrd:name></xrd:member></xrd:clientList>";
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
     * Client list with 4 members (2 * MEMBER, 2 * SUBSYSTEM)
     *
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test3() throws XRd4JException, SOAPException {
        String soapString = "<?xml version=\"1.0\"?><ns2:clientList xmlns:ns1=\"http://x-road.eu/xsd/identifiers\" xmlns:ns2=\"http://x-road.eu/xsd/xroad.xsd\"><ns2:member><ns2:id ns1:objectType=\"MEMBER\"><ns1:xRoadInstance>FI-DEV63</ns1:xRoadInstance><ns1:memberClass>GOV</ns1:memberClass><ns1:memberCode>7654321-0</ns1:memberCode></ns2:id><ns2:name>Org 1</ns2:name></ns2:member><ns2:member><ns2:id ns1:objectType=\"MEMBER\"><ns1:xRoadInstance>FI-DEV63</ns1:xRoadInstance><ns1:memberClass>GOV</ns1:memberClass><ns1:memberCode>1234567-8</ns1:memberCode></ns2:id><ns2:name>Org 2</ns2:name></ns2:member><ns2:member><ns2:id ns1:objectType=\"SUBSYSTEM\"><ns1:xRoadInstance>FI-DEV63</ns1:xRoadInstance><ns1:memberClass>GOV</ns1:memberClass><ns1:memberCode>1234567-8</ns1:memberCode><ns1:subsystemCode>TestClient</ns1:subsystemCode></ns2:id><ns2:name>Org 2</ns2:name></ns2:member><ns2:member><ns2:id ns1:objectType=\"SUBSYSTEM\"><ns1:xRoadInstance>FI-DEV63</ns1:xRoadInstance><ns1:memberClass>GOV</ns1:memberClass><ns1:memberCode>1234567-8</ns1:memberCode><ns1:subsystemCode>TestService</ns1:subsystemCode></ns2:id><ns2:name>Org 2</ns2:name></ns2:member></ns2:clientList>";
        List<ConsumerMember> list = new ListClientsResponseDeserializer().deserializeConsumerList(soapString);

        assertEquals("FI-DEV63", list.get(0).getXRoadInstance());
        assertEquals("GOV", list.get(0).getMemberClass());
        assertEquals("7654321-0", list.get(0).getMemberCode());
        assertEquals(null, list.get(0).getSubsystemCode());
        assertEquals(ObjectType.MEMBER, list.get(0).getObjectType());

        assertEquals("FI-DEV63", list.get(1).getXRoadInstance());
        assertEquals("GOV", list.get(1).getMemberClass());
        assertEquals("1234567-8", list.get(1).getMemberCode());
        assertEquals(null, list.get(1).getSubsystemCode());
        assertEquals(ObjectType.MEMBER, list.get(1).getObjectType());

        assertEquals("FI-DEV63", list.get(2).getXRoadInstance());
        assertEquals("GOV", list.get(2).getMemberClass());
        assertEquals("1234567-8", list.get(2).getMemberCode());
        assertEquals("TestClient", list.get(2).getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, list.get(2).getObjectType());

        assertEquals("FI-DEV63", list.get(3).getXRoadInstance());
        assertEquals("GOV", list.get(3).getMemberClass());
        assertEquals("1234567-8", list.get(3).getMemberCode());
        assertEquals("TestService", list.get(3).getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, list.get(3).getObjectType());
    }

    /**
     * Client list with no members
     *
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test4() throws XRd4JException, SOAPException {
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
    public void test5() throws XRd4JException, SOAPException {
        String soapString = "<?xml version=\"1.0\"?><xrd:clientList xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\" xmlns:id=\"http://x-road.eu/xsd/identifiers\"/>";
        List<ConsumerMember> list = new ListClientsResponseDeserializer().deserializeConsumerList(soapString);

        assertEquals(0, list.size());
    }
}
