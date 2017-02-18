package com.pkrete.xrd4j.client;

import com.pkrete.xrd4j.client.deserializer.ServiceResponseDeserializer;
import com.pkrete.xrd4j.common.message.ServiceRequest;
import com.pkrete.xrd4j.common.message.ServiceResponse;
import com.pkrete.xrd4j.client.serializer.ServiceRequestSerializer;
import com.pkrete.xrd4j.common.member.ConsumerMember;
import com.pkrete.xrd4j.common.member.ProducerMember;
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
     * @param url URL that identifies where the message should be sent
     * @return the SOAPMessage object that is the response to the request
     * message that was sent.
     * @throws SOAPException if there's a SOAP error
     */
    SOAPMessage send(SOAPMessage request, String url) throws SOAPException;

    /**
     * Sends the given message to the specified endpoint and blocks until it has
     * returned the response. Null is returned if the given url is malformed or
     * if sending the message fails. Serialization and deserialization from/to
     * SOAPMessage is done inside the method.
     *
     * @param request the ServiceRequest object to be sent
     * @param url URL that identifies where the message should be sent
     * @param serializer the ServiceRequestSerializer object that serializes the
     * request to SOAPMessage
     * @param deserializer the ServiceResponseDeserializer object that
     * deserializes SOAPMessage response to ServiceResponse
     * @return the ServiceResponse object that is the response to the message
     * that was sent.
     * @throws SOAPException if there's a SOAP error
     */
    ServiceResponse send(ServiceRequest request, String url, ServiceRequestSerializer serializer, ServiceResponseDeserializer deserializer) throws SOAPException;

    /**
     * Calls listClients meta service and retrieves list of all the potential
     * service providers (i.e., members and subsystems) of an X-Road instance.
     * Returns a list of list of ConsumerMembers that represent X-Road clients.
     *
     * @param url URL of X-Road security server
     * @return list of ConsumerMembers
     */
    List<ConsumerMember> listClients(String url);

    /**
     * Calls listCentralServices meta service and retrieves list of all central
     * services defined in an X-Road instance. Returns a list of ProducerMembers
     * that represent X-Road central services.
     *
     * @param url URL of X-Road security server
     * @return list of ProducerMembers
     */
    List<ProducerMember> listCentralServices(String url);

    /**
     * Calls listMethods meta service that lists all the services offered by a
     * service provider. Returns a list of ProducerMember objects wrapped in
     * ServiceResponse object's responseData variable.
     *
     * @param request the ServiceRequest object to be sent
     * @param url URL that identifies where the message should be sent
     * @return ServiceResponse that holds a list of ProducerMember objects
     * @throws SOAPException if there's a SOAP error
     */
    ServiceResponse listMethods(final ServiceRequest request, final String url) throws SOAPException;

    /**
     * Calls allowedMethods meta service that lists all the services by a
     * service provider that the caller has permission to invoke. Returns a list
     * of ProducerMember objects wrapped in ServiceResponse object's
     * responseData variable.
     *
     * @param request the ServiceRequest object to be sent
     * @param url URL that identifies where the message should be sent
     * @return ServiceResponse that holds a list of ProducerMember objects
     * @throws SOAPException if there's a SOAP error
     */
    ServiceResponse allowedMethods(final ServiceRequest request, final String url) throws SOAPException;

    /**
     * Calls getSecurityServerMetrics monitoring service that returns a data set
     * collected by environmental monitoring sensors.
     *
     * @param request the ServiceRequest object to be sent
     * @param url URL that identifies where the message should be sent
     * @return ServiceResponse that holds a NodeList containing the response
     * data
     * @throws SOAPException if there's a SOAP error
     */
    ServiceResponse getSecurityServerMetrics(final ServiceRequest request, final String url) throws SOAPException;
}
