package com.pkrete.xrd4j.common.exception;

/**
 * This class extends the Exception class and it represents a generic
 * XRd4J exception.
 *
 * @author Petteri Kivimäki
 */
public class XRd4JException extends Exception {

    /**
     * Constructs and initializes a new XRd4JException object
     * with the given error message.
     * @param message error message that's shown
     */
    public XRd4JException(final String message) {
        super(message);
    }
}
