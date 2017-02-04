package com.pkrete.xrd4j.server.serializer;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.message.ErrorMessage;
import com.pkrete.xrd4j.common.message.ErrorMessageType;
import com.pkrete.xrd4j.common.message.ServiceRequest;
import com.pkrete.xrd4j.common.message.ServiceResponse;
import com.pkrete.xrd4j.common.serializer.AbstractHeaderSerializer;
import com.pkrete.xrd4j.common.util.SOAPHelper;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;

/**
 * This abstract class serves as base class for serializer classes that
 * serialize ServiceResponse objects to SOAPMessage objects. All the subclasses
 * must implement the serializeResponse method which takes care of serializing
 * application specific response object to SOAP body's response element. This
 * class takes care of adding all the required SOAP headers.
 *
 * @author Petteri Kivimäki
 */
public abstract class AbstractServiceResponseSerializer extends AbstractHeaderSerializer implements ServiceResponseSerializer {

    private static final Logger logger = LoggerFactory.getLogger(AbstractServiceResponseSerializer.class);

    /**
     * Serializes the application specific response part to SOAP body's response
     * element. All the children under response element will use provider's
     * namespace. Namespace prefix is added automatically.
     *
     * @param response ServiceResponse holding the application specific response
     * object
     * @param soapResponse SOAPMessage's response object where the response
     * element is added
     * @param envelope SOAPMessage's SOAPEnvelope object
     * @throws SOAPException if there's a SOAP error
     */
    protected abstract void serializeResponse(ServiceResponse response, SOAPElement soapResponse, SOAPEnvelope envelope) throws SOAPException;

    /**
     * Serializes the given ServiceResponse object to SOAPMessage object.
     *
     * @param response ServiceResponse to be serialized
     * @param request ServiceRequest that initiated the service call
     * @return SOAPMessage representing the given ServiceRequest; null if the
     * operation fails
     */
    @Override
    public final SOAPMessage serialize(final ServiceResponse response, final ServiceRequest request) {
        try {
            // Response must process wrappers in the same way as in request.
            // Unit tests might use null request.
            if (request != null) {
                logger.debug("Setting response to process wrappers in the same way as in request.");
                response.setProcessingWrappers(request.isProcessingWrappers());
            }

            logger.debug("Serialize ServiceResponse message to SOAP.");
            MessageFactory myMsgFct = MessageFactory.newInstance();
            SOAPMessage message = myMsgFct.createMessage();

            response.setSoapMessage(message);

            // If response has SOAP Fault, skip header
            if (response.hasError() && response.getErrorMessage().getErrorMessageType() == ErrorMessageType.STANDARD_SOAP_ERROR_MESSAGE) {
                logger.warn("Standard SOAP error detected. SOAP header is skipped.");
                this.serializeSOAPFault(response);
            } else {
                // Generate header by copying it from the request
                // Request and response MUST have the same headers
                super.serializeHeader(request, message.getSOAPPart().getEnvelope());
                try {
                    // Generate body
                    this.serializeBody(response, request.getSoapMessage());
                } catch (XRd4JException | NullPointerException ex) {
                    // Producer namespace URI is missing, response can't be
                    // generated
                    logger.error(ex.getMessage(), ex);
                    logger.warn("Drop headers and return SOAP Fault.");
                    message = myMsgFct.createMessage();
                    response.setSoapMessage(message);
                    ErrorMessage errorMessage = new ErrorMessage("SOAP-ENV:Server", "Internal server error.", "", "");
                    response.setErrorMessage(errorMessage);
                    this.serializeSOAPFault(response);
                }
            }

            logger.debug("ServiceResponse message was serialized succesfully.");
            return message;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.warn("Failed to serialize ServiceResponse message to SOAP.");
        return null;
    }

    /**
     * Generates SOAP body, including the request and response elements.
     *
     * @param response ServiceResponse to be serialized
     * @param soapRequest request's SOAP message object that's used for copying
     * the request element
     * @throws SOAPException if there's a SOAP error
     */
    private void serializeBody(final ServiceResponse response, final SOAPMessage soapRequest) throws SOAPException, XRd4JException {
        logger.debug("Generate SOAP body.");
        if (response.isAddNamespaceToServiceResponse() || response.isAddNamespaceToRequest() || response.isAddNamespaceToResponse()) {
            if (response.getProducer().getNamespaceUrl() == null || response.getProducer().getNamespaceUrl().isEmpty()) {
                throw new XRd4JException("Producer namespace URI can't be null or empty.");
            }
            logger.debug("Producer namespace \"{}\".", response.getProducer().getNamespaceUrl());
        }

        // Body - Start
        SOAPEnvelope envelope = response.getSoapMessage().getSOAPPart().getEnvelope();
        SOAPBody body = envelope.getBody();
        Name bodyName;
        if (response.isAddNamespaceToServiceResponse()) {
            logger.debug("Create service response with namespace.");
            bodyName = envelope.createName(response.getProducer().getServiceCode() + "Response", response.getProducer().getNamespacePrefix(), response.getProducer().getNamespaceUrl());
        } else {
            logger.debug("Create service response without namespace.");
            bodyName = envelope.createName(response.getProducer().getServiceCode() + "Response");
        }
        SOAPBodyElement gltp = body.addBodyElement(bodyName);
        SOAPElement soapResponse;

        // Check if it is needed to process "request" and "response" wrappers
        if (response.isProcessingWrappers()) {
            logger.debug("Adding \"request\" and \"response\" wrappers to response message.");
            // Copy request from soapRequest
            boolean requestFound = false;
            NodeList list = soapRequest.getSOAPBody().getElementsByTagNameNS("*", response.getProducer().getServiceCode());
            if (list.getLength() == 1) {
                Node node = (Node) list.item(0);
                for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                    if (node.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE
                            && "request".equals(node.getChildNodes().item(i).getLocalName())) {
                        Node requestNode = (Node) node.getChildNodes().item(i).cloneNode(true);
                        Node importNode = (Node) gltp.getOwnerDocument().importNode(requestNode, true);
                        gltp.appendChild(importNode);
                        if (response.isAddNamespaceToRequest()) {
                            logger.debug("Add provider namespace to request element.");
                            SOAPHelper.addNamespace(importNode, response);
                        }
                        requestFound = true;
                        break;
                    }
                }
            }
            if (!requestFound) {
                SOAPElement temp = gltp.addChildElement(envelope.createName("request"));
                if (response.isAddNamespaceToRequest()) {
                    logger.debug("Add provider namespace to request element.");
                    SOAPHelper.addNamespace(temp, response);
                }
            }

            soapResponse = gltp.addChildElement(envelope.createName("response"));
        } else {
            logger.debug("Skipping addition of \"request\" and \"response\" wrappers to response message.");
            soapResponse = gltp;
        }

        // Check if there's a non-technical SOAP error
        if (response.hasError()) {
            // Add namespace to the response element only, children excluded
            if (response.isAddNamespaceToResponse()) {
                logger.debug("Add provider namespace to response element.");
                SOAPHelper.addNamespace(soapResponse, response);
            }
            logger.warn("Non-technical SOAP error detected.");
            logger.debug("Generate error message.");
            ErrorMessage errorMessage = response.getErrorMessage();
            if (errorMessage.getFaultCode() != null) {
                logger.trace("Add \"faultcode\" element.");
                SOAPElement elem = soapResponse.addChildElement(envelope.createName("faultcode"));
                elem.addTextNode(errorMessage.getFaultCode());
            }
            if (errorMessage.getFaultString() != null) {
                logger.trace("Add \"faultstring\" element.");
                SOAPElement elem = soapResponse.addChildElement(envelope.createName("faultstring"));
                elem.addTextNode(errorMessage.getFaultString());
            }
            logger.debug("Error message was generated succesfully.");
        } else {
            logger.trace("Passing processing to subclass implementing \"serializeResponse\" method.");
            // Generate response
            if (response.isAddNamespaceToResponse()) {
                logger.debug("Add provider namespace to response element.");
                if (!response.isForceNamespaceToResponseChildren()) {
                    SOAPHelper.addNamespace(soapResponse, response);
                    this.serializeResponse(response, soapResponse, envelope);
                } else {
                    logger.debug("Add provider namespace to all the response element's child elements.");
                    this.serializeResponse(response, soapResponse, envelope);
                    SOAPHelper.addNamespace(soapResponse, response);
                }
            } else {
                logger.debug("Don't add provider namespace to response element.");
                this.serializeResponse(response, soapResponse, envelope);
            }
        }
        logger.debug("SOAP body was generated succesfully.");
    }

