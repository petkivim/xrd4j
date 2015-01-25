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
 * This enumeration represents the type of the member (company, government
 * institution, private person, etc.).
 *
 * @author dinky
 */
public enum MemberClass {

    /**
     * Private company.
     */
    COM("COM"),
    /**
     * Government agency.
     */
    GOV("GOV"),
    /**
     * Enterprise.
     */
    ENT("ENT"),
    /**
     * Foundation, open data provider.
     */
    ORG("ORG");
    /**
     * Value of the enum.
     */
    private String value;

    private MemberClass(String value) {
        this.value = value;
    }

    @Override
    /**
     * Returns a String object representing the value the MemeberClass.
     * @return a String object representing the value the MemeberClass
     */
    public String toString() {
        return this.value;
    }
}
