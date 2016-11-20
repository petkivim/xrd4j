package com.pkrete.xrd4j.server.deserializer;

import com.pkrete.xrd4j.common.deserializer.AbstractHeaderDeserializer;
import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.exception.XRd4JMissingMemberException;
import com.pkrete.xrd4j.common.member.ConsumerMember;
import com.pkrete.xrd4j.common.member.ProducerMember;
import com.pkrete.xrd4j.common.member.SecurityServer;
import com.pkrete.xrd4j.common.message.ServiceRequest;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class offers methods for deserializing SOAPMessages to ServiceRequest
 * objects. This class serializes only SOAP headers as SOAP body contains
 * application specific content, and therefore an application specific
 * deserializer is needed for SOAP body.
 *
 * This deserializer can be used by an adapter service for handling incoming
 * requests. SOAP header contains the information about the service that the
 * consumer is calling, and the information can be accessed through the
 * ServiceRequest object returned by this class. Application specific
 * deserializers must extend the abstract AbstractCustomRequestDeserializer
 * class.
 *
 * @author Petteri Kivimäki
 */
public class ServiceRequestDeserializerImpl extends AbstractHeaderDeserializer implements ServiceRequestDeserializer {

    private static final Logger logger = LoggerFactory.getLogger(ServiceRequestDeserializerImpl.class);

    /**
     * Deserializes the given SOAPMessage object to ServiceRequest object. Only
     * SOAP header is deserialized. An application specific serializer is needed
     * for SOAP body.
     *
     * @param message SOAP message to be deserialized
     * @return ServiceRequest object that represents the given SOAPMessage
     * object or null if the operation failed
     * @throws SOAPException if there's a SOAP error
     * @throws XRd4JException if there's a XRd4J error
     */
    @Override
    public final ServiceRequest deserialize(final SOAPMessage message) throws XRd4JException, SOAPException {
        logger.debug("Deserialize SOAP message.");
        SOAPPart mySPart = message.getSOAPPart();
        SOAPEnvelope envelope = mySPart.getEnvelope();

        // Desearialize header
        ServiceRequest request = this.deserializeHeader(envelope.getHeader());
        request.setSoapMessage(message);

        logger.debug("SOAP message header was succesfully deserialized.");
        return request;
    }

    /**
     * Deserializes the given SOAPHeader object to ServiceRequest object.
     *
     * @param header SOAP header to be deserialized
     * @return ServiceRequest object that contains the given SOAP header
     * @throws SOAPException if there's a SOAP error
     * @throws XRd4JException if there's a XRd4J exception
     */
    private ServiceRequest deserializeHeader(final SOAPHeader header) throws SOAPException, XRd4JException {
        logger.debug("Deserialize SOAP header.");
        // Check that SOAP header exists
        if (header == null || header.getChildNodes().getLength() == 0) {
            logger.warn("No SOAP header or an empty SOAP header was found.");
            return new ServiceRequest();
        }
        // Client headers
        String id = super.deserializeId(header);
        String userId = super.deserializeUserId(header);
        String issue = super.deserializeIssue(header);
        String protocolVersion = super.deserializeProtocolVersion(header);

        // Create objects
        ConsumerMember consumer = null;
        ProducerMember producer = null;
        SecurityServer securityServer = null;
        try {
            consumer = super.deserializeConsumer(header);
        } catch (XRd4JMissingMemberException ex) {
            logger.warn("Deserializing \"ConsumerMember\" failed.");
        }
        try {
            producer = super.deserializeProducer(header);
        } catch (XRd4JMissingMemberException ex) {
            logger.warn("Deserializing \"ProducerMember\" failed.");
        }
        try {
            // Not mandatory - can be null
            securityServer = super.deserializeSecurityServer(header);
        } catch (XRd4JException ex) {
            logger.warn("Deserializing \"ProducerMember\" failed.");
        }
        ServiceRequest request = new ServiceRequest(consumer, producer, id);
        request.setSecurityServer(securityServer);
        request.setUserId(userId);
        request.setIssue(issue);
        request.setProtocolVersion(protocolVersion);

        logger.debug("SOAP header was succesfully deserialized.");
        // Return request
        return request;
    }
}
