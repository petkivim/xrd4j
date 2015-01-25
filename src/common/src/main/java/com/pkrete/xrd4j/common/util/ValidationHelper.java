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
package com.pkrete.xrd4j.common.util;

import com.pkrete.xrd4j.common.exception.XRd4JException;

/**
 * This class offers some helper methods for validation.
 *
 * @author Petteri Kivimäki
 */
public class ValidationHelper {

    /**
     * Constructs and initializes a new ValidationHelper object. Should never
     * be used.
     */
    private ValidationHelper() {
    }

    /**
     * Checks that the given object is not null.
     * @param object Object to be validated
     * @param objectName name of the object to be used in the exception
     * @throws XRd4JException if object is null
     */
    public static void validateNotNull(Object object, String objectName) throws XRd4JException {
        if (object == null) {
            throw new XRd4JException("\"" + objectName + "\" can't be null.");
        }
    }

    /**
     * Checks that the given String object object is not empty.
     * @param str String object to be validated
     * @param name name of the object to be used in the exception
     * @throws XRd4JException if object is empty
     */
    public static void validateStrNotEmpty(String str, String name) throws XRd4JException {
        if (str.isEmpty()) {
            throw new XRd4JException("\"" + name + "\" can't be empty.");
        }
    }

    /**
     * Checks that the given String object is not null or empty.
     * @param str String object to be validated
     * @param name name of the object to be used in the exception
     * @throws XRd4JException if String object is null or empty
     */
    public static void validateStrNotNullOrEmpty(String str, String name) throws XRd4JException {
        ValidationHelper.validateNotNull(str, name);
        ValidationHelper.validateStrNotEmpty(str, name);
    }
}