    /**
     * Serializes a standard SOAP error message to SOAP Fault.
     *
     * @param response ServiceResponse that contains the error
     * @throws SOAPException if there's a SOAP error
     */
    private void serializeSOAPFault(final ServiceResponse response) throws SOAPException {
        logger.debug("Generate SOAP Fault.");
        SOAPEnvelope envelope = response.getSoapMessage().getSOAPPart().getEnvelope();
        SOAPBody body = envelope.getBody();
        Name bodyName = envelope.createName("Fault", "SOAP-ENV", "http://schemas.xmlsoap.org/soap/envelope/");
        SOAPBodyElement gltp = body.addBodyElement(bodyName);
        ErrorMessage errorMessage = response.getErrorMessage();
        if (errorMessage.getFaultCode() != null) {
            logger.trace("Add \"faultcode\" element.");
            SOAPElement elem = gltp.addChildElement(envelope.createName("faultcode"));
            elem.addTextNode(errorMessage.getFaultCode());
        }
        if (errorMessage.getFaultString() != null) {
            logger.trace("Add \"faultstring\" element.");
            SOAPElement elem = gltp.addChildElement(envelope.createName("faultstring"));
            elem.addTextNode(errorMessage.getFaultString());
        }
        if (errorMessage.getFaultActor() != null) {
            logger.trace("Add \"faultactor\" element.");
            SOAPElement elem = gltp.addChildElement(envelope.createName("faultactor"));
            elem.addTextNode(errorMessage.getFaultActor());
        }
        if (errorMessage.getDetail() != null) {
            logger.trace("Add \"detail\" element.");
            SOAPElement elem = gltp.addChildElement(envelope.createName("detail"));
            this.serializeSOAPFaultDetail(errorMessage, elem);
        }
        logger.debug("SOAP Fault was generated succesfully.");
    }

    /**
     * Serializes SOAP Fault's detail element to String. If the detail element
     * contains a complex data type, this method must be overridden in a
     * subclass.
     *
     * @param errorMessage ErrorMessage that contains the detail element
     * @param faultDetail SOAPElement for the detail
     * @throws SOAPException if there's a SOAP error
     */
    protected void serializeSOAPFaultDetail(final ErrorMessage errorMessage, final SOAPElement faultDetail) throws SOAPException {
        logger.trace("Using the default implementation for \"detail\" element.");
        faultDetail.addTextNode(errorMessage.getDetail().toString());
    }
}
