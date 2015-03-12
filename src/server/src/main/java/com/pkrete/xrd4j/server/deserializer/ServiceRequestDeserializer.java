package com.pkrete.xrd4j.server.deserializer;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.message.ServiceRequest;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 * This class defines an interface for deserializing SOAPMessage objects
 * to ServiceRequest objects. In practise, the classes implementing this
 * interface take care of deserealizing SOAP header as SOAP body contains
 * application specific content, and the type of the content is defined
 * by the information included in SOAP header. Therefore, SOAP header
 * must be deserialized first, and then use application and message
 * specific deserializer for deserializing the request data. Application
 * and message specific deserializers must implement
 * {@link CustomRequestDeserializer CustomRequestDeserializer} interface.
 *
 * @author Petteri Kivimäki
 */
public interface ServiceRequestDeserializer {

    /**
     * Deserializes the given SOAPMessage object to ServiceRequest object.
     * Only SOAP header is deserialized.
     * @param message SOAP message to be deserialized
     * @return ServiceRequest object that represents the given
     * SOAPMessage object or null if the operation failed
     * @throws SOAPException if there's a SOAP error
     * @throws XRd4JException if there's a XRd4J error
     */
    ServiceRequest deserialize(SOAPMessage message) throws XRd4JException, SOAPException;
}
