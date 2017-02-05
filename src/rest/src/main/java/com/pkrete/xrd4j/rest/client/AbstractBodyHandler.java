package com.pkrete.xrd4j.rest.client;

import java.util.Map;
import org.apache.http.Consts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This abstract class offers method for adding HTTP request body to the HTTP
 * request. Classes implementing POST, PUT or DELETE requests can extend this
 * class.
 *
 * @author Petteri Kivimäki
 */
public abstract class AbstractBodyHandler extends AbstractClient {

    private static final Logger logger = LoggerFactory.getLogger(AbstractBodyHandler.class);

    /**
     * Builds a new StringEntity object that's used as HTTP request body.
     * Content type of the request is set according to the given headers. If the
     * given headers do not contain Content-Type header, "application/xml" is
     * used. If the given request body is null or empty, null is returned.
     *
     * @param requestBody request body
     * @param headers HTTP headers to be added to the request
     * @return new StringEntity object or null
     */
    protected StringEntity buildRequestEntity(String requestBody, Map<String, String> headers) {
        String contentTypeHeader = "Content-Type";
        logger.debug("Build new request entity.");

        // If request body is not null or empty
        if (requestBody != null && !requestBody.isEmpty()) {
            logger.debug("Request body found.");
            // Set content type of the request, default is "application/xml"
            String reqContentType = "application/xml";
            if (headers != null && !headers.isEmpty()) {
                if (headers.get(contentTypeHeader) != null && !headers.get(contentTypeHeader).isEmpty()) {
                    reqContentType = headers.get(contentTypeHeader);
                } else {
                    logger.warn("\"Content-Type\" header is missing. Use \"application/xml\" as default.");
                    // No value set, use default value
                    headers.put(contentTypeHeader, reqContentType);
                }
            }
            // Create request entity that's used as request body
            return new StringEntity(requestBody, ContentType.create(reqContentType, Consts.UTF_8));
        }
        logger.debug("No request body found for request. Null is returned");
        return null;
    }
}
