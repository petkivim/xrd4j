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
 * Enumeration for SDSB identifier types. SDSB members don't have a fixed
 * identifier type as the type is defined in a context of a single request.
 * Depending on the context the same member can have different identifier type.
 * In most cases the member acting as a consumer represents SUBSYSTEM and the
 * member acting as a provider represents SERVICE.
 *
 * @author Petteri Kivimäki
 */
public enum ObjectType {

    /**
     * The identifier consists of SDSBInstance code, member class and member
     * code.
     */
    MEMBER,
    /**
     * The identifier consists of SDSBInstance code, member class, member
     * code and subsystem code.
     */
    SUBSYSTEM,
    /**
     * The identifier consists of SDSBInstance code, member class, member
     * code, subsystem code (OPTIONAL), service code and service version
     * (OPTIONAL).
     */
    SERVICE,
    /**
     * The identifier consists of SDSBInstance code and service code.
     * (OPTIONAL).
     */
    CENTRALSERVICE;
}
