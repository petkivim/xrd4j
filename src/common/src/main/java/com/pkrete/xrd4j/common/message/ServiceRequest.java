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
 * This class represents X-Road service request messages that are sent by a
 * ConsumerMember and received by a ProviderMember.
 *
 * @param <T> runtime type of the request data
 * @author Petteri Kivimäki
 */
public class ServiceRequest<T> extends AbstractMessage implements Serializable {

    /**
     * The actual request data that's sent to the service. Data type is
     * application specific.
     */
    private T requestData;

    /**
     * Constructs and initializes a new ServiceRequest object.
     */
    public ServiceRequest() {
        super();
    }

    /**
     * Constructs and initializes a new ServiceRequest object.
     * @param consumer client that's calling a service
     * @param producer service provider whose service the client is calling
     * @param id unique identifier of the message
     * @throws XRd4JException if there's a XRd4J error
     */
    public ServiceRequest(ConsumerMember consumer, ProducerMember producer, String id) throws XRd4JException {
        super(consumer, producer, id);
    }

    /**
     * Returns the request data that's sent to the service.
     * @return request data
     */
    public T getRequestData() {
        return requestData;
    }

    /**
     * Sets the request data that's sent to the service.
     * @param requestData new value
     */
    public void setRequestData(T requestData) {
        this.requestData = requestData;
    }

    @Override
    /**
     * Indicates whether some other object is "equal to" this ServiceRequest.
     * @param o the reference object with which to compare
     * @return true only if the specified object is also an ServiceRequest
     * and it has the same id as this ServiceRequest
     */
    public boolean equals(Object o) {
        if (o instanceof ServiceRequest && id.equals(((ServiceRequest) o).id)) {
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
