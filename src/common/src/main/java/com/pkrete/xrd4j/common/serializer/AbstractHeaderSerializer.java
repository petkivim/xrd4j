package com.pkrete.xrd4j.common.serializer;

import com.pkrete.xrd4j.common.message.AbstractMessage;
import com.pkrete.xrd4j.common.member.ObjectType;
import com.pkrete.xrd4j.common.message.ErrorMessageType;
import com.pkrete.xrd4j.common.util.MessageHelper;
import com.pkrete.xrd4j.common.util.Constants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This abstract class contains methods for adding valid X-Road version 6 SOAP
 * headers to SOAP messages.
 *
 * @author Petteri Kivimäki
 */
public abstract class AbstractHeaderSerializer {

    private static final Logger logger = LoggerFactory.getLogger(AbstractHeaderSerializer.class);

    /**
     * Adds X-Road version 6 SOAP headers to the given SOAP envelope. The given
     * message holds the actual data used in the headers.
     *
     * @param message request or response message that holds the data for the
     * headers
     * @param envelope SOAP envelope where SOAP headers are added
     * @throws SOAPException if there's a SOAP error
     */
    protected final void serializeHeader(final AbstractMessage message, final SOAPEnvelope envelope) throws SOAPException {
        logger.debug("Generate SOAP header.");
        if (message.hasError() && message.getErrorMessage().getErrorMessageType() == ErrorMessageType.STANDARD_SOAP_ERROR_MESSAGE) {
            logger.warn("Standard SOAP error detected. SOAP header is skipped.");
            return;
        }
        envelope.addNamespaceDeclaration(Constants.NS_ID_PREFIX, Constants.NS_ID_URL);
        envelope.addNamespaceDeclaration(Constants.NS_XRD_PREFIX, Constants.NS_XRD_URL);

        // Header - Start
        SOAPHeader header = envelope.getHeader();
        // Client - Start
        this.serializeClient(header, message, envelope);
        // Client - End
        // Service - Start
        this.serializeService(header, message, envelope);
        // Service - End
        // Security Server - Start
        if (message.getSecurityServer() != null) {
            this.serializeSecurityServer(header, message, envelope);
        }
        // SecurityServer - End
        if (message.getUserId() != null && !message.getUserId().isEmpty()) {
            logger.debug("Generate \"{}\" element.", Constants.NS_XRD_ELEM_USER_ID);
            SOAPElement userId = header.addChildElement(Constants.NS_XRD_ELEM_USER_ID, Constants.NS_XRD_PREFIX);
            userId.addTextNode(message.getUserId());
            logger.debug("\"{}\" element was succesfully generated.", Constants.NS_XRD_ELEM_USER_ID);
        }
        logger.debug("Generate \"{}\" element.", Constants.NS_XRD_ELEM_ID);
        SOAPElement id = header.addChildElement(Constants.NS_XRD_ELEM_ID, Constants.NS_XRD_PREFIX);
        id.addTextNode(message.getId());
        logger.debug("\"{}\" element was succesfully generated.", Constants.NS_XRD_ELEM_ID);
        if (message.getIssue() != null && !message.getIssue().isEmpty()) {
            logger.debug("Generate \"{}\" element.", Constants.NS_XRD_ELEM_ISSUE);
            SOAPElement issue = header.addChildElement(Constants.NS_XRD_ELEM_ISSUE, Constants.NS_XRD_PREFIX);
            issue.addTextNode(message.getIssue());
            logger.debug("\"{}\" element was succesfully generated.", Constants.NS_XRD_ELEM_ISSUE);
        }
        logger.debug("Generate \"{}\" element.", Constants.NS_XRD_ELEM_PROTOCOL_VERSION);
        SOAPElement protocolVersion = header.addChildElement(Constants.NS_XRD_ELEM_PROTOCOL_VERSION, Constants.NS_XRD_PREFIX);
        protocolVersion.addTextNode(message.getProtocolVersion());
        logger.debug("\"{}\" element was succesfully generated.", Constants.NS_XRD_ELEM_PROTOCOL_VERSION);
        // Header - End
        logger.debug("SOAP header was generated succesfully.");
    }

    private void serializeClient(final SOAPHeader header, final AbstractMessage message, final SOAPEnvelope envelope) throws SOAPException {
        logger.debug("Generate \"Client\" element.");
        ObjectType clientObjectType = MessageHelper.getObjectType(message.getConsumer());
        SOAPElement clientHeader = header.addChildElement(Constants.NS_XRD_ELEM_CLIENT, Constants.NS_XRD_PREFIX);
        clientHeader.addAttribute(envelope.createQName(Constants.NS_ID_ATTR_OBJECT_TYPE, Constants.NS_ID_PREFIX), clientObjectType.toString());
        SOAPElement xRoadInstance = clientHeader.addChildElement(Constants.NS_ID_ELEM_XROAD_INSTANCE, Constants.NS_ID_PREFIX);
        xRoadInstance.addTextNode(message.getConsumer().getXRoadInstance());
        SOAPElement memberClass = clientHeader.addChildElement(Constants.NS_ID_ELEM_MEMBER_CLASS, Constants.NS_ID_PREFIX);
        memberClass.addTextNode(message.getConsumer().getMemberClass());
        SOAPElement memberCode = clientHeader.addChildElement(Constants.NS_ID_ELEM_MEMBER_CODE, Constants.NS_ID_PREFIX);
        memberCode.addTextNode(message.getConsumer().getMemberCode());
        if (clientObjectType == ObjectType.SUBSYSTEM) {
            SOAPElement subsystem = clientHeader.addChildElement(Constants.NS_ID_ELEM_SUBSYSTEM_CODE, Constants.NS_ID_PREFIX);
            subsystem.addTextNode(message.getConsumer().getSubsystemCode());
        }
        logger.debug("\"Client\" element was succesfully generated.");
    }

