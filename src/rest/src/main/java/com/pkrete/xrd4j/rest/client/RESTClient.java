package com.pkrete.xrd4j.rest.client;

import com.pkrete.xrd4j.rest.ClientResponse;
import java.util.Map;

/**
 * This class defines an interface for all the REST client classes that
 * implement GET, POST, PUT and DELETE clients.
 *
 * @author Petteri Kivimäki
 */
@FunctionalInterface
public interface RESTClient {

    /**
     * Makes a HTTP request to the given URL using the given request body,
     * parameters and HTTP headers. The parameters are used as URL parameters,
     * but if there's a parameter "resourceId", it's added directly to the end
     * of the URL. If there's no request body, the value can be null.
     *
     * @param url URL where the request is sent
     * @param params request parameters
     * @param requestBody request body
     * @param headers HTTP headers to be added to the request
     * @return response as string
     */
    public ClientResponse send(String url, String requestBody, Map<String, ?> params, Map<String, String> headers);
}
