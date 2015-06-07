package com.pkrete.xrd4j.client;

import com.pkrete.xrd4j.client.deserializer.ServiceResponseDeserializer;
import com.pkrete.xrd4j.common.message.ServiceRequest;
import com.pkrete.xrd4j.common.message.ServiceResponse;
import com.pkrete.xrd4j.client.serializer.ServiceRequestSerializer;
import com.pkrete.xrd4j.common.member.ConsumerMember;
import com.pkrete.xrd4j.common.member.ProducerMember;
import java.net.MalformedURLException;
import java.util.List;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 * This class defines an interface for SOAP client that can be used for sending
 * SOAPMessage and ServiceRequest objects to SOAP endpoints.
 *
 * @author Petteri Kivimäki
 */
public interface SOAPClient {

    /**
     * Sends the given message to the specified endpoint and blocks until it has
     * returned the response. Null is returned if the given url is malformed or
     * if sending the message fails.
     *
     * @param request the SOAPMessage object to be sent
     * @param url an URL that identifies where the message should be sent
     * @return the SOAPMessage object that is the response to the request
     * message that was sent.
     * @throws SOAPException if there's a SOAP error
     * @throws MalformedURLException if no protocol is specified, or an unknown
     * protocol is found, or url is null
     */
    SOAPMessage send(SOAPMessage request, String url) throws SOAPException, MalformedURLException;

    /**
     * Sends the given message to the specified endpoint and blocks until it has
     * returned the response. Null is returned if the given url is malformed or
     * if sending the message fails. Serialization and deserialization from/to
     * SOAPMessage is done inside the method.
     *
     * @param request the ServiceRequest object to be sent
     * @param url url an URL that identifies where the message should be sent
     * @param serializer the ServiceRequestSerializer object that serializes the
     * request to SOAPMessage
     * @param deserializer the ServiceResponseDeserializer object that
     * deserializes SOAPMessage response to ServiceResponse
     * @return the ServiceResponse object that is the response to the message
     * that was sent.
     * @throws SOAPException if there's a SOAP error
     * @throws MalformedURLException if no protocol is specified, or an unknown
     * protocol is found, or url is null
     */
    ServiceResponse send(ServiceRequest request, String url, ServiceRequestSerializer serializer, ServiceResponseDeserializer deserializer) throws SOAPException, MalformedURLException;

    /**
     * Calls listClients meta service and returns a list of ConsumerMembers that
     * represent X-Road clients.
     *
     * @param url URL of X-Road security server
     * @return list of ConsumerMembers
     */
    List<ConsumerMember> listClients(String url);

    /**
     * Calls listCentralServices meta service and returns a list of
     * ProducerMembers that represent X-Road central services.
     *
     * @param url URL of X-Road security server
     * @return list of ProducerMembers
     */
    List<ProducerMember> listCentralServices(String url);
}
