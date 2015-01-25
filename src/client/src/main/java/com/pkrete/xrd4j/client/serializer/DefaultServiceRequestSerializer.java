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
package com.pkrete.xrd4j.client.serializer;

import com.pkrete.xrd4j.common.message.ServiceRequest;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;

/**
 * This class offers default implementation for the abstract
 * AbstractServiceRequestSerializer class. This class offers an empty
 * implementation for serializeRequest method. This class can be used for
 * request serialization when the request body contains no elements and
 * ServiceRequest's requestData is null.
 *
 * @author Petteri Kivim'ki
 */
public class DefaultServiceRequestSerializer extends AbstractServiceRequestSerializer {

    /**
     * Empty implementation.
     * @param request ServiceRequest holding the application specific request
     * object
     * @param soapRequest SOAPMessage's request object where the request
     * element is added
     * @param envelope SOAPMessage's SOAPEnvelope object
     * @throws SOAPException if there's a SOAP error
     */
    @Override
    protected void serializeRequest(ServiceRequest request, SOAPElement soapRequest, SOAPEnvelope envelope) throws SOAPException {
    }
}
