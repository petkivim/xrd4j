package com.pkrete.xrd4j.server.serializer;

import com.pkrete.xrd4j.common.message.ServiceRequest;
import com.pkrete.xrd4j.common.message.ServiceResponse;
import javax.xml.soap.SOAPMessage;

/**
 * This class defines an interface for serializing ServiceResponse objects
 * to SOAPMessage objects.
 *
 * @author Petteri Kivimäki
 */
public interface ServiceResponseSerializer {

    /**
     * Serializes the given ServiceResponse object to SOAPMessage object.
     * @param response ServiceResponse to be serialized
     * @param request ServiceRequest that initiated the service call
     * @return SOAPMessage representing the given ServiceRequest
     */
    SOAPMessage serialize(ServiceResponse response, ServiceRequest request);
}
