package com.pkrete.xrd4j.common.util;

import junit.framework.TestCase;

/**
 * Test cases for MessageHelper class. Test values have been calculated using
 * the site below.
 *
 * http://www.webutils.pl/SHA1_Calculator
 *
 * @author Petteri Kivimäki
 */
public class MessageHelperTest extends TestCase {

    /**
     * Test for calculating Base64 encoded hash using SHA-512 algorithm.
     */
    public void testCalculateHash() {
        assertEquals("7iaw3Ur350mqGo7jwQrpkj9hiYB3Lkc/iBml1JQODbJ6wYX4oOHV+E+IvIh/1nsUNzLDBMxfqa2Ob1f1ACio/w==", MessageHelper.calculateHash("test"));
        assertEquals("VxRyq5GZc5xJZQFWKZsUobdzXqg8lwR9Cponu5m6XJgVMjhGVbxdbZBYEsQETnwIgsscb2h8j9rKvm40yqiuPg==", MessageHelper.calculateHash("string hash test"));
    }
}
