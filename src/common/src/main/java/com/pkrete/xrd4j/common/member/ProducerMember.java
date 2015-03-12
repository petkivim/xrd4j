package com.pkrete.xrd4j.common.member;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.util.ValidationHelper;
import java.io.Serializable;

/**
 * This class represent X-Road producer member that produces services to
 * X-Road. The services are invoked by ConsumerMembers by sending
 * ServiceRequests. This class identifies the producer member and the
 * subsystem, service and service version that's called by the consumer
 * member.
 *
 * @author Petteri Kivimäki
 */
public class ProducerMember extends AbstractMember implements Serializable {

    /**
     * Namespace prefix of this member
     */
    private String namespacePrefix;
    /**
     * Namespace URL of this member
     */
    private String namespaceUrl;
    /**
     * Code that uniquely identifies a service offered by given SDSB member
     * or subsystem. Optional.
     */
    private String serviceCode;
    /**
     * Version of the service. Optional.
     */
    private String serviceVersion;

    /**
     * Constructs and initializes a new ProducerMember that represents
     * a central service offered by the central servers.
     * @param sdsbInstance identifier of this SDSB instance
     * @param serviceCode unique service code
     * @throws XRd4JException if there's a XRd4J error
     */
    public ProducerMember(String sdsbInstance, String serviceCode) throws XRd4JException {
        super(sdsbInstance);
        this.serviceCode = serviceCode;
        ValidationHelper.validateStrNotNullOrEmpty(serviceCode, "serviceCode");
    }

    /**
     * Constructs and initializes a new ProducerMember.
     * @param sdsbInstance identifier of this SDSB instance
     * @param memberClass type of this member
     * @param memberCode unique member code
     * @param serviceCode code that uniquely identifies a service offered by
     * this member
     * @throws XRd4JException if there's a XRd4J error
     */
    public ProducerMember(String sdsbInstance, String memberClass, String memberCode, String serviceCode) throws XRd4JException {
        super(sdsbInstance, memberClass, memberCode);
        this.serviceCode = serviceCode;
        ValidationHelper.validateStrNotNullOrEmpty(serviceCode, "serviceCode");

    }

    /**
     * Constructs and initializes a new ProducerMember.
     * @param sdsbInstance identifier of this SDSB instance
     * @param memberClass type of this member
     * @param memberCode unique member code
     * @param subsystemCode subsystem code that uniquely identifies a
     * subsystem of this member
     * @param serviceCode code that uniquely identifies a service offered by
     * the given susbsystem of this member
     * @throws XRd4JException if there's a XRd4J error
     */
    public ProducerMember(String sdsbInstance, String memberClass, String memberCode, String subsystemCode, String serviceCode) throws XRd4JException {
        super(sdsbInstance, memberClass, memberCode, subsystemCode);
        this.serviceCode = serviceCode;
        ValidationHelper.validateStrNotNullOrEmpty(serviceCode, "serviceCode");
    }

    /**
     * Constructs and initializes a new ProducerMember.
     * @param sdsbInstance identifier of this SDSB instance
     * @param memberClass type of this member
     * @param memberCode unique member code
     * @param subsystemCode subsystem code that uniquely identifies a
     * subsystem of this member
     * @param serviceCode code that uniquely identifies a service offered by
     * the given susbsystem of this member
     * @param serviceVersion version of the service
     * @throws XRd4JException if there's a XRd4J error
     */
    public ProducerMember(String sdsbInstance, String memberClass, String memberCode, String subsystemCode, String serviceCode, String serviceVersion) throws XRd4JException {
        super(sdsbInstance, memberClass, memberCode, subsystemCode);
        this.serviceCode = serviceCode;
        this.serviceVersion = serviceVersion;
        ValidationHelper.validateStrNotNullOrEmpty(serviceCode, "serviceCode");
        ValidationHelper.validateStrNotNullOrEmpty(serviceVersion, "serviceVersion");
    }

    /**
     * Returns the version number of the service.
     * @return version number of the service
     */
    public String getServiceVersion() {
        return serviceVersion;
    }

    /**
     * Sets the version number of the service.
     * @param serviceVersion new value
     */
    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    /**
     * Returns a code that uniquely identifies a service offered by this
     * member.
     * @return code that uniquely identifies a service offered by this
     * member
     */
    public String getServiceCode() {
        return serviceCode;
    }

    /**
     * Sets the code that uniquely identifies a service offered by this
     * member.
     * @param serviceCode new value
     */
    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    /**
     * Returns the namespace prefix of this ProducerMember.
     * @return namespace prefix of this member
     */
    public String getNamespacePrefix() {
        return namespacePrefix;
    }

    /**
     * Sets the namespace prefix of this ProducerMember.
     * @param namespacePrefix new value
     */
    public void setNamespacePrefix(String namespacePrefix) {
        this.namespacePrefix = namespacePrefix;
    }

    /**
     * Returns the namespace URL of this ProducerMember.
     * @return namespace URL of this member
     */
    public String getNamespaceUrl() {
        return namespaceUrl;
    }

    /**
     * Sets the namespace URL of this ProducerMember,
     * @param namespaceUrl new value
     */
    public void setNamespaceUrl(String namespaceUrl) {
        this.namespaceUrl = namespaceUrl;
    }

    @Override
    /**
     * Returns a String presentation of this ProducerMember object.
     * @return String presentation of this ProducerMember object
     */
    public String toString() {
        StringBuilder builder = new StringBuilder(super.sdsbInstance).append(".");
        builder.append(super.memberClass != null ? super.memberClass + "." : "");
        builder.append(super.memberCode != null ? super.memberCode + "." : "");
        builder.append(super.subsystemCode != null ? super.subsystemCode + "." : "");
        builder.append(this.getServiceCode());
        builder.append(this.getServiceVersion() != null ? "." + this.getServiceVersion() : "");
        return builder.toString();
    }

    @Override
    /**
     * Indicates whether some other object is "equal to" this ProducerMember.
     * @param o the reference object with which to compare
     * @return true only if the specified object is also an ProducerMember
     * and it has the same id as this ProducerMember
     */
    public boolean equals(Object o) {
        if (o instanceof ProducerMember && this.toString().equals(((ProducerMember) o).toString())) {
            return true;
        }
        return false;
    }

    @Override
    /**
     * Returns a hash code value for the object.
     * @return a hash code value for this object
     */
    public int hashCode() {
        return this.toString().hashCode();
    }
}
