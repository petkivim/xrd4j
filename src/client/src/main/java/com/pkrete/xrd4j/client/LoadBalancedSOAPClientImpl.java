package com.pkrete.xrd4j.client;

import com.pkrete.xrd4j.client.deserializer.ServiceResponseDeserializer;
import com.pkrete.xrd4j.client.serializer.ServiceRequestSerializer;
import com.pkrete.xrd4j.common.member.ConsumerMember;
import com.pkrete.xrd4j.common.member.ProducerMember;
import com.pkrete.xrd4j.common.message.ServiceRequest;
import com.pkrete.xrd4j.common.message.ServiceResponse;
import java.util.List;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents a round-robin load balanced SOAP client that can be
 * used for sending SOAPMessage and ServiceRequest objects to multiple SOAP
 * endpoints. This class holds a list of server URLs and going down the list of
 * security servers in the group, the round-robin load balancer forwards a
 * client request to each server in turn. However, this class does not provide
 * high availability features - if a request fails, it is not sent again to
 * another endpoint. In addition, repeatedly failing endpoints are not removed
 * from the list and they keep on receiving requests.
 *
 * @author Petteri Kivimäki
 */
public class LoadBalancedSOAPClientImpl implements LoadBalancedSOAPClient {

    private static final Logger logger = LoggerFactory.getLogger(LoadBalancedSOAPClientImpl.class);
    private final SOAPClient soapClient;
    private final List<String> endpointUrls;
    private int nextTarget;

    /**
     * Constructs and initializes a new LoadBalancedSOAPClientImpl object.
     *
     * @param endpointUrls list of security server URLs where the requests are
     * sent
     * @throws SOAPException if there's an error
     */
    public LoadBalancedSOAPClientImpl(List<String> endpointUrls) throws SOAPException {
        this.endpointUrls = endpointUrls;
        this.soapClient = new SOAPClientImpl();
        this.nextTarget = 0;
        logger.debug("Create new LoadBalancedSOAPClientImpl with {} endpoint URLs", endpointUrls.size());
        for (String url : this.endpointUrls) {
            logger.debug("Found URL: \"{}\"", url);
        }
    }

    /**
     * Sends the given message to one of the defined endpoints and blocks until
     * it has returned the response. Null is returned if sending the message
     * fails.
     *
     * @param request the SOAPMessage object to be sent
     * @return the SOAPMessage object that is the response to the request
     * message that was sent.
     * @throws SOAPException if there's a SOAP error
     */
    @Override
    public SOAPMessage send(final SOAPMessage request) throws SOAPException {
        return this.soapClient.send(request, this.getTargetUrl());
    }

    /**
     * Sends the given message to one of the defined endpoints and blocks until
     * it has returned the response. Null is returned if sending the message
     * fails. Serialization and deserialization from/to SOAPMessage is done
     * inside the method.
     *
     * @param request the ServiceRequest object to be sent
     * @param serializer the ServiceRequestSerializer object that serializes the
     * request to SOAPMessage
     * @param deserializer the ServiceResponseDeserializer object that
     * deserializes SOAPMessage response to ServiceResponse
     * @return the ServiceResponse object that is the response to the message
     * that was sent.
     * @throws SOAPException if there's a SOAP error
     */
    @Override
    public ServiceResponse send(final ServiceRequest request, final ServiceRequestSerializer serializer, final ServiceResponseDeserializer deserializer) throws SOAPException {
        return this.soapClient.send(request, this.getTargetUrl(), serializer, deserializer);
    }

    /**
     * Calls listClients meta service and retrieves list of all the potential
     * service providers (i.e., members and subsystems) of an X-Road instance.
     * Returns a list of list of ConsumerMembers that represent X-Road clients.
     *
     * @return list of ConsumerMembers
     */
    @Override
    public List<ConsumerMember> listClients() {
        return this.soapClient.listClients(this.getTargetUrl());
    }

    /**
     * Calls listCentralServices meta service and retrieves list of all central
     * services defined in an X-Road instance. Returns a list of ProducerMembers
     * that represent X-Road central services.
     *
     * @return list of ProducerMembers
     */
    @Override
    public List<ProducerMember> listCentralServices() {
        return this.soapClient.listCentralServices(this.getTargetUrl());
    }

    /**
     * Calls listMethods meta service that lists all the services offered by a
     * service provider. Returns a list of ProducerMember objects wrapped in
     * ServiceResponse object's responseData variable.
     *
     * @param request the ServiceRequest object to be sent
     * @return ServiceResponse that holds a list of ProducerMember objects
     * @throws SOAPException if there's a SOAP error
     */
    @Override
    public ServiceResponse listMethods(final ServiceRequest request) throws SOAPException {
        return this.soapClient.listMethods(request, this.getTargetUrl());
    }

    /**
     * Calls allowedMethods meta service that lists all the services by a
     * service provider that the caller has permission to invoke. Returns a list
     * of ProducerMember objects wrapped in ServiceResponse object's
     * responseData variable.
     *
     * @param request the ServiceRequest object to be sent
     * @return ServiceResponse that holds a list of ProducerMember objects
     * @throws SOAPException if there's a SOAP error
     */
    @Override
    public ServiceResponse allowedMethods(final ServiceRequest request) throws SOAPException {
        return this.soapClient.allowedMethods(request, this.getTargetUrl());
    }

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
    @Override
    public ServiceResponse getSecurityServerMetrics(final ServiceRequest request, final String url) throws SOAPException {
        return this.soapClient.getSecurityServerMetrics(request, this.getTargetUrl());
    }

    /**
     * Returns the next target URL in turn and updates the pointer holding the
     * value of the next target.
     *
     * @return target URL
     */
    protected String getTargetUrl() {
        // Get the next target URL
        String target = this.endpointUrls.get(this.nextTarget);
        logger.trace("Current nextTarget: \"{}\", targetUrl: \"{}\"", this.nextTarget, target);
        // Update next pointer
        this.nextTarget = this.nextTarget == this.endpointUrls.size() - 1 ? 0 : this.nextTarget + 1;
        logger.trace("New nextTarget: \"{}\"", this.nextTarget);
        return target;
    }
}
