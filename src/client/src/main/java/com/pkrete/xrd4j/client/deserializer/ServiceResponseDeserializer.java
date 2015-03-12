package com.pkrete.xrd4j.client.deserializer;

import com.pkrete.xrd4j.common.message.ServiceResponse;
import javax.xml.soap.SOAPMessage;

/**
 * This class defines an interface for deserializing SOAPMessage objects
 * to ServiceResponse objects.
 *
 * @author Petteri Kivimäki
 */
public interface ServiceResponseDeserializer {

    /**
     * Deserializes the given SOAPMessage object to ServiceResponse object.
     * @param message SOAP message to be deserialized
     * @return ServiceResponse object that represents the given
     * SOAPMessage object; if the operation fails, null is returned
     */
    ServiceResponse deserialize(SOAPMessage message);

    /**
     * Deserializes the given SOAPMessage object to ServiceResponse object.
     * If service producer's namespace URI is given, then it's used for
     * finding the response from the SOAP mesagge's body. Value "*" means
     * that the namespace is ignored.
     * @param message SOAP message to be deserialized
     * @param producerNamespaceURI service producer's namespace URI
     * @return ServiceResponse object that represents the given
     * SOAPMessage object; if the operation fails, null is returned
     */
    ServiceResponse deserialize(SOAPMessage message, String producerNamespaceURI);
}
