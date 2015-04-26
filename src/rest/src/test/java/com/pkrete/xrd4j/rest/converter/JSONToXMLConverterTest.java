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
     *
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

    /**
     * Test key named array. This behavior could be considered a bug in
     * org.json.
     */
    public void testNotNestingArrayTags() {
        /*
         * When passing JSON with a key "array" and an array-type value, we would
         * require nested sets of <array /> tags, but org.json gives us only one level:
         *      "<array><id>49</id><name_en>City of Espoo</name_en><name_sv>Esbo stad</name_sv><data_source_url>www.espoo.fi</data_source_url><name_fi>Espoon kaupunki</name_fi></array><array><id>91</id><name_en>City of Helsinki</name_en><name_sv>Helsingfors stad</name_sv><data_source_url>www.hel.fi</data_source_url><name_fi>Helsingin kaupunki</name_fi></array>"
         *
         * Therefore we disallow the use of the "array" key altogether.
         */
        String correctXml = "<error>Invalid key \"array\"</error>";
        String json = "{\"array\":[{\"id\":49,\"name_fi\":\"Espoon kaupunki\",\"name_sv\":\"Esbo stad\",\"name_en\":\"City of Espoo\",\"data_source_url\":\"www.espoo.fi\"},{\"id\":91,\"name_fi\":\"Helsingin kaupunki\",\"name_sv\":\"Helsingfors stad\",\"name_en\":\"City of Helsinki\",\"data_source_url\":\"www.hel.fi\"}]}";
        String xml = this.converter.convert(json);
        assertEquals(correctXml, xml);
    }

    /**
     * Test empty array in object is removed. This behavior could be considered
     * a bug in org.json.
     */
    public void testEmptyArray() {
        String correctXml = "<DATA><array>one</array><array>two</array><array>three</array></DATA>";
        String json = "{\"DATA\": [[\"one\", \"two\", \"three\"]], \"ERRORS\": []}";
        String xml = this.converter.convert(json);
        assertEquals(correctXml, xml);
    }

    /**
     * Test a deep structure comes through correctly.
     */
    public void testDeepData() {
        String correctXml = "<DEEPDATA><realm>1</realm><realm>2</realm><realm>3</realm></DEEPDATA>";
        correctXml += "<DATA><array>one</array><array>two</array><array>three</array></DATA>";

        String json = "{\"DATA\": [[\"one\",\"two\",\"three\"]], \"DEEPDATA\": {\"realm\": [1,2,3]}}";
        String xml = this.converter.convert(json);

        assertEquals(correctXml, xml);
    }

    /**
     * Test converting JSON-LD to XML.
     */
    public void testJSONLD1() {
        String correctXml = "<__at__context>http://json-ld.org/contexts/person.jsonld</__at__context><name>John Lennon</name><__at__id>http://dbpedia.org/resource/John_Lennon</__at__id>";

        String json = "{\"@context\": \"http://json-ld.org/contexts/person.jsonld\",\"@id\": \"http://dbpedia.org/resource/John_Lennon\",\"name\": \"John Lennon\"}";
        String xml = this.converter.convert(json);

        assertEquals(correctXml, xml);
    }
}
