package com.pkrete.xrd4j.common.exception;

/**
 * This class extends the RuntimeException class and it represents a generic
 * XRd4J exception.
 *
 * @author Petteri Kivimäki
 */
public class XRd4JRuntimeException extends RuntimeException {

    /**
     * Constructs and initializes a new XRd4JRuntimeException object with the
     * given error message.
     *
     * @param message error message that's shown
     */
    public XRd4JRuntimeException(final String message) {
        super(message);
    }
}
