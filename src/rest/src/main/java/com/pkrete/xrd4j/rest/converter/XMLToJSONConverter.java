package com.pkrete.xrd4j.rest.converter;

import org.json.XML;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class converts XML strings to JSON strings.
 *
 * @author Petteri Kivimäki
 * @author Markus Törnqvist
 */
public class XMLToJSONConverter implements Converter {

    private static final Logger logger = LoggerFactory.getLogger(XMLToJSONConverter.class);

    /**
     * Converts the given XML string to JSON string.
     * class.
     * @param data XML string
     * @return JSON string or an empty string if the conversion fails
     */
    @Override
    public String convert(String data) {
        logger.debug("CONVERTING " + data);
        try {
            JSONObject asJson = XML.toJSONObject(data);
            if (asJson.has("array")) {
                // If the JSON object has an "array" key, it's an array
                JSONArray jsonArray = asJson.getJSONArray("array");
                logger.debug("RETURN ARRAY " + jsonArray.toString());
                return jsonArray.toString();
            } else {
                // Did not have top-level array key.
                this.normalizeObject(asJson);
                logger.debug("NORMALIZED TO " + asJson.toString());
                return asJson.toString();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.warn("Converting XML to JSON failed! An empty String is returned.");
            return "";
        }
    }


    protected JSONObject normalizeObject(JSONObject obj) {
        logger.debug("NORM: " + obj.toString());
        for (String key : JSONObject.getNames(obj)) {
            JSONObject subtree = obj.optJSONObject(key);
            if (subtree != null) {
                if (subtree.has("array")) {
                    // Set the array as the direct value
                    JSONArray subarray = subtree.getJSONArray("array");
                    obj.put(key, subarray);
                    logger.debug("recurse with {}: {}", key, subtree.toString());
                }

                // See if there's more to do in this subtree
                normalizeObject(subtree);
            }
        }

        return obj;
    }
}
