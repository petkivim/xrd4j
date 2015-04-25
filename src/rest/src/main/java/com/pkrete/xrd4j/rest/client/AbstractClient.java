package com.pkrete.xrd4j.rest.client;

import com.pkrete.xrd4j.rest.ClientResponse;
import com.pkrete.xrd4j.rest.util.ClientUtil;
import java.io.IOException;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is an abstract base class for classes implementing GET, POST, PUT and
 * DELETE HTTP clients.
 *
 * @author Petteri Kivimäki
 */
public abstract class AbstractClient implements RESTClient {
    
    private static final Logger logger = LoggerFactory.getLogger(AbstractClient.class);

    protected abstract HttpUriRequest buildtHttpRequest(String url, String requestBody, Map<String, String> headers);
    
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
    @Override
    public ClientResponse send(String url, String requestBody, Map<String, String> params, Map<String, String> headers) {       
        // Build target URL
        url = ClientUtil.buildTargetURL(url, params);

        // Create HTTP client
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Build request
        HttpUriRequest request = this.buildtHttpRequest(url, requestBody, headers);

        logger.info("Starting HTTP {} operation.", request.getMethod());
        
        // Add headers
        if (headers != null && !headers.isEmpty()) {
            for (String key : headers.keySet()) {
                logger.debug("Add header : \"{}\" = \"{}\"", key, headers.get(key));
                request.setHeader(key, headers.get(key));
            }
        }

        try {
            //Send the request; It will immediately return the response in HttpResponse object
            CloseableHttpResponse response = httpClient.execute(request);
            // Get Content-Type header
            Header[] contentTypeHeader = response.getHeaders("Content-Type");
            String contentType = null;
            // Check for null and empty
            if (contentTypeHeader != null && contentTypeHeader.length > 0) {
                contentType = contentTypeHeader[0].getValue();
            }
            // Get Status Code
            int statusCode = response.getStatusLine().getStatusCode();
            // Get reason phrase
            String reasonPhrase = response.getStatusLine().getReasonPhrase();

            // Get response payload
            String responseStr = ClientUtil.getResponseString(response.getEntity());

            response.close();
            httpClient.close();
            logger.debug("REST response content type: \"{}\".", contentType);
            logger.debug("REST response status code: \"{}\".", statusCode);
            logger.debug("REST response reason phrase: \"{}\".", reasonPhrase);
            logger.debug("REST response : \"{}\".", responseStr);
            logger.info("HTTP {} operation completed.", request.getMethod());
            return new ClientResponse(responseStr, contentType, statusCode, reasonPhrase);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            logger.warn("HTTP {} operation failed. An empty string is returned.", request.getMethod());
            return null;
        }
    }
}
