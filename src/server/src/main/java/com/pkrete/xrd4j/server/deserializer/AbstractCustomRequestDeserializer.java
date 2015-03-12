package com.pkrete.xrd4j.server.deserializer;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.message.ErrorMessage;
import com.pkrete.xrd4j.common.message.ServiceRequest;
import com.pkrete.xrd4j.common.util.SOAPHelper;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;

/**
 * This abstract class serves as a base class for all the application specific
 * request deserializers. Each adapter server must implement application
 * specific request deserializer for each supported request tyåe. Application
 * specific deserializers take care of deserializing SOAP body's request
 * element to application specific object that can be used for handling
 * the request. Each application specific deserializer must implement the
 * abstract deserializeRequest method defined in this class,
 *
 * @param <T> runtime type of the request data
 * @author Petteri Kivimäki
 */
public abstract class AbstractCustomRequestDeserializer<T> implements CustomRequestDeserializer {

    private static final Logger logger = LoggerFactory.getLogger(AbstractCustomRequestDeserializer.class);

    /**
     * Deserializes SOAP body's request element to application specific
     * object. The given Node contains the request element to be deserialized.
     * @param requestNode Node which the request element to be deserialized
     * @param message SOAPMessage object that contains the whole SOAP request
     * @return application specific object that represents the request element
     * @throws SOAPException if there's a SOAP error
     */
    protected abstract T deserializeRequest(Node requestNode, SOAPMessage message) throws SOAPException;

    /**
     * Deserializes SOAP body's request element to application specific
     * object.
     * @param request ServiceRequest holding the SOAPMessage object
     * @throws SOAPException if there's a SOAP error
     * @throws XRd4JException if there's a XRd4J error
     */
    public final void deserialize(final ServiceRequest request) throws SOAPException, XRd4JException {
        this.deserialize(request, "*");
    }

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
    public final void deserialize(final ServiceRequest request, final String producerNamespaceURI) throws SOAPException, XRd4JException {
        logger.debug("Deserialize SOAP body. Use \"{}\" namespace URI.", producerNamespaceURI);

        SOAPPart mySPart = request.getSoapMessage().getSOAPPart();
        SOAPEnvelope envelope = mySPart.getEnvelope();
        SOAPBody body = envelope.getBody();
        // Get request
        NodeList list = body.getElementsByTagNameNS(producerNamespaceURI, request.getProducer().getServiceCode());
        if (list.getLength() == 1) {
            logger.debug("Found service request element.");
            Node requestNode = SOAPHelper.getNode((Node) list.item(0), "request");
            logger.debug("Deserialize request element.");
            T requestData = this.deserializeRequest(requestNode, request.getSoapMessage());
            request.setRequestData(requestData);
            logger.debug("Request element was succesfully deserialized.");
            request.getProducer().setNamespaceUrl(list.item(0).getNamespaceURI());
            request.getProducer().setNamespacePrefix(list.item(0).getPrefix());
            logger.debug("SOAP body was succesfully deserialized.");
        } else {
            String msg = "Request body is missing.";
            if (!producerNamespaceURI.equals("*")) {
                logger.debug("No service request element was found. Try again without namepsace URI.");
                list = body.getElementsByTagNameNS("*", request.getProducer().getServiceCode());
                if (list.getLength() == 1) {
                    logger.warn("Service request element was found, but with wrong namespace URI : \"{}\".", list.item(0).getNamespaceURI());
                    msg = "Wrong namespace URI.";
                }
            }
            logger.warn("Service request element was not deserialized. SOAP Fault is set.");
            ErrorMessage errorMessage = new ErrorMessage("SOAP-ENV:Client", msg, null, null);
            request.setErrorMessage(errorMessage);
            throw new XRd4JException("Request body was not found.");
        }
    }
}
