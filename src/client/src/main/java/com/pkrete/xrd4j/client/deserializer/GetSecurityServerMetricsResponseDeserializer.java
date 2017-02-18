package com.pkrete.xrd4j.client.deserializer;

import com.pkrete.xrd4j.common.util.Constants;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;

/**
 * This class is used for deserializing responses of getSecurityServerMetrics
 * monitoring service.
 *
 * @author Petteri Kivimäki
 */
public class GetSecurityServerMetricsResponseDeserializer extends AbstractResponseDeserializer<String, NodeList> {

    private static final Logger logger = LoggerFactory.getLogger(GetSecurityServerMetricsResponseDeserializer.class);

    /**
     * Constructs and initializes a new getSecurityServerMetricsResponseDeserializer.
     */
    public GetSecurityServerMetricsResponseDeserializer() {
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
     * Deserializes getSecurityServerMetrics environmental monitoring service
     * response from SOAP to NodeList of metricSet objects.
     *
     * @param responseNode response element of the SOAP message
     * @param message entire SOAP message
     * @return NodeList list of metricSet elements
     * @throws SOAPException if there's a SOAP exception
     */
    @Override
    protected NodeList deserializeResponseData(Node responseNode, SOAPMessage message) throws SOAPException {
        // Get list of metricSet elements
        NodeList list = message.getSOAPBody().getElementsByTagNameNS(Constants.NS_ENV_MONITORING_URL, Constants.NS_ENV_MONITORING_ELEM_METRIC_SET);
        logger.debug("Found {} {} elements from SOAP body. ", list.getLength(), Constants.NS_ENV_MONITORING_ELEM_METRIC_SET);
        return list;
    }
}
