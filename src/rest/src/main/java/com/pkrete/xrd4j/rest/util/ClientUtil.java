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
package com.pkrete.xrd4j.rest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class contains utility methods for REST clients.
 *
 * @author Petteri Kivimäki
 */
public class ClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(ClientUtil.class);

    /**
     * Extracts the response string from the given HttpEntity.
     * @param entity HttpEntity that contains the response
     * @return response String
     */
    public static String getResponseString(HttpEntity entity) {
        StringBuilder builder = new StringBuilder();
        if (entity != null) {
            String inputLine;
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(entity.getContent()));
                while ((inputLine = in.readLine()) != null) {
                    builder.append(inputLine);
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        }
        return builder.toString();
    }

    /**
     * Builds the target URL based on the given based URL and parameters Map.
     * @param url base URL
     * @param params URL parameters
     * @return comlete URL containing the base URL with all the parameters
     * appended
     */
    public static String buildTargetURL(String url, Map<String, String> params) {
        logger.debug("Target URL : \"{}\".", url);
        if (params == null || params.isEmpty()) {
            logger.debug("URL parameters list is null or empty. Nothing to do here. Return target URL.");
            return url;
        }
        if (params.containsKey("resourceId")) {
            logger.debug("Resource ID found from parameters map. Resource ID value : \"{}\".", params.get("resourceId"));
            if (!url.endsWith("/")) {
                url += "/";
            }
            url += params.get("resourceId");
            params.remove("resourceId");
            logger.debug("Resource ID added to URL : \"{}\".", url);
        }

        StringBuilder paramsString = new StringBuilder();
        for (String key : params.keySet()) {
            if (paramsString.length() > 0) {
                paramsString.append("&");
            }
            paramsString.append(key).append("=").append(params.get(key));
            logger.debug("Parameter : \"{}\"=\"{}\"", key, params.get(key));
        }

        if (!url.contains("?") && !params.isEmpty()) {
            url += "?";
        } else if (url.contains("?") && !params.isEmpty()) {
            if (!url.endsWith("?") && !url.endsWith("&")) {
                url += "&";
            }
        }
        url += paramsString.toString();
        logger.debug("Request parameters added to URL : \"{}\".", url);
        return url;
    }
}
