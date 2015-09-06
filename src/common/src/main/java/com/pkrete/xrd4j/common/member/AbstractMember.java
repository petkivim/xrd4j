package com.pkrete.xrd4j.common.member;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.util.Constants;
import com.pkrete.xrd4j.common.util.ValidationHelper;
import java.io.Serializable;

/**
 * This is an abstract base class for classes representing X-Road consumer
 * and producer members.
 *
 * @author Petteri Kivimäki
 */
public abstract class AbstractMember implements Serializable {

    /**
     * Identifies the X-Road instance.
     */
    protected String xRoadInstance;
    /**
     * Member class defines the type of this member.
     */
    protected String memberClass;
    /**
     * Code that uniquely identifies a member of given member type.
     */
    protected String memberCode;
    /**
     * Code that uniquely identifies a subsystem of given SDSB member.
     */
    protected String subsystemCode;
    /**
     * SDSB identifier type.
     */
    protected ObjectType objectType;

    /**
     * Constructs and initializes a new AbstractMember.
     * @param xRoadInstance identifier of this X-Road instance
     * @throws XRd4JException if there's a XRd4J error
     */
    protected AbstractMember(String xRoadInstance) throws XRd4JException {
        this.xRoadInstance = xRoadInstance;
        ValidationHelper.validateStrNotNullOrEmpty(xRoadInstance, Constants.NS_ID_ELEM_XROAD_INSTANCE);
    }

    /**
     * Constructs and initializes a new AbstractMember.
     * @param xRoadInstance identifier of this X-Road instance
     * @param memberClass type of this member
     * @param memberCode unique member code
     * @throws XRd4JException if there's a XRd4J error
     */
    protected AbstractMember(String xRoadInstance, String memberClass, String memberCode) throws XRd4JException {
        this(xRoadInstance);
        this.memberClass = memberClass;
        this.memberCode = memberCode;
        ValidationHelper.validateStrNotNullOrEmpty(memberClass, Constants.NS_ID_ELEM_MEMBER_CLASS);
        ValidationHelper.validateStrNotNullOrEmpty(memberCode, Constants.NS_ID_ELEM_MEMBER_CODE);
    }

    /**
     * Constructs and initializes a new AbstractMember.
     * @param xRoadInstance identifier of this X-Road instance
     * @param memberClass type of this member
     * @param memberCode unique member code
     * @param subsystemCode subsystem code that uniquely identifies a
     * subsystem of this member
     * @throws XRd4JException if there's a XRd4J error
     */
    protected AbstractMember(String xRoadInstance, String memberClass, String memberCode, String subsystemCode) throws XRd4JException {
        this(xRoadInstance, memberClass, memberCode);
        this.subsystemCode = subsystemCode;
        ValidationHelper.validateStrNotNullOrEmpty(subsystemCode, Constants.NS_ID_ELEM_SUBSYSTEM_CODE);
    }

    /**
     * Returns the X-Road instance of this member.
     * @return xRoadInstance of this member
     */
    public String getXRoadInstance() {
        return xRoadInstance;
    }

    /**
     * Sets the X-Road instance of this member.
     * @param xRoadInstance new value
     */
    public void setXRoadInstance(String xRoadInstance) {
        this.xRoadInstance = xRoadInstance;
    }

    /**
     * Returns the member class of this member. Member class represents
     * the type of this member: company, government institution, private
     * person, etc.
     * @return member class of this member
     */
    public String getMemberClass() {
        return memberClass;
    }

    /**
     * Sets the member class of this member. Member class represents
     * the type of this member: company, government institution, private
     * person, etc.
     * @param memberClass new value
     */
    public void setMemberClass(String memberClass) {
        this.memberClass = memberClass;
    }

    /**
     * Returns the code that uniquely identifies this member.
     * @return code that uniquely identifies this member
     */
    public String getMemberCode() {
        return memberCode;
    }

    /**
     * Sets the code that uniquely identifies this member.
     * @param memberCode new value
     */
    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    /**
     * Returns the code that uniquely identifies a subsystem of this member.
     * @return code that uniquely identifies a subsystem of this member
     */
    public String getSubsystemCode() {
        return subsystemCode;
    }

    /**
     * Sets the code that uniquely identifies a subsystem of this member
     * @param subsystemCode new value
     */
    public void setSubsystemCode(String subsystemCode) {
        this.subsystemCode = subsystemCode;
    }

    /**
     * Returns the SDSB identifier type of this message. The identifier type
     * is automatically set by request/response serializers and deserializers
     * and it should never be set manually.
     * @return SDSB identifier type of this message
     */
    public ObjectType getObjectType() {
        return objectType;
    }

    /**
     * Sets the SDSB identifier type of this message. The identifier type
     * is automatically set by request/response serializers and deserializers
     * and it should never be set manually.
     * @param objectType new value
     */
    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }
}
