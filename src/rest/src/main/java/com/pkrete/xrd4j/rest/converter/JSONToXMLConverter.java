/**
 * MIT License
 *
 * Copyright (C) 2014 Petteri Kivimäki, Markus Törnqvist
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
package com.pkrete.xrd4j.rest.converter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class converts JSON strings to XML strings.
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
            logger.debug("RETURN XML " + asXML);
            return asXML;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.warn("Converting JSON to XML failed! An empty string is returned.");
        return "";
    }
}
