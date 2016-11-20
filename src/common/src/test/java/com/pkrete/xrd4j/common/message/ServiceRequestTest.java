package com.pkrete.xrd4j.common.message;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.member.ConsumerMember;
import com.pkrete.xrd4j.common.member.ProducerMember;
import com.pkrete.xrd4j.common.member.SecurityServer;
import com.pkrete.xrd4j.common.util.MessageHelper;
import junit.framework.TestCase;

/**
 * Test cases for ServiceRequest class.
 *
 * @author Petteri Kivimäki
 */
public class ServiceRequestTest extends TestCase {

    private ConsumerMember consumer;
    private ProducerMember producer;
    private SecurityServer securityServer;

    /**
     * Set up instance variables used in test cases.
     *
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.consumer = new ConsumerMember("FI", "COM", "12345-5", "system");
        this.producer = new ProducerMember("FI", "GOV", "12345-6", "system", "TestService", "v1");
        this.securityServer = new SecurityServer("FI", "GOV", "12345-6", "myserver");
    }

    /**
     * Test for toString method.
     *
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testToString() throws XRd4JException {
        String id = MessageHelper.generateId();
        ServiceRequest request = new ServiceRequest(consumer, producer, "12345");
        assertEquals("12345", request.toString());
        request = new ServiceRequest(consumer, producer, id);
        assertEquals(id, request.toString());
        ServiceRequestTest.assertFalse(request.toString().equals(id + "1"));
        assertEquals("4.0", request.getProtocolVersion());
        request.setProtocolVersion("5.0");
        assertEquals("5.0", request.getProtocolVersion());
    }

    /**
     * Test for equals method.
     *
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testEquals() throws XRd4JException {
        String id = MessageHelper.generateId();
        assertEquals(new ServiceRequest(consumer, producer, "12345"), new ServiceRequest(consumer, producer, "12345"));
        assertEquals(new ServiceRequest(consumer, producer, id), new ServiceRequest(consumer, producer, id));
        ServiceRequest req1 = new ServiceRequest(consumer, producer, id);
        req1.setSecurityServer(securityServer);
        ServiceRequest req2 = new ServiceRequest(consumer, producer, id);
        req2.setSecurityServer(securityServer);
        assertEquals(req1.getSecurityServer(), req2.getSecurityServer());
        this.securityServer = new SecurityServer("FI", "GOV", "12345-7", "myserver");
        req2.setSecurityServer(securityServer);
        ServiceRequestTest.assertFalse(req1.getSecurityServer().equals(req2.getSecurityServer()));
        ServiceRequestTest.assertFalse(new ServiceRequest(consumer, producer, MessageHelper.generateId()).equals(new ServiceRequest(consumer, producer, MessageHelper.generateId())));
    }

    /**
     * Test for ServiceRequest constructor. Consumer is null.
     *
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testException1() throws XRd4JException {
        try {
            ServiceRequest request = new ServiceRequest(null, producer, "12345");
            fail("Should not reach this");
        } catch (XRd4JException ex) {
            // OK
        }
    }

    /**
     * Test for ServiceRequest constructor. Producer is null.
     *
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testException2() throws XRd4JException {
        try {
            ServiceRequest request = new ServiceRequest(consumer, null, "12345");
            fail("Should not reach this");
        } catch (XRd4JException ex) {
            // OK
        }
    }

    /**
     * Test for ServiceRequest constructor. Id is null.
     *
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testException3() throws XRd4JException {
        try {
            ServiceRequest request = new ServiceRequest(consumer, producer, null);
            fail("Should not reach this");
        } catch (XRd4JException ex) {
            // OK
        }
    }

    /**
     * Test for ServiceRequest constructor. Id is empty.
     *
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testException4() throws XRd4JException {
        try {
            ServiceRequest request = new ServiceRequest(consumer, producer, "");
            fail("Should not reach this");
        } catch (XRd4JException ex) {
            // OK
        }
    }
}
