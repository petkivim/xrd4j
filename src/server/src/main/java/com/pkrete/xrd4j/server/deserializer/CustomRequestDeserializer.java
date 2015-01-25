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
package com.pkrete.xrd4j.server.deserializer;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.message.ServiceRequest;
import javax.xml.soap.SOAPException;

/**
 * This class defines an interface for deserializing SOAP body's request
 * element to application specific object. Class implementing
 * {@link ServiceRequestDeserializer ServiceRequestDeserializer} interface
 * must be used first for deserializing SOAP header.
 *
 * @author Petteri Kivimäki
 */
public interface CustomRequestDeserializer {

    /**
     * Deserializes SOAP body's request element to application specific
     * object.
     * @param request ServiceRequest holding the SOAPMessage object
     * @throws SOAPException if there's a SOAP error
     * @throws XRd4JException if there's a XRd4J error
     */
    void deserialize(ServiceRequest request) throws SOAPException, XRd4JException;

    /**
     * Deserializes SOAP body's request element to application specific
     * object. If service producer's namespace URI is given, then it's used for
     * finding the response from the SOAP mesagge's body. Value "*" means
     * that the namespace is ignored.
     * @param request ServiceRequest holding the SOAPMessage object
     * @param producerNamespaceURI service producer's namespace URI
     * @throws SOAPException if there's a SOAP error
     * @throws XRd4JException if there's a XRd4J error
     */
    void deserialize(ServiceRequest request, String producerNamespaceURI) throws SOAPException, XRd4JException;
}