    private void serializeService(final SOAPHeader header, final AbstractMessage message, final SOAPEnvelope envelope) throws SOAPException {
        logger.debug("Generate \"Service\" element.");
        ObjectType serviceObjectType = MessageHelper.getObjectType(message.getProducer());
        SOAPElement serviceHeader = header.addChildElement(Constants.NS_XRD_ELEM_SERVICE, Constants.NS_XRD_PREFIX);
        serviceHeader.addAttribute(envelope.createQName(Constants.NS_ID_ATTR_OBJECT_TYPE, Constants.NS_ID_PREFIX), serviceObjectType.toString());
        SOAPElement xRoadInstance = serviceHeader.addChildElement(Constants.NS_ID_ELEM_XROAD_INSTANCE, Constants.NS_ID_PREFIX);
        xRoadInstance.addTextNode(message.getProducer().getXRoadInstance());
        if (serviceObjectType == ObjectType.SERVICE) {
            SOAPElement memberClass = serviceHeader.addChildElement(Constants.NS_ID_ELEM_MEMBER_CLASS, Constants.NS_ID_PREFIX);
            memberClass.addTextNode(message.getProducer().getMemberClass());
            SOAPElement memberCode = serviceHeader.addChildElement(Constants.NS_ID_ELEM_MEMBER_CODE, Constants.NS_ID_PREFIX);
            memberCode.addTextNode(message.getProducer().getMemberCode());
        }
        if (message.getProducer().getSubsystemCode() != null && !message.getProducer().getSubsystemCode().isEmpty()) {
            SOAPElement subsystem = serviceHeader.addChildElement(Constants.NS_ID_ELEM_SUBSYSTEM_CODE, Constants.NS_ID_PREFIX);
            subsystem.addTextNode(message.getProducer().getSubsystemCode());
        }
        SOAPElement serviceCode = serviceHeader.addChildElement(Constants.NS_ID_ELEM_SERVICE_CODE, Constants.NS_ID_PREFIX);
        serviceCode.addTextNode(message.getProducer().getServiceCode());
        if (message.getProducer().getServiceVersion() != null && !message.getProducer().getServiceVersion().isEmpty()) {
            SOAPElement serviceVersion = serviceHeader.addChildElement(Constants.NS_ID_ELEM_SERVICE_VERSION, Constants.NS_ID_PREFIX);
            serviceVersion.addTextNode(message.getProducer().getServiceVersion());
        }
        logger.debug("\"Service\" element was succesfully generated.");
    }

    private void serializeSecurityServer(final SOAPHeader header, final AbstractMessage message, final SOAPEnvelope envelope) throws SOAPException {
        logger.debug("Generate \"SecurityServer\" element.");
        SOAPElement securityServerHeader = header.addChildElement(Constants.NS_XRD_ELEM_SECURITY_SERVER, Constants.NS_XRD_PREFIX);
        securityServerHeader.addAttribute(envelope.createQName(Constants.NS_ID_ATTR_OBJECT_TYPE, Constants.NS_ID_PREFIX), ObjectType.SERVER.toString());
        SOAPElement xRoadInstance = securityServerHeader.addChildElement(Constants.NS_ID_ELEM_XROAD_INSTANCE, Constants.NS_ID_PREFIX);
        xRoadInstance.addTextNode(message.getSecurityServer().getXRoadInstance());
        SOAPElement memberClass = securityServerHeader.addChildElement(Constants.NS_ID_ELEM_MEMBER_CLASS, Constants.NS_ID_PREFIX);
        memberClass.addTextNode(message.getSecurityServer().getMemberClass());
        SOAPElement memberCode = securityServerHeader.addChildElement(Constants.NS_ID_ELEM_MEMBER_CODE, Constants.NS_ID_PREFIX);
        memberCode.addTextNode(message.getSecurityServer().getMemberCode());
        SOAPElement serverCode = securityServerHeader.addChildElement(Constants.NS_ID_ELEM_SERVER_CODE, Constants.NS_ID_PREFIX);
        serverCode.addTextNode(message.getSecurityServer().getServerCode());
        logger.debug("\"SecurityServer\" element was succesfully generated.");
    }
}
