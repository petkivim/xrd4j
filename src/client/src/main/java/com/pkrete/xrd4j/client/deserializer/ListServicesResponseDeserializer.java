package com.pkrete.xrd4j.client.deserializer;

import com.pkrete.xrd4j.common.member.ObjectType;
import com.pkrete.xrd4j.common.member.ProducerMember;
import com.pkrete.xrd4j.common.util.Constants;
import com.pkrete.xrd4j.common.util.SOAPHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;

/**
 * This class is used for deserializing responses of listMethods and
 * allowedMethods meta services.
 *
 * @author Petteri Kivimäki
 */
public class ListServicesResponseDeserializer extends AbstractResponseDeserializer<String, List<ProducerMember>> {

    private static final Logger logger = LoggerFactory.getLogger(ListServicesResponseDeserializer.class);

    /**
     * Constructs and initializes a new ListServicesResponseDeserializer.
     */
    public ListServicesResponseDeserializer() {
        super.isMetaServiceResponse = true;
    }

    /**
     * The message pair doesn't contain request data - returns always null.
     *
     * @param requestNode request element of the SOAP message
     * @return null
     * @throws SOAPException if there's a SOAP exception
     */
    @Override
    protected String deserializeRequestData(Node requestNode) throws SOAPException {
        return null;
    }

    /**
     * Deserializes listMethods and allowedMethods meta service responses from
     * SOAP to list of ProducerMember objects.
     *
     * @param responseNode response element of the SOAP message
     * @param message entire SOAP message
     * @return list ProducerMember objects
     * @throws SOAPException if there's a SOAP exception
     */
    @Override
    protected List<ProducerMember> deserializeResponseData(Node responseNode, SOAPMessage message) throws SOAPException {
        List<ProducerMember> producers = new ArrayList<ProducerMember>();
        // Get list of services
        NodeList list = message.getSOAPBody().getElementsByTagNameNS(Constants.NS_XRD_URL, Constants.NS_XRD_ELEM_SERVICE);
        logger.debug("Found {} {} elements from SOAP body. ", list.getLength(), Constants.NS_XRD_ELEM_SERVICE);

        for (int i = 0; i < list.getLength(); i++) {
            logger.debug("Deserialize \"{}\".", Constants.NS_XRD_ELEM_SERVICE);
            // Service headers
            Map<String, String> service = SOAPHelper.nodesToMap(list.item(i).getChildNodes());
            // Service object type
            ObjectType serviceObjectType = this.deserializeObjectType((Node) list.item(i));
            try {
                producers.add(super.getProducerMember(service, serviceObjectType));
            } catch (Exception e) {
                logger.error("Deserializing \"{}\" failed - skip.", Constants.NS_XRD_ELEM_SERVICE);
                logger.error(e.getMessage(), e);
            }
        }
        return producers;
    }
}
