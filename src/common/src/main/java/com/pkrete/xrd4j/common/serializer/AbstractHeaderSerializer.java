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
 * This abstract class contains methods for adding valid X-Road version 6
 * SOAP headers to SOAP messages.
 *
 * @author Petteri Kivimäki
 */
public abstract class AbstractHeaderSerializer {

    private static final Logger logger = LoggerFactory.getLogger(AbstractHeaderSerializer.class);

    /**
     * Adds X-Road version 6 SOAP headers to the given SOAP envelope. The
     * given message holds the actual data used in the headers.
     * @param message request or response message that holds the data for
     * the headers
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
        envelope.addNamespaceDeclaration(Constants.NS_SDSB_PREFIX, Constants.NS_SDSB_URL);

        // Header - Start
        SOAPHeader header = envelope.getHeader();
        // Client - Start
        logger.debug("Generate \"Client\" element.");
        ObjectType clientObjectType = MessageHelper.getObjectType(message.getConsumer());
        SOAPElement clientHeader = header.addChildElement(Constants.NS_SDSB_ELEM_CLIENT, Constants.NS_SDSB_PREFIX);
        clientHeader.addAttribute(envelope.createQName(Constants.NS_ID_ATTR_OBJECT_TYPE, Constants.NS_ID_PREFIX), clientObjectType.toString());
        SOAPElement sdsbInstance = clientHeader.addChildElement(Constants.NS_ID_ELEM_SDSB_INSTANCE, Constants.NS_ID_PREFIX);
        sdsbInstance.addTextNode(message.getConsumer().getSdsbInstance());
        SOAPElement memberClass = clientHeader.addChildElement(Constants.NS_ID_ELEM_MEMBER_CLASS, Constants.NS_ID_PREFIX);
        memberClass.addTextNode(message.getConsumer().getMemberClass());
        SOAPElement memberCode = clientHeader.addChildElement(Constants.NS_ID_ELEM_MEMBER_CODE, Constants.NS_ID_PREFIX);
        memberCode.addTextNode(message.getConsumer().getMemberCode());
        if (clientObjectType == ObjectType.SUBSYSTEM) {
            SOAPElement subsystem = clientHeader.addChildElement(Constants.NS_ID_ELEM_SUBSYSTEM_CODE, Constants.NS_ID_PREFIX);
            subsystem.addTextNode(message.getConsumer().getSubsystemCode());
        }
        logger.debug("\"Client\" element was succesfully generated.");
        // Client - End
        // Service - Start
        logger.debug("Generate \"Service\" element.");
        ObjectType serviceObjectType = MessageHelper.getObjectType(message.getProducer());
        SOAPElement serviceHeader = header.addChildElement(Constants.NS_SDSB_ELEM_SERVICE, Constants.NS_SDSB_PREFIX);
        serviceHeader.addAttribute(envelope.createQName(Constants.NS_ID_ATTR_OBJECT_TYPE, Constants.NS_ID_PREFIX), serviceObjectType.toString());
        sdsbInstance = serviceHeader.addChildElement(Constants.NS_ID_ELEM_SDSB_INSTANCE, Constants.NS_ID_PREFIX);
        sdsbInstance.addTextNode(message.getProducer().getSdsbInstance());
        if (serviceObjectType == ObjectType.SERVICE) {
            memberClass = serviceHeader.addChildElement(Constants.NS_ID_ELEM_MEMBER_CLASS, Constants.NS_ID_PREFIX);
            memberClass.addTextNode(message.getProducer().getMemberClass());
            memberCode = serviceHeader.addChildElement(Constants.NS_ID_ELEM_MEMBER_CODE, Constants.NS_ID_PREFIX);
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
        // Service - End
        if (message.getUserId() != null && !message.getUserId().isEmpty()) {
            logger.debug("Generate \"userId\" element.");
            SOAPElement userId = header.addChildElement(Constants.NS_SDSB_ELEM_USER_ID, Constants.NS_SDSB_PREFIX);
            userId.addTextNode(message.getUserId());
            logger.debug("\"userId\" element was succesfully generated.");
        }
        logger.debug("Generate \"id\" element.");
        SOAPElement id = header.addChildElement(Constants.NS_SDSB_ELEM_ID, Constants.NS_SDSB_PREFIX);
        logger.debug("\"Id\" element was succesfully generated.");
        id.addTextNode(message.getId());
        if (message.getIssue() != null && !message.getIssue().isEmpty()) {
            logger.debug("Generate \"issue\" element.");
            SOAPElement issue = header.addChildElement(Constants.NS_SDSB_ELEM_ISSUE, Constants.NS_SDSB_PREFIX);
            issue.addTextNode(message.getIssue());
            logger.debug("\"Issue\" element was succesfully generated.");
        }
        // Header - End
        logger.debug("SOAP header was generated succesfully.");
    }
}
