package com.pkrete.xrd4j.rest.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a factory class that's responsible for creating REST client objects
 * according to the given parameters.
 *
 * @author Petteri Kivimäki
 */
public class RESTClientFactory {

    private static final Logger logger = LoggerFactory.getLogger(RESTClientFactory.class);

    /**
     * Creates a new RESTClient object matching the given HTTP verb. If no
     * matching RESTClient is found, null is returned.
     * @param httpVerb HTTP verb (GET, POST, PUT, DELETE)
     * @return RESTClient object matching the given HTTP verb or null
     */
    public static RESTClient createRESTClient(String httpVerb) {
        if (httpVerb == null || httpVerb.isEmpty()) {
            logger.warn("HTTP verb can't be null or empty. Null is returned.");
            return null;
        }
        logger.trace("Create new REST client.");
        if (httpVerb.equalsIgnoreCase("get")) {
            logger.debug("New GET client created.");
            return new GetClient();
        } else if (httpVerb.equalsIgnoreCase("post")) {
            logger.debug("New POST client created.");
            return new PostClient();
        } else if (httpVerb.equalsIgnoreCase("put")) {
            logger.debug("New PUT client created.");
            return new PutClient();
        } else if (httpVerb.equalsIgnoreCase("delete")) {
            logger.debug("New DELETE client created.");
            return new DeleteClient();
        }
        logger.warn("Unable to create a new REST client. Invalid HTTP verb : \"{}\". Null is returned.", httpVerb);
        return null;
    }
}
