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
package com.pkrete.xrd4j.client.deserializer;

import com.pkrete.xrd4j.common.message.ServiceResponse;
import javax.xml.soap.SOAPMessage;

/**
 * This class defines an interface for deserializing SOAPMessage objects
 * to ServiceResponse objects.
 *
 * @author Petteri Kivimäki
 */
public interface ServiceResponseDeserializer {

    /**
     * Deserializes the given SOAPMessage object to ServiceResponse object.
     * @param message SOAP message to be deserialized
     * @return ServiceResponse object that represents the given
     * SOAPMessage object; if the operation fails, null is returned
     */
    ServiceResponse deserialize(SOAPMessage message);

    /**
     * Deserializes the given SOAPMessage object to ServiceResponse object.
     * If service producer's namespace URI is given, then it's used for
     * finding the response from the SOAP mesagge's body. Value "*" means
     * that the namespace is ignored.
     * @param message SOAP message to be deserialized
     * @param producerNamespaceURI service producer's namespace URI
     * @return ServiceResponse object that represents the given
     * SOAPMessage object; if the operation fails, null is returned
     */
    ServiceResponse deserialize(SOAPMessage message, String producerNamespaceURI);
}
