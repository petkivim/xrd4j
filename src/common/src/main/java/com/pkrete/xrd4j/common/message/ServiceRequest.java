package com.pkrete.xrd4j.common.message;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.member.ProducerMember;
import com.pkrete.xrd4j.common.member.ConsumerMember;

/**
 * This class represents X-Road service request message that is sent by a
 * ConsumerMember and received by a ProviderMember.
 *
 * @param <T> runtime type of the request data
 * @author Petteri Kivimäki
 */
public class ServiceRequest<T> extends AbstractMessage {

    /**
     * The actual request data that's sent to the service. Data type is
     * application specific.
     */
    private T requestData;
    /**
     * Indicates if producer namespace should be added to the request element
     * and all its child elements.
     */
    private boolean addNamespaceToRequest;

    /**
     * Constructs and initializes a new ServiceRequest object.
     */
    public ServiceRequest() {
        super();
        this.addNamespaceToRequest = true;
    }

    /**
     * Constructs and initializes a new ServiceRequest object.
     *
     * @param consumer client that's calling a service
     * @param producer service provider whose service the client is calling
     * @param id unique identifier of the message
     * @throws XRd4JException if there's a XRd4J error
     */
    public ServiceRequest(ConsumerMember consumer, ProducerMember producer, String id) throws XRd4JException {
        super(consumer, producer, id);
        this.addNamespaceToRequest = true;
    }

    /**
     * Returns the request data that's sent to the service.
     *
     * @return request data
     */
    public T getRequestData() {
        return requestData;
    }

    /**
     * Sets the request data that's sent to the service.
     *
     * @param requestData new value
     */
    public void setRequestData(T requestData) {
        this.requestData = requestData;
    }

    /**
     * Returns a boolean value that indicates if producer namespace should be
     * added to the request element and all its child elements.
     *
     * @return true or false
     */
    public boolean isAddNamespaceToRequest() {
        return addNamespaceToRequest;
    }

    /**
     * Sets the boolean value that indicates if producer namespace should be
     * added to the request element and all its child elements.
     *
     * @param addNamespaceToRequest new value
     */
    public void setAddNamespaceToRequest(boolean addNamespaceToRequest) {
        this.addNamespaceToRequest = addNamespaceToRequest;
    }

    @Override
    /**
     * Indicates whether some other object is "equal to" this ServiceRequest.
     *
     * @param o the reference object with which to compare
     * @return true only if the specified object is also an ServiceRequest and
     * it has the same id as this ServiceRequest
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
     *
     * @return a hash code value for this object
     */
    public int hashCode() {
        return this.id.hashCode();
    }
}
