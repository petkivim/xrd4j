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
package com.pkrete.xrd4j.common.member;

/**
 * Enumeration for supported SDSB instance codes. Typically,
 * this should be ISO code of the country running the X-Road instance,
 * optionally amended with suffix corresponding to environment.
 *
 * @author Petteri Kivimäki
 */
public enum SDSBInstance {

    /**
     * Finland's production environment.
     */
    FI("FI"),
    /**
     * Finland's test environment.
     */
    FI_TEST("FI_TEST"),
    /**
     * Finland's development environment.
     */
    FI_DEV("FI_DEV"),
    /**
     * Finland's pilot environment.
     */
    FI_PILOT("FI_PILOT");
    /**
     * Value of the enum.
     */
    private String value;

    private SDSBInstance(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
