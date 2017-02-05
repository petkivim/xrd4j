package com.pkrete.xrd4j.rest.converter;

/**
 * An interface for converting XML, JSON and JSON-LD. Input and output is
 * always a String that contains the data in the format that is defined by
 * the implementing class.
 *
 * @author Petteri Kivimäki
 */
@FunctionalInterface
public interface Converter {

    /**
     * Converts the given string between formats defined by the implementing
     * class.
     * @param data input
     * @return output
     */
    String convert(String data);
}
