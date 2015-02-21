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
        logger.debug("Build new request entity.");

        // If request body is not null or empty
        if (requestBody != null && !requestBody.isEmpty()) {
            logger.debug("Request body found.");
            // Set content type of the request, default is "application/xml"
            String reqContentType = "application/xml";
            if (headers != null && !headers.isEmpty()) {
                if (headers.get("Content-Type") != null && !headers.get("Content-Type").isEmpty()) {
                    reqContentType = headers.get("Content-Type");
                } else {
                    logger.warn("\"Content-Type\" header is missing. Use \"application/xml\" as default.");
                    // No value set, use default value
                    headers.put("Content-Type", reqContentType);
                }
            }
            // Create request entity that's used as request body
            return new StringEntity(requestBody, ContentType.create(reqContentType, Consts.UTF_8));
        }
        logger.debug("No request body found for request. Null is returned");
        return null;
    }
}
