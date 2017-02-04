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
 * This class is used for serializing listMethods and allowedMethods meta
 * service requests.
 *
 * @author Petteri Kivim'ki
 */
public class DefaultServiceRequestSerializer extends AbstractServiceRequestSerializer {

    /**
     * Empty implementation.
     *
     * @param request ServiceRequest holding the application specific request
     * object
     * @param soapRequest SOAPMessage's request object where the request element
     * is added
     * @param envelope SOAPMessage's SOAPEnvelope object
     * @throws SOAPException if there's a SOAP error
     */
    @Override
    protected void serializeRequest(ServiceRequest request, SOAPElement soapRequest, SOAPEnvelope envelope) throws SOAPException {
        // This class can be used for serializing meta service calls that don't
        // have any request parameters.
    }
}
