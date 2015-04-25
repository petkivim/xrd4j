package com.pkrete.xrd4j.rest.client;

import java.util.Map;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class offers a REST client for HTTP GET requests.
 *
 * @author Petteri Kivimäki
 */
public class GetClient extends AbstractClient {

    private static final Logger logger = LoggerFactory.getLogger(GetClient.class);

    /**
     * Builds a new HTTP GET request with the given URL. Request body and
     * headers are omitted.
     *
     * @param url URL where the request is sent
     * @param requestBody request body
     * @param headers HTTP headers to be added to the request
     * @return new HttpUriRequest object
     */
    @Override
    protected HttpUriRequest buildtHttpRequest(String url, String requestBody, Map<String, String> headers) {
        logger.debug("Build new HTTP GET request.");
        // Create a new post request
        return RequestBuilder.get().setUri(url).build();
    }
}
