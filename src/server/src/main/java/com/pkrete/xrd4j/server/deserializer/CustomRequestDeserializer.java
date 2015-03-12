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
