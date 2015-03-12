package com.pkrete.xrd4j.common.member;

/**
 * Enumeration for SDSB identifier types. SDSB members don't have a fixed
 * identifier type as the type is defined in a context of a single request.
 * Depending on the context the same member can have different identifier type.
 * In most cases the member acting as a consumer represents SUBSYSTEM and the
 * member acting as a provider represents SERVICE.
 *
 * @author Petteri Kivimäki
 */
public enum ObjectType {

    /**
     * The identifier consists of SDSBInstance code, member class and member
     * code.
     */
    MEMBER,
    /**
     * The identifier consists of SDSBInstance code, member class, member
     * code and subsystem code.
     */
    SUBSYSTEM,
    /**
     * The identifier consists of SDSBInstance code, member class, member
     * code, subsystem code (OPTIONAL), service code and service version
     * (OPTIONAL).
     */
    SERVICE,
    /**
     * The identifier consists of SDSBInstance code and service code.
     * (OPTIONAL).
     */
    CENTRALSERVICE;
}
