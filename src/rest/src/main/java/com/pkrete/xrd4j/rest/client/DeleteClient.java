package com.pkrete.xrd4j.rest.client;

import java.util.Map;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class offers a REST client for HTTP DELETE requests.
 *
 * @author Petteri Kivimäki
 */
public class DeleteClient extends AbstractBodyHandler {

    private static final Logger logger = LoggerFactory.getLogger(DeleteClient.class);

    /**
     * Builds a new HTTP DELETE request with the given URL and request body.
     * Content type of the request is set according to the given headers. If the
     * given headers do not contain Content-Type header, "application/xml" is
     * used.
     *
     * @param url URL where the request is sent
     * @param requestBody request body
     * @param headers HTTP headers to be added to the request
     * @return new HttpUriRequest object
     */
    @Override
    protected HttpUriRequest buildtHttpRequest(String url, String requestBody, Map<String, String> headers) {
        logger.debug("Build new HTTP DELETE request.");
        HttpUriRequest request;
        // Create request entity that's used as request body
        StringEntity requestEntity = super.buildRequestEntity(requestBody, headers);
        if (requestEntity != null) {
            request = RequestBuilder.delete().setUri(url).setEntity(requestEntity).build();
        } else {
            logger.debug("No request body found for HTTP DELETE request.");
            request = RequestBuilder.delete().setUri(url).build();
        }
        // Return request
        return request;
    }
}
