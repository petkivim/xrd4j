package com.pkrete.xrd4j.common.exception;

/**
 * This class extends the Exception class and it represents an exception related
 * to ProcuderMember and ConsumerMember objects.
 *
 * @author Petteri Kivimäki
 */
public class XRd4JMissingMemberException extends Exception {

    /**
     * Constructs and initializes a new XRd4JMissingMemberException object
     * with the given error message.
     * @param message error message that's shown
     */
    public XRd4JMissingMemberException(final String message) {
        super(message);
    }
}
