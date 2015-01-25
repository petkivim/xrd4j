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
package com.pkrete.xrd4j.rest.converter;

import junit.framework.TestCase;

/**
 * Test cases for JSONToXMLConverter class.
 *
 * @author Petteri Kivimäki
 */
public class JSONToXMLConverterTest extends TestCase {

    private Converter converter;

    /**
     * Set up instance variables used in test cases.
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.converter = new JSONToXMLConverter();
    }

    /**
     * Test converting single string element.
     */
    public void testSingleStrElement() {
        String correctXml = "<key1>value1</key1>";
        String json = "{\"key1\":\"value1\"}";
        String xml = this.converter.convert(json);
        assertEquals(correctXml, xml);
    }

    /**
     * Test converting multiple string elements.
     */
    public void testMultipleStrElements() {
        String correctXml = "<key4>value4</key4><key3>value3</key3><key2>value2</key2><key1>value1</key1>";
        String json = "{\"key1\":\"value1\",\"key2\":\"value2\",\"key3\":\"value3\",\"key4\":\"value4\"}";
        String xml = this.converter.convert(json);
        assertEquals(correctXml, xml);
    }

    /**
     * Test converting single string element.
     */
    public void testSingleIntElement() {
        String correctXml = "<key1>1</key1>";
        String json = "{\"key1\":1}";
        String xml = this.converter.convert(json);
        assertEquals(correctXml, xml);
    }

    /**
     * Test converting multiple string, int and boolean elements.
     */
    public void testMultipleElements() {
        String correctXml = "<key3>3</key3><key2>true</key2><key1>value1</key1>";
        String json = "{\"key1\":\"value1\",\"key2\":true,\"key3\":3}";
        String xml = this.converter.convert(json);
        assertEquals(correctXml, xml);
    }

    /**
     * Test converting nested element.
     */
    public void testNestedElement() {
        String correctXml = "<request><key1>value1</key1></request>";
        String json = "{\"request\":{\"key1\":\"value1\"}}";
        String xml = this.converter.convert(json);
        assertEquals(correctXml, xml);
    }

    /**
     * Test converting nested elements.
     */
    public void testNestedElements1() {
        String correctXml = "<request><key3>3</key3><key2>true</key2><key1>value1</key1></request>";
        String json = "{\"request\":{\"key1\":\"value1\",\"key2\":true,\"key3\":3}}";
        String xml = this.converter.convert(json);
        assertEquals(correctXml, xml);
    }

    /**
     * Test converting nested elements.
     */
    public void testNestedElements2() {
        String correctXml = "<menu><id>file</id><popup><menuitem><value>New</value><onclick>CreateNewDoc()</onclick></menuitem><menuitem><value>Open</value><onclick>OpenDoc()</onclick></menuitem><menuitem><value>Close</value><onclick>CloseDoc()</onclick></menuitem></popup><value>File</value></menu>";
        String json = "{\"menu\": {\"id\": \"file\",\"value\": \"File\",\"popup\": {\"menuitem\": [{\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"},{\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},{\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}]}}}";
        String xml = this.converter.convert(json);
        assertEquals(correctXml, xml);
    }

    /**
     * Test converting nested elements.
     */
    public void testNestedElements3() {
        String correctXml = "<id>49</id><name_en>City of Espoo</name_en><name_sv>Esbo stad</name_sv><data_source_url>www.espoo.fi</data_source_url><name_fi>Espoon kaupunki</name_fi>";
        String json = "{\"id\":49,\"name_fi\":\"Espoon kaupunki\",\"name_sv\":\"Esbo stad\",\"name_en\":\"City of Espoo\",\"data_source_url\":\"www.espoo.fi\"}";
        String xml = this.converter.convert(json);
        assertEquals(correctXml, xml);
    }

    /**
     * Test converting nested elements.
     */
    public void testArray() {
        String correctXml = "<array><id>49</id><name_en>City of Espoo</name_en><name_sv>Esbo stad</name_sv><data_source_url>www.espoo.fi</data_source_url><name_fi>Espoon kaupunki</name_fi></array><array><id>91</id><name_en>City of Helsinki</name_en><name_sv>Helsingfors stad</name_sv><data_source_url>www.hel.fi</data_source_url><name_fi>Helsingin kaupunki</name_fi></array>";
        String json = "[{\"id\":49,\"name_fi\":\"Espoon kaupunki\",\"name_sv\":\"Esbo stad\",\"name_en\":\"City of Espoo\",\"data_source_url\":\"www.espoo.fi\"},{\"id\":91,\"name_fi\":\"Helsingin kaupunki\",\"name_sv\":\"Helsingfors stad\",\"name_en\":\"City of Helsinki\",\"data_source_url\":\"www.hel.fi\"}]";
        String xml = this.converter.convert(json);
        assertEquals(correctXml, xml);
    }
}
