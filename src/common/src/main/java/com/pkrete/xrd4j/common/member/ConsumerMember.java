/**
 * MIT License
 *
 * Copyright (C) 2014 Petteri Kivimäki
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
    public ConsumerMember(SDSBInstance sdsbInstance, MemberClass memberClass, String memberCode) throws XRd4JException {
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
    public ConsumerMember(SDSBInstance sdsbInstance, MemberClass memberClass, String memberCode, String subsystemCode) throws XRd4JException {
        super(sdsbInstance, memberClass, memberCode, subsystemCode);
    }

    @Override
    /**
     * Returns a String presentation of this ConsumerMember object.
     * @return String presentation of this ConsumerMember object
     */
    public String toString() {
        StringBuilder builder = new StringBuilder(super.sdsbInstance.toString()).append(".");
        builder.append(super.memberClass.toString()).append(".");
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
