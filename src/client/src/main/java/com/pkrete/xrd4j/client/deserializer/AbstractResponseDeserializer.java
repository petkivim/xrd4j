package com.pkrete.xrd4j.client.deserializer;

import com.pkrete.xrd4j.common.deserializer.AbstractHeaderDeserializer;
import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.exception.XRd4JMissingMemberException;
import com.pkrete.xrd4j.common.member.ConsumerMember;
import com.pkrete.xrd4j.common.member.ProducerMember;
import com.pkrete.xrd4j.common.message.ErrorMessage;
import com.pkrete.xrd4j.common.message.ServiceResponse;
import com.pkrete.xrd4j.common.util.SOAPHelper;
import java.util.Map;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;

/**
 * This abstract class serves as a base class for response deserializers. This
 * class offers a method that deserializes SOAP header, but the deserialization
 * of SOAP body's request and response elements must be implemented in the
 * subclasses as their implementation is application specific.
 *
 * @param <T1> runtime type of the request data
 * @param <T2> runtime type of the response data
 * @author Petteri Kivimäki
 */
public abstract class AbstractResponseDeserializer<T1, T2> extends AbstractHeaderDeserializer implements ServiceResponseDeserializer {

    /**
     * This boolean value tells if the response is from X-Road meta service.
     */
    protected boolean isMetaServiceResponse = false;
    
    private static final Logger logger = LoggerFactory.getLogger(AbstractResponseDeserializer.class);

    /**
     * Deserializes SOAP body's request element.
     *
     * @param requestNode Node containing the request element
     * @return application specific object representing the request element
     * @throws SOAPException if there's a SOAP error
     */
    protected abstract T1 deserializeRequestData(Node requestNode) throws SOAPException;

    /**
     * Deserializes SOAP body's response element.
     *
     * @param responseNode Node containing the response element
     * @param message SOAPMessage object that contains the whole SOAP response
     * @return application specific object representing the response element
     * @throws SOAPException if there's a SOAP error
     */
    protected abstract T2 deserializeResponseData(Node responseNode, SOAPMessage message) throws SOAPException;

    /**
     * Deserializes the given SOAPMessage object to ServiceResponse object.
     *
     * @param message SOAP message to be deserialized
     * @return ServiceResponse object that represents the given SOAPMessage
     * object; if the operation fails, null is returned
     */
    @Override
    public final ServiceResponse deserialize(final SOAPMessage message) {
        return this.deserialize(message, "*");
    }

