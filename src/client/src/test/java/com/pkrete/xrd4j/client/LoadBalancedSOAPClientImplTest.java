package com.pkrete.xrd4j.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.soap.SOAPException;
import junit.framework.TestCase;

/**
 * Test cases for LoadBalancedSOAPClientImpl class. Test cases cover only cases
 * where SOAP server is not needed.
 *
 * @author Petteri Kivimäki
 */
public class LoadBalancedSOAPClientImplTest extends TestCase {

    public void test1() throws SOAPException {
        List<String> urls = new ArrayList<>();
        LoadBalancedSOAPClientImpl client = new LoadBalancedSOAPClientImpl(urls);
        try {
            // The list is empty so this should throw an exception
            assertEquals(client.getTargetUrl(), null);
            // If the exception is not thrown, the test has failed
            fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    public void test2() throws SOAPException {
        List<String> urls = new ArrayList<>();
        urls.add("http://server1.myhost.com");
        LoadBalancedSOAPClientImpl client = new LoadBalancedSOAPClientImpl(urls);
        assertEquals("http://server1.myhost.com", client.getTargetUrl());
        assertEquals("http://server1.myhost.com", client.getTargetUrl());
        assertEquals("http://server1.myhost.com", client.getTargetUrl());
    }

    public void test3() throws SOAPException {
        List<String> urls = new ArrayList<>();
        urls.add("http://server1.myhost.com");
        urls.add("http://server2.myhost.com");
        LoadBalancedSOAPClientImpl client = new LoadBalancedSOAPClientImpl(urls);
        assertEquals("http://server1.myhost.com", client.getTargetUrl());
        assertEquals("http://server2.myhost.com", client.getTargetUrl());
        assertEquals("http://server1.myhost.com", client.getTargetUrl());
        assertEquals("http://server2.myhost.com", client.getTargetUrl());
        assertEquals("http://server1.myhost.com", client.getTargetUrl());
    }

    public void test4() throws SOAPException {
        List<String> urls = new ArrayList<>();
        urls.add("http://server1.myhost.com");
        urls.add("http://server2.myhost.com");
        urls.add("http://server3.myhost.com");
        LoadBalancedSOAPClientImpl client = new LoadBalancedSOAPClientImpl(urls);
        assertEquals("http://server1.myhost.com", client.getTargetUrl());
        assertEquals("http://server2.myhost.com", client.getTargetUrl());
        assertEquals("http://server3.myhost.com", client.getTargetUrl());
        assertEquals("http://server1.myhost.com", client.getTargetUrl());
    }
}
