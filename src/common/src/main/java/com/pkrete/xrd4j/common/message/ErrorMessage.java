package com.pkrete.xrd4j.common.message;

/**
 * This class represents an error message returned by the service producer
 * or by the security server.
 *
 * @author Petteri Kivimäki
 */
public class ErrorMessage {

    /**
     * Type of this error message: standard SOAP error message or
     * non-technical SOAP error message.
     */
    private ErrorMessageType errorMessageType;
    /**
     * A code for identifying the fault.
     */
    private String faultCode;
    /**
     * A human readable explanation of the fault.
     */
    private String faultString;
    /**
     * Information about who caused the fault to happen.
     */
    private String faultActor;
    /**
     * Holds application specific error information related to the Body element.
     */
    private Object detail;

    /**
     * Constructs and initiates a new non-technical SOAP error message.
     * @param faultCode code for identifying the fault
     * @param faultString human readable explanation of the fault
     */
    public ErrorMessage(String faultCode, String faultString) {
        this.faultCode = faultCode;
        this.faultString = faultString;
        this.errorMessageType = ErrorMessageType.NON_TECHNICAL_SOAP_ERROR_MESSAGE;

    }

    /**
     * Constructs and initiates a new standard SOAP error message.
     * @param faultCode code for identifying the fault
     * @param faultString human readable explanation of the fault
     * @param faultFactor identity of the component that created the error
     * message
     * @param detail application specific error information related to the
     * Body element
     */
    public ErrorMessage(String faultCode, String faultString, String faultFactor, Object detail) {
        this.faultCode = faultCode;
        this.faultString = faultString;
        this.faultActor = faultFactor;
        this.detail = detail;
        this.errorMessageType = ErrorMessageType.STANDARD_SOAP_ERROR_MESSAGE;

    }

    /**
     * Returns the type of this ErrorMessage.
     * @return error message type
     */
    public ErrorMessageType getErrorMessageType() {
        return errorMessageType;
    }

    /**
     * Returns he code for identifying the fault.
     * @return code for identifying the fault
     */
    public String getFaultCode() {
        return faultCode;
    }

    /**
     * Sets the code for identifying the fault.
     * @param faultcode new value
     */
    public void setFaultCode(String faultcode) {
        this.faultCode = faultcode;
    }

    /**
     * Returns a human readable explanation of the fault.
     * @return human readable explanation of the fault
     */
    public String getFaultString() {
        return faultString;
    }

    /**
     * Sets the human readable explanation of the fault.
     * @param faultstring new value
     */
    public void setFaultString(String faultstring) {
        this.faultString = faultstring;
    }

    /**
     * Returns the identity of the component that created the error message.
     * @return identity of the component that created the error message
     */
    public String getFaultActor() {
        return faultActor;
    }

    /**
     * Sets the identity of the component that created the error message.
     * @param faultactor new value
     */
    public void setFaultActor(String faultactor) {
        this.faultActor = faultactor;
    }

    /**
     * Returns application specific error information related to the Body
     * element.
     * @return application specific error information related to the Body
     * element
     */
    public Object getDetail() {
        return detail;
    }

    /**
     * Sets the application specific error information related to the Body
     * element.
     * @param detail new value
     */
    public void setDetail(Object detail) {
        this.detail = detail;
    }
}
