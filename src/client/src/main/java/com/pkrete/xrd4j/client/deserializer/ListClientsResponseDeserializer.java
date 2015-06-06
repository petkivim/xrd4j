package com.pkrete.xrd4j.client.deserializer;

import com.pkrete.xrd4j.common.deserializer.AbstractHeaderDeserializer;
import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.exception.XRd4JMissingMemberException;
import com.pkrete.xrd4j.common.member.ConsumerMember;
import com.pkrete.xrd4j.common.member.ObjectType;
import com.pkrete.xrd4j.common.util.Constants;
import com.pkrete.xrd4j.common.util.SOAPHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * This class implements a deserializer for deserializing the response of the
 * listClients meta service.
 *
 * @author Petteri Kivimäki
 */
public class ListClientsResponseDeserializer extends AbstractHeaderDeserializer {

    private static final Logger logger = LoggerFactory.getLogger(ListClientsResponseDeserializer.class);

    /**
     * Deserializes a list of ConsumerMember objects from the given XML. If
     * deserialiing the XML fails, null is returned.
     *
     * @param xml XML to be deserialized
     * @return list of ConsumerMember objects
     */
    public List<ConsumerMember> deserializeConsumerList(String xml) {
        logger.debug("Deserialize a list of consumers from XML.");
        // Convert XML string to XML Document for deserializing
        Document doc = SOAPHelper.xmlStrToDoc(xml);
        try {
            // Get list of consumers
            return this.deserializeMembers(doc);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Deserializes member elements from the given XML document.
     *
     * @param doc XML document to be deserialized
     * @return list of ConsumerMember objects
     * @throws XRd4JException if there's a XRd4J error
     */
    private List<ConsumerMember> deserializeMembers(final Document doc)
            throws XRd4JException, XRd4JMissingMemberException {
        List<ConsumerMember> results = new ArrayList<ConsumerMember>();
        logger.debug("Deserialize \"{}\".", Constants.NS_XRD_ELEM_CLIENT_LIST);

        NodeList list = doc.getElementsByTagNameNS(Constants.NS_XRD_URL, Constants.NS_XRD_ELEM_ID);
        logger.debug("Found {} {} elements from XML. ", list.getLength(), Constants.NS_XRD_ELEM_ID);
        for (int i = 0; i < list.getLength(); i++) {
            // Client object type
            ObjectType clientObjectType = super.deserializeObjectType(list.item(i));
            // Client headers
            Map<String, String> client = SOAPHelper.nodesToMap(list.item(i).getChildNodes());
            logger.trace("Element found : \"{}\"", Constants.NS_XRD_ELEM_ID);
            results.add(super.getConsumerMember(client, clientObjectType));
        }
        logger.debug("Deserialized {} \"{}\" elements from the given XML document.", results.size(), Constants.NS_XRD_ELEM_ID);
        return results;
    }
}
