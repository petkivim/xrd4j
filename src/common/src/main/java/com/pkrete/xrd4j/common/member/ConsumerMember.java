package com.pkrete.xrd4j.common.member;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import java.io.Serializable;

/**
 * This class represents X-Road consumer member that acts as a client that
 * initiates service call by sending a ServiceRequest. This class identifies
 * the consumer member and the subsystem that's acting as a client and
 * initiates the service call.
 *
 * @author Petteri Kivimäki
 */
public class ConsumerMember extends AbstractMember implements Serializable {

    /**
     * Constructs and initializes a new ConsumerMember.
     * @param sdsbInstance identifier of this SDSB instance
     * @param memberClass type of this member
     * @param memberCode unique member code
     * @throws XRd4JException if there's a XRd4J error
     */
    public ConsumerMember(String sdsbInstance, String memberClass, String memberCode) throws XRd4JException {
        super(sdsbInstance, memberClass, memberCode);
    }

    /**
     * Constructs and initializes a new ConsumerMember.
     * @param sdsbInstance identifier of this SDSB instance
     * @param memberClass type of this member
     * @param memberCode unique member code
     * @param subsystemCode subsystem code that uniquely identifies a
     * subsystem of this member
     * @throws XRd4JException if there's a XRd4J error
     */
    public ConsumerMember(String sdsbInstance, String memberClass, String memberCode, String subsystemCode) throws XRd4JException {
        super(sdsbInstance, memberClass, memberCode, subsystemCode);
    }

    @Override
    /**
     * Returns a String presentation of this ConsumerMember object.
     * @return String presentation of this ConsumerMember object
     */
    public String toString() {
        StringBuilder builder = new StringBuilder(super.xRoadInstance).append(".");
        builder.append(super.memberClass).append(".");
        builder.append(super.memberCode);
        builder.append(super.subsystemCode != null && !super.subsystemCode.isEmpty() ? "." + super.subsystemCode : "");
        return builder.toString();
    }

    @Override
    /**
     * Indicates whether some other object is "equal to" this ConsumerMember.
     * @param o the reference object with which to compare
     * @return true only if the specified object is also an ConsumerMember
     * and it has the same id as this ConsumerMember
     */
    public boolean equals(Object o) {
        if (o instanceof ConsumerMember && this.toString().equals(((ConsumerMember) o).toString())) {
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
