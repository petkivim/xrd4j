package com.pkrete.xrd4j.common.message;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.member.ConsumerMember;
import com.pkrete.xrd4j.common.member.ProducerMember;
import com.pkrete.xrd4j.common.util.MessageHelper;
import junit.framework.TestCase;

/**
 * Test cases for ServiceResponse class.
 *
 * @author Petteri Kivimäki
 */
public class ServiceResponseTest extends TestCase {

    private ConsumerMember consumer;
    private ProducerMember producer;

    /**
     * Set up instance variables used in test cases.
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.consumer = new ConsumerMember("FI", "COM", "12345-5", "system");
        this.producer = new ProducerMember("FI", "GOV", "12345-6", "system", "TestService", "v1");
    }

    /**
     * Test for toString method.
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testToString() throws XRd4JException {
        String id = MessageHelper.generateId();
        ServiceResponse response = new ServiceResponse(consumer, producer, "12345");
        assertEquals("12345", response.toString());
        response = new ServiceResponse(consumer, producer, id);
        assertEquals(id, response.toString());
        ServiceResponseTest.assertFalse(response.toString().equals(id + "1"));
    }

    /**
     * Test for equals method.
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testEquals() throws XRd4JException {
        String id = MessageHelper.generateId();
        assertEquals(new ServiceResponse(consumer, producer, "12345"), new ServiceResponse(consumer, producer, "12345"));
        assertEquals(new ServiceResponse(consumer, producer, id), new ServiceResponse(consumer, producer, id));
        ServiceResponseTest.assertFalse(new ServiceResponse(consumer, producer, MessageHelper.generateId()).equals(new ServiceResponse(consumer, producer, MessageHelper.generateId())));
    }

    /**
     * Test for ServiceResponse constructor. Consumer is null.
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testException1() throws XRd4JException {
        try {
            ServiceResponse response = new ServiceResponse(null, producer, "12345");
            fail("Should not reach this");
        } catch (XRd4JException ex) {
            // OK
        }
    }

    /**
     * Test for ServiceResponse constructor. Producer is null.
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testException2() throws XRd4JException {
        try {
            ServiceResponse response = new ServiceResponse(consumer, null, "12345");
            fail("Should not reach this");
        } catch (XRd4JException ex) {
            // OK
        }
    }

    /**
     * Test for ServiceResponse constructor. Id is null.
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testException3() throws XRd4JException {
        try {
            ServiceResponse response = new ServiceResponse(consumer, producer, null);
            fail("Should not reach this");
        } catch (XRd4JException ex) {
            // OK
        }
    }

    /**
     * Test for ServiceResponse constructor. Id is empty.
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testException4() throws XRd4JException {
        try {
            ServiceResponse response = new ServiceResponse(consumer, producer, "");
            fail("Should not reach this");
        } catch (XRd4JException ex) {
            // OK
        }
    }
}
