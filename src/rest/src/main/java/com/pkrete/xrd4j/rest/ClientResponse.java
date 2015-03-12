package com.pkrete.xrd4j.rest;

/**
 * This class represents the response returned by HTTP clients.
 *
 * @author Petteri Kivimäki
 */
public class ClientResponse {

    private String data;
    private String contentType;
    private int statusCode;
    private String reasonPhrase;

    /**
     * Constructs and initializes a new ClientResponse object.
     * @param data response payload
     * @param contentType response content type
     * @param statusCode HTTP status code of the response
     * @param reasonPhrase reason phrase of this response
     */
    public ClientResponse(String data, String contentType, int statusCode, String reasonPhrase) {
        this.data = data;
        this.contentType = contentType;
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    /**
     * Returns the payload of the response message.
     * @return payload of the response message
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the payload of the response message.
     * @param data new value
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Returns the content type of the response message.
     * @return content type of the response message
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the content type of the response message.
     * @param contentType new value
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Returns the HTTP status code of the response message.
     * @return HTTP status code of the response message
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Updates the status line of this response with a new status code.
     * @param statusCode new value
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Returns the reason phrase of this response.
     * @return reason phrase of this response
     */
    public String getReasonPhrase() {
        return reasonPhrase;
    }

    /**
     * Updates this response with a new reason phrase.
     * @param reasonPhrase new value
     */
    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }
}
