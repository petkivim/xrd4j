package com.pkrete.xrd4j.common.util;

import com.pkrete.xrd4j.common.member.ConsumerMember;
import com.pkrete.xrd4j.common.member.ProducerMember;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class offers helper methods for handling configuration related
 * operations.
 *
 * @author Petteri Kivimäki
 */
public class ConfigurationHelper {

    private final static Logger logger = LoggerFactory.getLogger(ConfigurationHelper.class);

    /**
     * Copies the client id string into an array. [0] = instance, [1] =
     * memberClass, [2] = memberCode, [3] = subsystem. If the structure of the
     * string is not correct, null is returned.
     *
     * @param clientId client id string
     * @return client id in an array
     */
    private static String[] clientIdToArr(String clientId) {
        if (clientId == null) {
            return null;
        }
        String[] clientArr = clientId.split("\\.");
        if (clientArr.length == 3 || clientArr.length == 4) {
            return clientArr;
        }
        return null;
    }

    /**
     * Parses the given client id string and creates a new ConsumerMember
     * according to its value. Null is returned if the given string doesn't
     * contain a valid client id.
     *
     * @param clientId String containing a client id
     * @return new ProducerMember object or null
     */
    public static ConsumerMember parseConsumerMember(String clientId) {
        String[] clientIdArr = ConfigurationHelper.clientIdToArr(clientId);
        if (clientIdArr == null) {
            logger.warn("Client can not be null.");
            return null;
        } else {
            try {
                ConsumerMember consumer = null;
                String instance = clientIdArr[0];
                String memberClass = clientIdArr[1];
                String memberCode = clientIdArr[2];
                if (clientIdArr.length == 3) {
                    consumer = new ConsumerMember(instance, memberClass, memberCode);
                    logger.debug("Consumer member succesfully created. Identifier format : \"instance.memberClass.memberCode\".");
                } else if (clientIdArr.length == 4) {
                    String subsystem = clientIdArr[3];
                    consumer = new ConsumerMember(instance, memberClass, memberCode, subsystem);
                    logger.debug("Consumer member succesfully created. Identifier format : \"instance.memberClass.memberCode.subsystem\".");
                }
                return consumer;
            } catch (Exception ex) {
                logger.warn("Creating consumer member failed.");
                return null;
            }
        }
    }

    /**
     * Copies the client id string into an array. [0] = instance, [1] =
     * memberClass, [2] = memberCode, [3] = subsystem, [4] = service, [5] =
     * version. If the structure of the string is not correct, null is returned.
     *
     * @param serviceId service id string
     * @return service id in an array
     */
    private static String[] serviceIdToArr(String serviceId) {
        if (serviceId == null) {
            return null;
        }
        String[] serviceArr = serviceId.split("\\.");
        if (serviceArr.length >= 4 && serviceArr.length <= 6) {
            return serviceArr;
        }
        return null;
    }

    /**
     * Parses the given service id string and creates a new ProducerMember
     * according to its value. Null is returned if the given string doesn't
     * contain a valid service id.
     *
     * @param serviceId String containing a service id
     * @return new ProducerMember object or null
     */
    public static ProducerMember parseProducerMember(String serviceId) {
        String[] serviceIdArr = ConfigurationHelper.serviceIdToArr(serviceId);
        if (serviceIdArr == null) {
            logger.warn("Service can not be null.");
            return null;
        } else {
            try {
                ProducerMember producer = null;
                String instance = serviceIdArr[0];
                String memberClass = serviceIdArr[1];
                String memberCode = serviceIdArr[2];
                if (serviceIdArr.length == 4) {
                    String service = serviceIdArr[3];
                    producer = new ProducerMember(instance, memberClass, memberCode, service);
                    logger.debug("Producer member succesfully created. Identifier format : \"instance.memberClass.memberCode.service\".");
                } else if (serviceIdArr.length == 5) {
                    // Last element is considered as version number if it
                    // starts with the letters [vV] and besides that contains
                    // only numbers, or if the element contains only numbers.
                    // Also characters [-_] are allowed.
                    if (serviceIdArr[4].matches("(v|V|)[\\d_-]+")) {
                        String service = serviceIdArr[3];
                        String version = serviceIdArr[4];
                        producer = new ProducerMember(instance, memberClass, memberCode, "subsystem", service, version);
                        producer.setSubsystemCode(null);
                        logger.debug("Producer member succesfully created. Identifier format : \"instance.memberClass.memberCode.service.version\".");
                    } else {
                        String subsystem = serviceIdArr[3];
                        String service = serviceIdArr[4];
                        producer = new ProducerMember(instance, memberClass, memberCode, subsystem, service);
                        logger.debug("Producer member succesfully created. Identifier format : \"instance.memberClass.memberCode.subsystem.service\".");
                    }
                } else if (serviceIdArr.length == 6) {
                    String subsystem = serviceIdArr[3];
                    String service = serviceIdArr[4];
                    String version = serviceIdArr[5];
                    producer = new ProducerMember(instance, memberClass, memberCode, subsystem, service, version);
                    logger.debug("Producer member succesfully created. Identifier format : \"instance.memberClass.memberCode.subsystem.service.version\".");
                }
                return producer;
            } catch (Exception ex) {
                logger.warn("Creating producer member failed.");
                return null;
            }
        }
    }
}
