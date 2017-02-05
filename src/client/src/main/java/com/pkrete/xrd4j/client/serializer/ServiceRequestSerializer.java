package com.pkrete.xrd4j.client.serializer;

import com.pkrete.xrd4j.common.message.ServiceRequest;
import javax.xml.soap.SOAPMessage;

/**
 * This class defines an interface for serializing ServiceRequest objects
 * to SOAPMessage objects.
 *
 * @author Petteri Kivimäki
 */
@FunctionalInterface
public interface ServiceRequestSerializer {

    /**
     * Serializes the given ServiceRequest object to SOAPMessage object.
     * @param request ServiceRequest to be serialized
     * @return SOAPMessage representing the given ServiceRequest
     */
    SOAPMessage serialize(ServiceRequest request);
}