    /**
     * Deserializes the given SOAPMessage object to ServiceResponse object. If
     * service producer's namespace URI is given, then it's used for finding the
     * response from the SOAP mesagge's body. Value "*" means that the namespace
     * is ignored.
     *
     * @param message SOAP message to be deserialized
     * @param producerNamespaceURI service producer's namespace URI
     * @return ServiceResponse object that represents the given SOAPMessage
     * object; if the operation fails, null is returned
     */
    @Override
    public final ServiceResponse deserialize(final SOAPMessage message, final String producerNamespaceURI) {
        try {
            logger.debug("Deserialize SOAP message. Producer namespace URI \"{}\".", producerNamespaceURI);
            SOAPPart mySPart = message.getSOAPPart();
            SOAPEnvelope envelope = mySPart.getEnvelope();

            // Deserialize header
            ServiceResponse response = this.deserializeHeader(envelope.getHeader());
            response.setSoapMessage(message);

            try {
                // Deserialize body
                this.deserializeBody(response, producerNamespaceURI);
            } catch (XRd4JMissingMemberException ex) {
                logger.warn(ex.getMessage(), ex);
                this.deserializeSOAPFault(response);
            }
            logger.debug("SOAP message was succesfully deserialized.");
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Deserializes SOAPHeader to ServiceResponse object.
     *
     * @param header SOAP header to be deserialized
     * @return ServiceResponse object that represents the SOAP header
     * @throws SOAPException if there's a SOAP error
     * @throws XRd4JException if there's a XRd4J exception
     */
    private ServiceResponse deserializeHeader(final SOAPHeader header) throws SOAPException, XRd4JException {

        logger.debug("Deserialize SOAP header.");
        // Check that SOAP header exists
        if (header == null || header.getChildNodes().getLength() == 0) {
            logger.warn("No SOAP header or an empty SOAP header was found.");
            return new ServiceResponse();
        }
        // Client headers
        String id = super.deserializeId(header);
        String userId = super.deserializeUserId(header);
        String issue = super.deserializeIssue(header);
        String requestHash = super.deserializeRequestHash(header);
        String algorithmId = super.deserializeAlgorithmId(header);
        String protocolVersion = super.deserializeProtocolVersion(header);

        // Create objects
        ConsumerMember consumer = null;
        ProducerMember producer = null;
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
        ServiceResponse response = new ServiceResponse(consumer, producer, id);
        response.setUserId(userId);
        response.setRequestHash(requestHash);
        response.setRequestHashAlgorithm(algorithmId);
        response.setIssue(issue);
        response.setProtocolVersion(protocolVersion);
        logger.debug("SOAP header was succesfully deserialized.");
        // Return response
        return response;
    }

    /**
     * Deserializes SOAP body, including request and response elements.
     *
     * @param response ServiceResponse that holds the SOAPMessage
     * @param producerNamespaceURI namespace URI of the producer member
     * @return true if and only if the operation was succesfully completed;
     * otherwise false
     * @throws SOAPException if there's a SOAP error
     * @throws XRd4JMissingMemberException if there's an error related to
     * members
     */
    private boolean deserializeBody(final ServiceResponse response, final String producerNamespaceURI) throws SOAPException, XRd4JMissingMemberException {
        logger.debug("Deserialize SOAP body.");
        // Nodes
        Node requestNode = null;
        Node responseNode = null;
        // Body - Start
        SOAPBody body = response.getSoapMessage().getSOAPBody();
        // List for response body
        NodeList list = null;
        // Try to get response body
        try {
            list = body.getElementsByTagNameNS(producerNamespaceURI, response.getProducer().getServiceCode() + "Response");
        } catch (NullPointerException ex) {
            logger.warn("Unable to fetch service response element.");
            throw new XRd4JMissingMemberException("Producer is null. SOAP header is probably missing.");
        }
        // Response element found
        if (list.getLength() == 1) {
            logger.debug("Found service response element.");
            requestNode = SOAPHelper.getNode((Node) list.item(0), "request");
            responseNode = SOAPHelper.getNode((Node) list.item(0), "response");

            logger.debug("Deserialize request element.");
            T1 requestData = this.deserializeRequestData(requestNode);
            response.setRequestData(requestData);
            logger.debug("Request element was succesfully deserialized.");
            // Check if the response contains a non-technical SOAP error message
            if (!this.deserializeResponseError(response, responseNode)) {
                logger.debug("Deserialize response element.");
                T2 responseData = this.deserializeResponseData(responseNode, response.getSoapMessage());
                response.setResponseData(responseData);
                logger.debug("Response element was succesfully deserialized.");
            } else {
                logger.warn("A non-technical SOAP error message was found instead of response.");
            }
            response.getProducer().setNamespaceUrl(list.item(0).getNamespaceURI());
            response.getProducer().setNamespacePrefix(list.item(0).getPrefix());
            logger.debug("SOAP body was succesfully deserialized.");
            return true;
        } else if (this.deserializeSOAPFault(response)) {
            logger.warn("Standard SOAP error message found inside SOAP Body.");
            // Standard SOAP error message found inside Body
            return true;
        }
        logger.warn("Service response element was not deserialized.");
        return false;
    }

    /**
     * Tries to deserialize standard SOAP error message. If a standard error
     * message is found true is returned. Otherwise false is returned.
     *
     * @param response ServiceResponse that holds the SOAPMessage
     * @return true if and only if a standard SOAP fault is found; otherwise
     * false
     * @throws SOAPException if there's a SOAP error
     */
    private boolean deserializeSOAPFault(final ServiceResponse response) throws SOAPException {
        logger.debug("Deserialize SOAP fault.");
        Map<String, String> fault = null;
        SOAPBody body = response.getSoapMessage().getSOAPBody();
        NodeList list = body.getElementsByTagNameNS("*", "Fault");
        if (list.getLength() == 1) {
            fault = SOAPHelper.nodesToMap(list.item(0).getChildNodes(), true);
            String faultCode = fault.get("FAULTCODE");
            String faultString = fault.get("FAULTSTRING");
            String faultActor = fault.get("FAULTACTOR");
            Object detail = this.deserializeFaultDetail(SOAPHelper.getNode((Node) list.item(0), "detail"));
            response.setErrorMessage(new ErrorMessage(faultCode, faultString, faultActor, detail));
            logger.info("SOAP fault was succesfully deserialized.");
            return true;
        }
        logger.debug("SOAP fault was not found.");
        return false;
    }

    /**
     * Deserializes fault detail element as String. If detail element has child
     * elements all the content of the children is presented as one concatenated
     * String. If detail element has children, this method can be overridden.
     *
     * @param detailNode detail element
     * @return detail element's value as string. If detail element is not found,
     * null is returned
     */
    protected Object deserializeFaultDetail(final Node detailNode) {
        logger.debug("Deserialize fault detail. Default implementation is assuming String value.");
        if (detailNode == null) {
            logger.warn("Detail element is null. Nothing to do here.");
            return null;
        }
        return detailNode.getTextContent();
    }

    /**
     * Tries to deserialize a non-technical SOAP error message that's inside the
     * response element. Returns true if "faultCode" or "faultString" is found.
     * Otherwise returns false.
     *
     * @param response ServiceResponse that holds the SOAPMessage
     * @param responseNode Node that holds the response element
     * @return true if and only if "faultCode" or "faultString" is found.
     * Otherwise returns false.
     * @throws SOAPException if there's a SOAP error
     */
    private boolean deserializeResponseError(final ServiceResponse response, final Node responseNode) throws SOAPException {
        logger.debug("Deserialize a non-technical SOAP error message.");
        if(this.isMetaServiceResponse) {
            logger.debug("Response being processed is from X-Road meta service. Skip.");
            return false;
        }
        String faultCode = null;
        String faultString = null;
        for (int i = 0; i < responseNode.getChildNodes().getLength(); i++) {
            if (responseNode.getChildNodes().item(i).getLocalName().equalsIgnoreCase("faultcode")) {
                logger.trace("FaultCode found.");
                faultCode = responseNode.getChildNodes().item(i).getTextContent();
            } else if (responseNode.getChildNodes().item(i).getLocalName().equalsIgnoreCase("faultstring")) {
                logger.trace("FaultString found.");
                faultString = responseNode.getChildNodes().item(i).getTextContent();
            }
        }
        if (faultCode != null || faultString != null) {
            response.setErrorMessage(new ErrorMessage(faultCode, faultString));
            logger.info("Error message was succesfully deserialized.");
            return true;
        }
        logger.debug("SOAP error message was not found.");
        return false;
    }
}
