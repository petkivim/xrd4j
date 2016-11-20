package com.pkrete.xrd4j.common.member;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.util.Constants;
import com.pkrete.xrd4j.common.util.ValidationHelper;
import java.io.Serializable;

/**
 * This class represents X-Road security server that hosts members and services.
 * This class identifies the security server where the message is sent.
 *
 * @author Petteri Kivimäki
 */
public class SecurityServer extends AbstractMember implements Serializable {

    /**
     * Identifies the security server.
     */
    protected String serverCode;

    /**
     * Constructs and initializes a new SecurityServer.
     *
     * @param xRoadInstance identifier of this X-Road instance
     * @param memberClass type of this member
     * @param memberCode unique member code
     * @param serverCode unique security server code
     * @throws XRd4JException if there's a XRd4J error
     */
    public SecurityServer(String xRoadInstance, String memberClass, String memberCode, String serverCode) throws XRd4JException {
        super(xRoadInstance, memberClass, memberCode);
        this.serverCode = serverCode;
        ValidationHelper.validateStrNotNullOrEmpty(this.serverCode, Constants.NS_ID_ELEM_SERVER_CODE);
    }

    /**
     * Returns the code that uniquely identifies security server.
     *
     * @return code that uniquely identifies security server
     */
    public String getServerCode() {
        return this.serverCode;
    }

    /**
     * Sets the code that uniquely identifies security server.
     *
     * @param serverCode new value
     */
    public void setServerCode(String serverCode) {
        this.serverCode = serverCode;
    }

    @Override
    /**
     * Returns a String presentation of this SecurityServer object.
     *
     * @return String presentation of this SecurityServer object
     */
    public String toString() {
        StringBuilder builder = new StringBuilder(super.xRoadInstance).append(".");
        builder.append(super.memberClass).append(".");
        builder.append(super.memberCode).append(".");
        builder.append(this.serverCode);
        return builder.toString();
    }

    @Override
    /**
     * Indicates whether some other object is "equal to" this SecurityServer.
     *
     * @param o the reference object with which to compare
     * @return true only if the specified object is also an SecurityServer and
     * it has the same id as this SecurityServer
     */
    public boolean equals(Object o) {
        if (o instanceof SecurityServer && this.toString().equals(((SecurityServer) o).toString())) {
            return true;
        }
        return false;
    }

    @Override
    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    public int hashCode() {
        return this.toString().hashCode();
    }
}
