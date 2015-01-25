/**
 * MIT License
 *
 * Copyright (C) 2014 Petteri Kivimäki
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.pkrete.xrd4j.rest.client;

import com.pkrete.xrd4j.rest.ClientResponse;
import com.pkrete.xrd4j.rest.util.ClientUtil;
import java.io.IOException;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class offers a REST client for HTTP GET requests.
 *
 * @author Petteri Kivimäki
 */
public class GetClient {

    private static final Logger logger = LoggerFactory.getLogger(GetClient.class);

    /**
     * Makes a HTTP GET request to the given URL using the given parameters and.
     * The parameters are used as URL parameters, but if there's
     * a parameter "resourceId", it's added directly to tbe end of the URL.
     * @param url URL where the request is sent
     * @param params request parameters
     * @return response as string
     */
    public static String get(String url, Map<String, String> params) {
        return GetClient.get(url, params);
    }

    /**
     * Makes a HTTP GET request to the given URL using the given parameters and
     * accept header. The parameters are used as URL parameters, but if there's
     * a parameter "resourceId", it's added directly to tbe end of the URL.
     * @param url URL where the request is sent
     * @param params request parameters
     * @param headers HTTP headers to be added to the request
     * @return response as string
     */
    public static ClientResponse get(String url, Map<String, String> params, Map<String, String> headers) {
        logger.info("Starting HTTP GET operation.");
        // Build target URL
        url = ClientUtil.buildTargetURL(url, params);

        CloseableHttpClient httpClient = HttpClients.createDefault();

        //Define a HttpGet request; You can choose between HttpPost, HttpDelete or HttpPut also.
        //Choice depends on type of method you will be invoking.
        //Set the API media type in http accept header
        HttpUriRequest request = RequestBuilder.get().setUri(url).build();

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
            logger.info("HTTP GET operation completed.");
            return new ClientResponse(responseStr, contentType, statusCode, reasonPhrase);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            logger.warn("HTTP GET operation failed. An empty string is returned.");
            return null;
        }
    }
}
