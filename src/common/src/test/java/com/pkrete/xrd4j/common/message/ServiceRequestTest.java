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
package com.pkrete.xrd4j.common.message;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.member.ConsumerMember;
import com.pkrete.xrd4j.common.member.MemberClass;
import com.pkrete.xrd4j.common.member.ProducerMember;
import com.pkrete.xrd4j.common.member.SDSBInstance;
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

    /**
     * Set up instance variables used in test cases.
     * @throws Exception 
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.consumer = new ConsumerMember(SDSBInstance.FI, MemberClass.COM, "12345-5", "system");
        this.producer = new ProducerMember(SDSBInstance.FI, MemberClass.GOV, "12345-6", "system", "TestService", "v1");
    }

    /**
     * Test for toString method.
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testToString() throws XRd4JException {
        String id = MessageHelper.generateId();
        ServiceRequest request = new ServiceRequest(consumer, producer, "12345");
        assertEquals("12345", request.toString());
        request = new ServiceRequest(consumer, producer, id);
        assertEquals(id, request.toString());
        ServiceRequestTest.assertFalse(request.toString().equals(id + "1"));

    }

    /**
     * Test for equals method.
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testEquals() throws XRd4JException {
        String id = MessageHelper.generateId();
        assertEquals(new ServiceRequest(consumer, producer, "12345"), new ServiceRequest(consumer, producer, "12345"));
        assertEquals(new ServiceRequest(consumer, producer, id), new ServiceRequest(consumer, producer, id));
        ServiceRequestTest.assertFalse(new ServiceRequest(consumer, producer, MessageHelper.generateId()).equals(new ServiceRequest(consumer, producer, MessageHelper.generateId())));
    }

    /**
     * Test for ServiceRequest constructor. Consumer is null.
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
