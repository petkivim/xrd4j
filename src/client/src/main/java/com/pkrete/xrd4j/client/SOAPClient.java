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
package com.pkrete.xrd4j.client;

import com.pkrete.xrd4j.client.deserializer.ServiceResponseDeserializer;
import com.pkrete.xrd4j.common.message.ServiceRequest;
import com.pkrete.xrd4j.common.message.ServiceResponse;
import com.pkrete.xrd4j.client.serializer.ServiceRequestSerializer;
import java.net.MalformedURLException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 * This class defines an interface for SOAP client that can be used for sending
 * SOAPMessage and ServiceRequest objects to SOAP endpoints.
 *
 * @author Petteri Kivimäki
 */
public interface SOAPClient {

    /**
     * Sends the given message to the specified endpoint and blocks until it
     * has returned the response. Null is returned if the given url is
     * malformed or if sending the message fails.
     * @param request the SOAPMessage object to be sent
     * @param url an URL that identifies where the message should be sent
     * @return the SOAPMessage object that is the response to the request
     * message that was sent.
     * @throws SOAPException if there's a SOAP error
     * @throws MalformedURLException if no protocol is specified, or an
     * unknown protocol is found, or url is null
     */
    SOAPMessage send(SOAPMessage request, String url) throws SOAPException, MalformedURLException;

    /**
     * Sends the given message to the specified endpoint and blocks until it
     * has returned the response. Null is returned if the given url is
     * malformed or if sending the message fails. Serialization and
     * deserialization from/to SOAPMessage is done inside the method.
     * @param request the ServiceRequest object to be sent
     * @param url url an URL that identifies where the message should be sent
     * @param serializer the ServiceRequestSerializer object that serializes
     * the request to SOAPMessage
     * @param deserializer the ServiceResponseDeserializer object that
     * deserializes SOAPMessage response to ServiceResponse
     * @return the ServiceResponse object that is the response to the message
     * that was sent.
     * @throws SOAPException if there's a SOAP error
     * @throws MalformedURLException if no protocol is specified, or an
     * unknown protocol is found, or url is null
     */
    ServiceResponse send(ServiceRequest request, String url, ServiceRequestSerializer serializer, ServiceResponseDeserializer deserializer) throws SOAPException, MalformedURLException;
}
