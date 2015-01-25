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
package com.pkrete.xrd4j.common.util;

/**
 * This class defines a set of constants used by other classes of the
 * application.
 *
 * @author Petteri Kivimäki
 */
public class Constants {

    /**
     * Constructs and initializes a new Constants object. Should never
     * be used.
     */
    private Constants() {
    }
    
    // Namespace definitions
    /**
     * Identifiers schema namespace prefix.
     */
    public static final String NS_ID_PREFIX = "id";
    /**
     * Identifiers schema namespace URI.
     */
    public static final String NS_ID_URL = "http://x-road.eu/xsd/identifiers";
    /**
     * SDSB schema namespace prefix.
     */
    public static final String NS_SDSB_PREFIX = "sdsb";
    /**
     * SDSB schema namespace URI.
     */
    public static final String NS_SDSB_URL = "http://x-road.eu/xsd/sdsb.xsd";
    /**
     * X-Road schema namespace prefix.
     */
    public static final String NS_XRD_PREFIX = "xrd";
    /**
     * X-Road schema namespace URI.
     */
    public static final String NS_XRD_URL = "http://x-road.eu/xsd/x-road.xsd";
    // Attributes
    /**
     * Object type attribute name.
     */
    public static final String NS_ID_ATTR_OBJECT_TYPE = "objectType";
    /**
     * Algorithm id attribute name.
     */
    public static final String ATTR_ALGORITHM_ID = "algorithmId";
    // Elements
    /**
     * Client element name.
     */
    public static final String NS_SDSB_ELEM_CLIENT = "client";
    /**
     * Service element name.
     */
    public static final String NS_SDSB_ELEM_SERVICE = "service";
    /**
     * Id element name.
     */
    public static final String NS_SDSB_ELEM_ID = "id";
    /**
     * User id element name.
     */
    public static final String NS_SDSB_ELEM_USER_ID = "userId";
    /**
     * Issue element name.
     */
    public static final String NS_SDSB_ELEM_ISSUE = "issue";
    /**
     * Request hash element name.
     */
    public static final String NS_SDSB_ELEM_REQUEST_HASH = "requestHash";
    /**
     * SDSB instance element name.
     */
    public static final String NS_ID_ELEM_SDSB_INSTANCE = "sdsbInstance";
    /**
     * Member class element name.
     */
    public static final String NS_ID_ELEM_MEMBER_CLASS = "memberClass";
    /**
     * Member code element name.
     */
    public static final String NS_ID_ELEM_MEMBER_CODE = "memberCode";
    /**
     * Subsystem code element name.
     */
    public static final String NS_ID_ELEM_SUBSYSTEM_CODE = "subsystemCode";
    /**
     * Service code element name.
     */
    public static final String NS_ID_ELEM_SERVICE_CODE = "serviceCode";
    /**
     * Service version element name.
     */
    public static final String NS_ID_ELEM_SERVICE_VERSION = "serviceVersion";
    // Meta services
    /**
     * List methods metaservice name.
     */
    public static final String NS_XRD_LIST_METHODS = "listMethods";
    /**
     * Test system metaservice name.
     */
    public static final String NS_XRD_TEST_SYSTEM = "testSystem";
}
