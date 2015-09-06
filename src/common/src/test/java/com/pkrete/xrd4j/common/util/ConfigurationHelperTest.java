package com.pkrete.xrd4j.common.util;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.member.ConsumerMember;
import com.pkrete.xrd4j.common.member.ProducerMember;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

/**
 * Test cases for ConfigurationHelper class.
 *
 * @author Petteri Kivimäki
 */
public class ConfigurationHelperTest extends TestCase {

    /**
     * Test parsing client ids from string.
     *
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testParseConsumer1() throws XRd4JException {
        String clientId = "FI_PILOT.GOV.0245437-2";
        ConsumerMember consumer = ConfigurationHelper.parseConsumerMember(clientId);

        assertEquals(clientId, consumer.toString());
        assertEquals("FI_PILOT", consumer.getXRoadInstance());
        assertEquals("GOV", consumer.getMemberClass());
        assertEquals("0245437-2", consumer.getMemberCode());
        assertEquals(null, consumer.getSubsystemCode());

        clientId = "FI_PILOT.GOV.0245437-2.ConsumerService";
        consumer = ConfigurationHelper.parseConsumerMember(clientId);
        assertEquals(clientId, consumer.toString());
        assertEquals("FI_PILOT", consumer.getXRoadInstance());
        assertEquals("GOV", consumer.getMemberClass());
        assertEquals("0245437-2", consumer.getMemberCode());
        assertEquals("ConsumerService", consumer.getSubsystemCode());

        clientId = "FI_PILOT.GOV.0245437-2.ConsumerService.getOrganizationList";
        consumer = ConfigurationHelper.parseConsumerMember(clientId);
        assertEquals(null, consumer);

        clientId = "FI_PILOT.GOV.0245437-2.ConsumerService.getOrganizationList.v1";
        consumer = ConfigurationHelper.parseConsumerMember(clientId);
        assertEquals(null, consumer);

        clientId = "FI_PILOT.GOV";
        consumer = ConfigurationHelper.parseConsumerMember(clientId);
        assertEquals(null, consumer);
    }

    /**
     * Test parsing service ids from string.
     *
     * @throws XRd4JException if there's a XRd4J error
     */
    public void testParseProducer1() throws XRd4JException {
        String serviceId = "FI_PILOT.GOV.0245437-2.ConsumerService.getOrganizationList.v1";
        ProducerMember producer = ConfigurationHelper.parseProducerMember(serviceId);

        assertEquals(serviceId, producer.toString());
        assertEquals("FI_PILOT", producer.getXRoadInstance());
        assertEquals("GOV", producer.getMemberClass());
        assertEquals("0245437-2", producer.getMemberCode());
        assertEquals("ConsumerService", producer.getSubsystemCode());
        assertEquals("getOrganizationList", producer.getServiceCode());
        assertEquals("v1", producer.getServiceVersion());

        serviceId = "FI_PILOT.GOV.0245437-2.ConsumerService2.getOrganizationList.V1";
        producer = ConfigurationHelper.parseProducerMember(serviceId);
        assertEquals(serviceId, producer.toString());
        assertEquals("FI_PILOT", producer.getXRoadInstance());
        assertEquals("GOV", producer.getMemberClass());
        assertEquals("0245437-2", producer.getMemberCode());
        assertEquals("ConsumerService2", producer.getSubsystemCode());
        assertEquals("getOrganizationList", producer.getServiceCode());
        assertEquals("V1", producer.getServiceVersion());

        serviceId = "FI_PILOT.GOV.0245437-2.ConsumerService2.getOrganizationList.V1_0";
        producer = ConfigurationHelper.parseProducerMember(serviceId);
        assertEquals(serviceId, producer.toString());
        assertEquals("FI_PILOT", producer.getXRoadInstance());
        assertEquals("GOV", producer.getMemberClass());
        assertEquals("0245437-2", producer.getMemberCode());
        assertEquals("ConsumerService2", producer.getSubsystemCode());
        assertEquals("getOrganizationList", producer.getServiceCode());
        assertEquals("V1_0", producer.getServiceVersion());

        serviceId = "FI_PILOT.GOV.0245437-2.ConsumerService.getOrganizationList1";
        producer = ConfigurationHelper.parseProducerMember(serviceId);
        assertEquals(serviceId, producer.toString());
        assertEquals("FI_PILOT", producer.getXRoadInstance());
        assertEquals("GOV", producer.getMemberClass());
        assertEquals("0245437-2", producer.getMemberCode());
        assertEquals("ConsumerService", producer.getSubsystemCode());
        assertEquals("getOrganizationList1", producer.getServiceCode());
        assertEquals(null, producer.getServiceVersion());

        serviceId = "FI_PILOT.GOV.0245437-2.getOrganizationList2";
        producer = ConfigurationHelper.parseProducerMember(serviceId);
        assertEquals(serviceId, producer.toString());
        assertEquals("FI_PILOT", producer.getXRoadInstance());
        assertEquals("GOV", producer.getMemberClass());
        assertEquals("0245437-2", producer.getMemberCode());
        assertEquals(null, producer.getSubsystemCode());
        assertEquals("getOrganizationList2", producer.getServiceCode());
        assertEquals(null, producer.getServiceVersion());

        serviceId = "FI_PILOT.GOV.0245437-2.getOrganizationList.v1";
        producer = ConfigurationHelper.parseProducerMember(serviceId);
        assertEquals(serviceId, producer.toString());
        assertEquals("FI_PILOT", producer.getXRoadInstance());
        assertEquals("GOV", producer.getMemberClass());
        assertEquals("0245437-2", producer.getMemberCode());
        assertEquals(null, producer.getSubsystemCode());
        assertEquals("getOrganizationList", producer.getServiceCode());
        assertEquals("v1", producer.getServiceVersion());

        serviceId = "FI_PILOT.GOV.0245437-2.getOrganizationList.V11";
        producer = ConfigurationHelper.parseProducerMember(serviceId);
        assertEquals(serviceId, producer.toString());
        assertEquals("FI_PILOT", producer.getXRoadInstance());
        assertEquals("GOV", producer.getMemberClass());
        assertEquals("0245437-2", producer.getMemberCode());
        assertEquals(null, producer.getSubsystemCode());
        assertEquals("getOrganizationList", producer.getServiceCode());
        assertEquals("V11", producer.getServiceVersion());

        serviceId = "FI_PILOT.GOV.0245437-2.getOrganizationList.1";
        producer = ConfigurationHelper.parseProducerMember(serviceId);
        assertEquals(serviceId, producer.toString());
        assertEquals("FI_PILOT", producer.getXRoadInstance());
        assertEquals("GOV", producer.getMemberClass());
        assertEquals("0245437-2", producer.getMemberCode());
        assertEquals(null, producer.getSubsystemCode());
        assertEquals("getOrganizationList", producer.getServiceCode());
        assertEquals("1", producer.getServiceVersion());

        serviceId = "FI_PILOT.GOV.0245437-2.getOrganizationList.1_0";
        producer = ConfigurationHelper.parseProducerMember(serviceId);
        assertEquals(serviceId, producer.toString());
        assertEquals("FI_PILOT", producer.getXRoadInstance());
        assertEquals("GOV", producer.getMemberClass());
        assertEquals("0245437-2", producer.getMemberCode());
        assertEquals(null, producer.getSubsystemCode());
        assertEquals("getOrganizationList", producer.getServiceCode());
        assertEquals("1_0", producer.getServiceVersion());

        serviceId = "FI_PILOT.GOV.0245437-2.getOrganizationList.1-0";
        producer = ConfigurationHelper.parseProducerMember(serviceId);
        assertEquals(serviceId, producer.toString());
        assertEquals("FI_PILOT", producer.getXRoadInstance());
        assertEquals("GOV", producer.getMemberClass());
        assertEquals("0245437-2", producer.getMemberCode());
        assertEquals(null, producer.getSubsystemCode());
        assertEquals("getOrganizationList", producer.getServiceCode());
        assertEquals("1-0", producer.getServiceVersion());

        serviceId = "FI_PILOT.GOV.0245437-2.getOrganizationList.ve1";
        producer = ConfigurationHelper.parseProducerMember(serviceId);
        assertEquals(serviceId, producer.toString());
        assertEquals("FI_PILOT", producer.getXRoadInstance());
        assertEquals("GOV", producer.getMemberClass());
        assertEquals("0245437-2", producer.getMemberCode());
        assertEquals("getOrganizationList", producer.getSubsystemCode());
        assertEquals("ve1", producer.getServiceCode());
        assertEquals(null, producer.getServiceVersion());

        serviceId = "FI_PILOT.GOV";
        producer = ConfigurationHelper.parseProducerMember(serviceId);
        assertEquals(null, producer);

        serviceId = "FI_PILOT.GOV.0245437-2";
        producer = ConfigurationHelper.parseProducerMember(serviceId);
        assertEquals(null, producer);

        serviceId = "FI_PILOT.GOV.0245437-2.subsystem.service.v1.0";
        producer = ConfigurationHelper.parseProducerMember(serviceId);
        assertEquals(null, producer);
    }
}
