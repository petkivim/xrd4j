package com.pkrete.xrd4j.common.member;

/**
 * Enumeration for X-Road identifier types. X-Road members don't have a fixed
 * identifier type as the type is defined in a context of a single request.
 * Depending on the context the same member can have different identifier type.
 * In most cases the member acting as a consumer represents SUBSYSTEM and the
 * member acting as a provider represents SERVICE.
 *
 * @author Petteri Kivimäki
 */
public enum ObjectType {

    /**
     * The identifier consists of xRoadInstance code, member class and member
     * code.
     */
    MEMBER,
    /**
     * The identifier consists of xRoadInstance code, member class, member code
     * and subsystem code.
     */
    SUBSYSTEM,
    /**
     * The identifier consists of xRoadInstance code, member class, member code,
     * subsystem code (OPTIONAL), service code and service version (OPTIONAL).
     */
    SERVICE,
    /**
     * The identifier consists of xRoadInstance code and service code.
     * (OPTIONAL).
     */
    CENTRALSERVICE,
    /**
     * The identifier consists of xRoadInstance code, member class, member code
     * and security server code.
     */
    SERVER;
}
