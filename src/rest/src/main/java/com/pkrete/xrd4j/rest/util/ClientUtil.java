package com.pkrete.xrd4j.rest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
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
     *
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
     *
     * @param url base URL
     * @param params URL parameters
     * @return complete URL containing the base URL with all the parameters
     * appended
     */
    public static String buildTargetURL(String url, Map<String, ?> params) {
        logger.debug("Target URL : \"{}\".", url);
        if (params == null || params.isEmpty()) {
            logger.debug("URL parameters list is null or empty. Nothing to do here. Return target URL.");
            return url;
        }
        if (params.containsKey("resourceId")) {
            String resourceId = null;
            // Get resource id
            if (params.get("resourceId") instanceof List) {
                resourceId = (String) ((List) params.get("resourceId")).get(0);
            } else {
                resourceId = (String) params.get("resourceId");
            }
            // Remove line breaks and omit leading and trailing whitespaces
            resourceId = resourceId.replaceAll("\\r\\n|\\r|\\n", "").trim();
            logger.debug("Resource ID found from parameters map. Resource ID value : \"{}\".", resourceId);
            if (!url.endsWith("/")) {
                url += "/";
            }
            // Add resource id
            url += resourceId;
            params.remove("resourceId");
            logger.debug("Resource ID added to URL : \"{}\".", url);
        }

        StringBuilder paramsString = new StringBuilder();
        for (String key : params.keySet()) {
            if (params.get(key) instanceof List) {
                for (String value : (List<String>) params.get(key)) {
                    if (paramsString.length() > 0) {
                        paramsString.append("&");
                    }
                    // Remove line breaks and omit leading and trailing whitespace
                    value = value.replaceAll("\\r\\n|\\r|\\n", "").trim();
                    paramsString.append(key).append("=").append(value);
                    logger.debug("Parameter : \"{}\"=\"{}\"", key, value);
                }

            } else {
                if (paramsString.length() > 0) {
                    paramsString.append("&");
                }
                // Get value and remove line breaks and omit leading
                // and trailing whitespace
                String value = ((String) params.get(key)).replaceAll("\\r\\n|\\r|\\n", "").trim();
                paramsString.append(key).append("=").append(value);
                logger.debug("Parameter : \"{}\"=\"{}\"", key, value);
            }
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
