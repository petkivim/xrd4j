package com.pkrete.xrd4j.rest.converter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class converts JSON strings to XML strings. If JSON keys start with
 * '@' character, it's converted to '__at__' string, because '@' is not
 * allowed in XML element names.
 *
 * @author Petteri Kivimäki
 * @author Markus Törnqvist
 */
public class JSONToXMLConverter implements Converter {

    private static final Logger logger = LoggerFactory.getLogger(JSONToXMLConverter.class);

    /**
     * Converts the given JSON string to XML string.
     * class.
     * @param data JSON string
     * @return XML string or an empty string if the conversion fails
     */
    @Override
    public String convert(String data) {
        String asXML;
        try {
            logger.debug("CONVERTING " + data);
            if (data.startsWith("{")) {
                JSONObject asJson = new JSONObject(data);

                // "array" behaves in a special way, best to disallow it
                if (asJson.has("array")) {
                    logger.error("Data violation: Invalid key \"array\"");
                    return "<error>Invalid key \"array\"</error>";
                }
                asXML = XML.toString(asJson);
            } else {
                asXML = XML.toString(new JSONArray(data));
            }
            // JSON-LD uses '@' characters in keys and they're not allowed
            // in XML element names. Replace '@' characters with '__at__' in
            // element names.
            asXML = asXML.replaceAll("<(/{0,1})@", "<$1__at__");
            logger.debug("RETURN XML " + asXML);
            return asXML;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.warn("Converting JSON to XML failed! An empty string is returned.");
        return "";
    }
}
