package com.pkrete.xrd4j.common.member;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import junit.framework.TestCase;

/**
 * Test cases for SecurityServer class.
 *
 * @author Petteri Kivimäki
 */
public class SecurityServerTest extends TestCase {

    /**
     * Test for toString method.
     *
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testToString() throws XRd4JException {
        SecurityServer server = new SecurityServer("FI", "COM", "12345-6", "myserver");
        assertEquals("FI.COM.12345-6.myserver", server.toString());
        SecurityServerTest.assertFalse(server.toString().equals("Fi.COM.12345-6.myserver"));
        SecurityServerTest.assertFalse(server.toString().equals("FI.cOm.12345-6.myserver"));
    }

    /**
     * Test for equals method.
     *
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testEquals() throws XRd4JException {
        assertEquals(new SecurityServer("FI", "COM", "12345-6", "myserver"), new SecurityServer("FI", "COM", "12345-6", "myserver"));
        SecurityServerTest.assertFalse(new SecurityServer("FI", "COM", "12345-6", "myserver").equals(new SecurityServer("FI", "COM", "12345-6", "testserver")));
        SecurityServerTest.assertFalse(new SecurityServer("FI", "COM", "12345-6", "myserver").equals(new SecurityServer("FI", "COM", "12345-7", "myserver")));
    }

    /**
     * Test for SecurityServer constructor (4 parameters). SDSBInstance is null.
     *
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testException1() throws XRd4JException {
        try {
            SecurityServer securityServer = new SecurityServer(null, "COM", "12345-6", "system");
            fail("Should not reach this");
        } catch (XRd4JException ex) {
            // OK
        }
    }

    /**
     * Test for SecurityServer constructor (4 parameters). MemberClass is null.
     *
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testException2() throws XRd4JException {
        try {
            SecurityServer securityServer = new SecurityServer("FI", null, "12345-6", "system");
            fail("Should not reach this");
        } catch (XRd4JException ex) {
            // OK
        }
    }

    /**
     * Test for SecurityServer constructor (4 parameters). Id is null.
     *
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testException3() throws XRd4JException {
        try {
            SecurityServer securityServer = new SecurityServer("FI", "COM", null, "system");
            fail("Should not reach this");
        } catch (XRd4JException ex) {
            // OK
        }
    }

    /**
     * Test for SecurityServer constructor (4 parameters). Member code is null.
     *
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testException4() throws XRd4JException {
        try {
            SecurityServer securityServer = new SecurityServer("FI", "COM", "12345-6", null);
            fail("Should not reach this");
        } catch (XRd4JException ex) {
            // OK
        }
    }

    /**
     * Test for SecurityServer constructor (4 parameters). SDSBInstance is
     * empty.
     *
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testException5() throws XRd4JException {
        try {
            SecurityServer securityServer = new SecurityServer("", "COM", "12345-6", "system");
            fail("Should not reach this");
        } catch (XRd4JException ex) {
            // OK
        }
    }

    /**
     * Test for SecurityServer constructor (4 parameters). Member class is
     * empty.
     *
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testException6() throws XRd4JException {
        try {
            SecurityServer securityServer = new SecurityServer("FI", "", "", "system");
            fail("Should not reach this");
        } catch (XRd4JException ex) {
            // OK
        }
    }

    /**
     * Test for SecurityServer constructor (4 parameters). Id is empty.
     *
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testException7() throws XRd4JException {
        try {
            SecurityServer securityServer = new SecurityServer("FI", "COM", "", "system");
            fail("Should not reach this");
        } catch (XRd4JException ex) {
            // OK
        }
    }

    /**
     * Test for SecurityServer constructor (4 parameters). Member code is.
     *
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testException8() throws XRd4JException {
        try {
            SecurityServer securityServer = new SecurityServer("FI", "COM", "12345-6", "");
            fail("Should not reach this");
        } catch (XRd4JException ex) {
            // OK
        }
    }
}
