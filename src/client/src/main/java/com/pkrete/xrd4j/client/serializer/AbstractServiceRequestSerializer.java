package com.pkrete.xrd4j.client.serializer;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.message.ServiceRequest;
import com.pkrete.xrd4j.common.serializer.AbstractHeaderSerializer;
import com.pkrete.xrd4j.common.util.SOAPHelper;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This abstract class serves as base class for serializer classes that
 * serialize ServiceRequest objects to SOAPMessage objects. All the subclasses
 * must implement the serializeRequest method which takes care of serializing
 * application specific request object to SOAP body's request element. This
 * class takes care of adding all the required SOAP headers.
 *
 * @author Petteri Kivimäki
 */
public abstract class AbstractServiceRequestSerializer extends AbstractHeaderSerializer implements ServiceRequestSerializer {

    private static final Logger logger = LoggerFactory.getLogger(AbstractServiceRequestSerializer.class);

    /**
     * Serializes the application specific request part to SOAP body's request
     * element. All the children under request element will use provider's
     * namespace. Namespace prefix is added automatically.
     * @param request ServiceRequest holding the application specific request
     * object
     * @param soapRequest SOAPMessage's request object where the request
     * element is added
     * @param envelope SOAPMessage's SOAPEnvelope object
     * @throws SOAPException if there's a SOAP error
     */
    protected abstract void serializeRequest(ServiceRequest request, SOAPElement soapRequest, SOAPEnvelope envelope) throws SOAPException;

    /**
     * Serializes the given ServiceRequest to SOAPMessage.
     * @param request ServiceRequest to be serialized
     * @return SOAPMessage representing the given ServiceRequest; null if
     * the operation fails
     */
    public final SOAPMessage serialize(final ServiceRequest request) {
        try {
            logger.debug("Serialize ServiceRequest message to SOAP.");
            MessageFactory myMsgFct = MessageFactory.newInstance();
            SOAPMessage message = myMsgFct.createMessage();

            request.setSoapMessage(message);

            // Generate header
            super.serializeHeader(request, message.getSOAPPart().getEnvelope());

            // Generate body
            this.serializeBody(request);

            logger.debug("ServiceRequest message was serialized succesfully.");
            return message;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.warn("Failed to serialize ServiceRequest message to SOAP.");
        return null;
    }

    /**
     * Generates SOAP body, including the request element.
     * @param request ServiceRequest to be serialized
     * @throws SOAPException if there's a SOAP error
     * @throws XRd4JException if there's a XRd4J error
     */
    private void serializeBody(final ServiceRequest request) throws SOAPException, XRd4JException {
        logger.debug("Generate SOAP body.");
        if (request.getProducer().getNamespaceUrl() == null || request.getProducer().getNamespaceUrl().isEmpty()) {
            throw new XRd4JException("Producer namespace URI can't be null or empty.");
        }
        logger.debug("Use producer namespace \"{}\".", request.getProducer().getNamespaceUrl());
        // Body - Start
        SOAPEnvelope envelope = request.getSoapMessage().getSOAPPart().getEnvelope();
        SOAPBody body = request.getSoapMessage().getSOAPBody();
        Name bodyName = envelope.createName(request.getProducer().getServiceCode(), request.getProducer().getNamespacePrefix(), request.getProducer().getNamespaceUrl());
        SOAPBodyElement gltp = body.addBodyElement(bodyName);
        if (request.getRequestData() != null) {
            SOAPElement soapRequest = gltp.addChildElement(envelope.createName("request"));
            logger.trace("Passing processing to subclass implementing \"serializeRequest\" method.");
            // Generate request
            this.serializeRequest(request, soapRequest, envelope);
            SOAPHelper.addNamespace(soapRequest, request);
        }
        logger.debug("SOAP body was generated succesfully.");
    }
}
