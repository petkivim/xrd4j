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
import com.pkrete.xrd4j.common.member.ProducerMember;
import com.pkrete.xrd4j.common.member.ConsumerMember;
import java.io.Serializable;

/**
 * This class represents X-Road service response messages that are sent by a
 * ProviderMember and received by a ConsumerMember. ServiceResponse message
 * is always sent as a response to ServiceRequest message.
 *
 * @param <T1> runtime type of the request data
 * @param <T2> runtime type of the response data
 * @author Petteri Kivimäki
 */
public class ServiceResponse<T1, T2> extends AbstractMessage implements Serializable {

    /**
     * Request data that's received from the client. Data type is application
     * specific.
     */
    private T1 requestData;
    /**
     * Response data that's returned to the client. Data type is application
     * specific.
     */
    private T2 responseData;
    /**
     * This field contains hash of the SOAP request message. This field is
     * automatically filled in by the service provider's security server.
     */
    private String requestHash;
    /**
     * Identifes hash algorithm that was used to calculate the value of the
     * requestHash field.
     */
    private String requestHashAlgorithm;
    /**
     * Indicates if producer namespace should be added to the service response.
     */
    private boolean addNamespaceToServiceResponse;
    /**
     * Indicates if producer namespace should be added to the request element
     * and all its child elements.
     */
    private boolean addNamespaceToRequest;
    /**
     * Indicates if producer namespace should be added to the response element.
     */
    private boolean addNamespaceToResponse;
    /**
     * Indicates if producer namespace should be forced to response element's
     * children.
     */
    private boolean forceNamespaceToResponseChildren;

    /**
     * Constructs and initializes a new ServiceResponse object.
     */
    public ServiceResponse() {
        super();
        this.addNamespaceToServiceResponse = true;
        this.addNamespaceToRequest = true;
        this.addNamespaceToResponse = true;
        this.forceNamespaceToResponseChildren = true;
    }

    /**
     * Constructs and initializes a new ServiceResponse object.
     * @param consumer client that's calling a service
     * @param producer service provider whose service the client is calling
     * @param id unique identifier of the message
     * @throws XRd4JException if there's a XRd4J error
     */
    public ServiceResponse(ConsumerMember consumer, ProducerMember producer, String id) throws XRd4JException {
        super(consumer, producer, id);
        this.addNamespaceToServiceResponse = true;
        this.addNamespaceToRequest = true;
        this.addNamespaceToResponse = true;
        this.forceNamespaceToResponseChildren = true;
    }

    /**
     * Returns the hash of the SOAP request message. The value is set by
     * service provider's security server.
     * @return hash of the SOAP request message
     */
    public String getRequestHash() {
        return requestHash;
    }

    /**
     * Sets the hash of the SOAP request message. The value is set by
     * service provider's security server.
     * @param requestHash new value
     */
    public void setRequestHash(String requestHash) {
        this.requestHash = requestHash;
    }

    /**
     * Returns the hash algorithm that was used to calculate the value of the
     * request hash.
     * @return hash algorithm that was used to calculate the value of the
     * request hash
     */
    public String getRequestHashAlgorithm() {
        return requestHashAlgorithm;
    }

    /**
     * Sets the hash algorithm that was used to calculate the value of the
     * request hash.
     * @param requestHashAlgorithm new value
     */
    public void setRequestHashAlgorithm(String requestHashAlgorithm) {
        this.requestHashAlgorithm = requestHashAlgorithm;
    }

    /**
     * Returns the request data that's received from the client. This data
     * is used as input for generating the response.
     * @return request data that's received from the client
     */
    public T1 getRequestData() {
        return requestData;
    }

    /**
     * Sets the request data that's received from the client. This data
     * is used as input for generating the response.
     * @param requestData new value
     */
    public void setRequestData(T1 requestData) {
        this.requestData = requestData;
    }

    /**
     * Returns the response data that's returned to the client. Response to
     * the clients request.
     * @return response data that's returned to the client
     */
    public T2 getResponseData() {
        return responseData;
    }

    /**
     * Sets the response data that's returned to the client. Response to
     * the clients request.
     * @param responseData new value
     */
    public void setResponseData(T2 responseData) {
        this.responseData = responseData;
    }

    /**
     * Returns a boolean value that indicates if producer namespace should be
     * added to the service response.
     * @return true or false
     */
    public boolean isAddNamespaceToServiceResponse() {
        return addNamespaceToServiceResponse;
    }

    /**
     * Sets the boolean value that indicates if producer namespace should be
     * added to the service response.
     * @param addNamespaceToServiceResponse
     */
    public void setAddNamespaceToServiceResponse(boolean addNamespaceToServiceResponse) {
        this.addNamespaceToServiceResponse = addNamespaceToServiceResponse;
    }

    /**
     * Returns a boolean value that indicates if producer namespace should be
     * added to the request element and all its child elements.
     * @return true or false
     */
    public boolean isAddNamespaceToRequest() {
        return addNamespaceToRequest;
    }

    /**
     * Sets the boolean value that indicates if producer namespace should be
     * added to the request element and all its child elements.
     * @param addNamespaceToRequest new value
     */
    public void setAddNamespaceToRequest(boolean addNamespaceToRequest) {
        this.addNamespaceToRequest = addNamespaceToRequest;
    }

    /**
     * Returns a boolean value that indicates if producer namespace should be
     * added to the response element.
     * @return true or false
     */
    public boolean isAddNamespaceToResponse() {
        return addNamespaceToResponse;
    }

    /**
     * Sets the boolean value that indicates if producer namespace should be
     * added to the response element.
     * @param addNamespaceToResponse new value
     */
    public void setAddNamespaceToResponse(boolean addNamespaceToResponse) {
        this.addNamespaceToResponse = addNamespaceToResponse;
    }

    /**
     * Returns a boolean value that indicates if producer namespace should be
     * forced to response element's children.
     * @return true or false
     */
    public boolean isForceNamespaceToResponseChildren() {
        return forceNamespaceToResponseChildren;
    }

    /**
     * Sets the boolean value that indicates if producer namespace should be
     * forced to response element's children.
     * @param forceNamespaceToResponseChildren new value
     */
    public void setForceNamespaceToResponseChildren(boolean forceNamespaceToResponseChildren) {
        this.forceNamespaceToResponseChildren = forceNamespaceToResponseChildren;
    }

    @Override
    /**
     * Indicates whether some other object is "equal to" this ServiceResponse.
     * @param o the reference object with which to compare
     * @return true only if the specified object is also an ServiceResponse
     * and it has the same id as this ServiceResponse
     */
    public boolean equals(Object o) {
        if (o instanceof ServiceResponse && id.equals(((ServiceResponse) o).id)) {
            return true;
        }
        return false;
    }

    @Override
    /**
     * Returns a hash code value for the object.
     * @return a hash code value for this object
     */
    public int hashCode() {
        return this.id.hashCode();
    }
}
