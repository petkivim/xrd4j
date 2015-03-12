package com.pkrete.xrd4j.common.message;

/**
 * This enum represents SOAP error message types.
 *
 * @author Petteri Kivimäki
 */
public enum ErrorMessageType {

    /**
     * Standard SOAP error message, SOAP fault.
     */
    STANDARD_SOAP_ERROR_MESSAGE,
    /**
     * Non-technical SOAP error message.
     */
    NON_TECHNICAL_SOAP_ERROR_MESSAGE;
}
