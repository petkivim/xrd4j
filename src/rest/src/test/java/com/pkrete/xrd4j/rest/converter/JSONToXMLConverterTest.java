package com.pkrete.xrd4j.rest.converter;

import com.pkrete.xrd4j.common.util.SOAPHelper;
import java.util.ArrayList;
import java.util.List;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

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
        List<String> elements = new ArrayList<>();
        elements.add("<key1>value1</key1>");
        elements.add("<key2>value2</key2>");
        elements.add("<key3>value3</key3>");
        elements.add("<key4>value4</key4>");
        String json = "{\"key1\":\"value1\",\"key2\":\"value2\",\"key3\":\"value3\",\"key4\":\"value4\"}";
        String xml = this.converter.convert(json);
        if (xml.length() != 76) {
            fail();
        }
        for (String element : elements) {
            if (!xml.contains(element)) {
                fail();
            }
        }
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
        List<String> elements = new ArrayList<>();
        elements.add("<key1>value1</key1>");
        elements.add("<key2>true</key2>");
        elements.add("<key3>3</key3>");

        String json = "{\"key1\":\"value1\",\"key2\":true,\"key3\":3}";
        String xml = this.converter.convert(json);
        if (xml.length() != 50) {
            fail();
        }
        for (String element : elements) {
            if (!xml.contains(element)) {
                fail();
            }
        }
    }

    /**
     * Test converting nested element.
     */
    public void testNestedElement() throws XPathExpressionException {
        String json = "{\"request\":{\"key1\":\"value1\"}}";
        String xml = this.converter.convert(json);
        Document xmlConverted = SOAPHelper.xmlStrToDoc(xml);

        XPath xPath = XPathFactory.newInstance().newXPath();
        assertEquals("value1", xPath.compile("/request/key1").evaluate(xmlConverted));
    }

    /**
     * Test converting nested elements.
     */
    public void testNestedElements1() throws XPathExpressionException {
        String json = "{\"request\":{\"key1\":\"value1\",\"key2\":true,\"key3\":3}}";
        String xml = this.converter.convert(json);
        Document xmlConverted = SOAPHelper.xmlStrToDoc(xml);

        XPath xPath = XPathFactory.newInstance().newXPath();
        assertEquals("value1", xPath.compile("/request/key1").evaluate(xmlConverted));
        assertEquals("true", xPath.compile("/request/key2").evaluate(xmlConverted));
        assertEquals("3", xPath.compile("/request/key3").evaluate(xmlConverted));
    }

    /**
     * Test converting nested elements.
     */
    public void testNestedElements2() throws XPathExpressionException {
        String json = "{\"menu\": {\"id\": \"file\",\"value\": \"File\",\"popup\": {\"menuitem\": [{\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"},{\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},{\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}]}}}";
        String xml = this.converter.convert(json);

        Document xmlConverted = SOAPHelper.xmlStrToDoc(xml);

        XPath xPath = XPathFactory.newInstance().newXPath();
        assertEquals("file", xPath.compile("/menu/id").evaluate(xmlConverted));
        assertEquals("File", xPath.compile("/menu/value").evaluate(xmlConverted));

        NodeList nodeList = (NodeList) xPath.compile("/menu/popup/menuitem").evaluate(xmlConverted, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            for (int j = 0; j < nodeList.item(i).getChildNodes().getLength(); j += 2) {
                String nodeName1 = nodeList.item(i).getChildNodes().item(j).getLocalName();
                String nodeValue1 = nodeList.item(i).getChildNodes().item(j).getTextContent();
                String nodeName2 = nodeList.item(i).getChildNodes().item(j + 1).getLocalName();
                String nodeValue2 = nodeList.item(i).getChildNodes().item(j + 1).getTextContent();

                if (!((nodeValue1.equals("New") && nodeValue2.equals("CreateNewDoc()")) || (nodeValue2.equals("New") && nodeValue1.equals("CreateNewDoc()")))
                        && !((nodeValue1.equals("Open") && nodeValue2.equals("OpenDoc()")) || (nodeValue2.equals("Open") && nodeValue1.equals("OpenDoc()")))
                        && !((nodeValue1.equals("Close") && nodeValue2.equals("CloseDoc()")) || (nodeValue2.equals("Close") && nodeValue1.equals("CloseDoc()")))) {
                    fail();
                }
            }
        }
    }

    /**
     * Test converting nested elements.
     */
    public void testNestedElements3() {
        List<String> elements = new ArrayList<>();
        elements.add("<id>49</id>");
        elements.add("<name_en>City of Espoo</name_en>");
        elements.add("<name_sv>Esbo stad</name_sv>");
        elements.add("<data_source_url>www.espoo.fi</data_source_url>");
        elements.add("<name_fi>Espoon kaupunki</name_fi>");
        // Correct XML (length: 152):
        // "<id>49</id><name_en>City of Espoo</name_en><name_sv>Esbo stad</name_sv><data_source_url>www.espoo.fi</data_source_url><name_fi>Espoon kaupunki</name_fi>";
        String json = "{\"id\":49,\"name_fi\":\"Espoon kaupunki\",\"name_sv\":\"Esbo stad\",\"name_en\":\"City of Espoo\",\"data_source_url\":\"www.espoo.fi\"}";
        String xml = this.converter.convert(json);

        if (xml.length() != 152) {
            fail();
        }
        for (String element : elements) {
            if (!xml.contains(element)) {
                fail();
            }
        }
    }

    /**
     * Test converting nested elements.
     */
    public void testArray() throws XPathExpressionException {
        List<String> elements = new ArrayList<>();
        elements.add("<id>49</id>");
        elements.add("<name_en>City of Espoo</name_en>");
        elements.add("<name_sv>Esbo stad</name_sv>");
        elements.add("<data_source_url>www.espoo.fi</data_source_url>");
        elements.add("<name_fi>Espoon kaupunki</name_fi>");
        elements.add("<id>91</id>");
        elements.add("<name_en>City of Helsinki</name_en>");
        elements.add("<name_sv>Helsingfors stad</name_sv>");
        elements.add("<data_source_url>www.hel.fi</data_source_url>");
        elements.add("<name_fi>Helsingin kaupunki</name_fi>");
        // Correct XML (length: 345):
        // "<array><id>49</id><name_en>City of Espoo</name_en><name_sv>Esbo stad</name_sv><data_source_url>www.espoo.fi</data_source_url><name_fi>Espoon kaupunki</name_fi></array><array><id>91</id><name_en>City of Helsinki</name_en><name_sv>Helsingfors stad</name_sv><data_source_url>www.hel.fi</data_source_url><name_fi>Helsingin kaupunki</name_fi></array>";
        String json = "[{\"id\":49,\"name_fi\":\"Espoon kaupunki\",\"name_sv\":\"Esbo stad\",\"name_en\":\"City of Espoo\",\"data_source_url\":\"www.espoo.fi\"},{\"id\":91,\"name_fi\":\"Helsingin kaupunki\",\"name_sv\":\"Helsingfors stad\",\"name_en\":\"City of Helsinki\",\"data_source_url\":\"www.hel.fi\"}]";
        String xml = this.converter.convert(json);

        if (xml.length() != 345) {
            fail();
        }
        if (!xml.matches("^<array>.+</array>$")) {
            fail();
        }
        for (String element : elements) {
            if (!xml.contains(element)) {
                fail();
            }
        }
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
        List<String> elements = new ArrayList<>();
        elements.add("<array>one</array>");
        elements.add("<array>two</array>");
        elements.add("<array>three</array>");
        // Correct XML (length: 69):
        // "<DATA><array>one</array><array>two</array><array>three</array></DATA>";
        String json = "{\"DATA\": [[\"one\", \"two\", \"three\"]], \"ERRORS\": []}";
        String xml = this.converter.convert(json);

        if (xml.length() != 69) {
            fail();
        }
        if (!xml.matches("^<DATA>.+</DATA>$")) {
            fail();
        }
        for (String element : elements) {
            if (!xml.contains(element)) {
                fail();
            }
        }
    }

    /**
     * Test a deep structure comes through correctly.
     */
    public void testDeepData() {
        List<String> elements = new ArrayList<>();
        elements.add("<realm>1</realm>");
        elements.add("<realm>2</realm>");
        elements.add("<realm>3</realm>");
        elements.add("<array>one</array>");
        elements.add("<array>two</array>");
        elements.add("<array>three</array>");

        String json = "{\"DATA\": [[\"one\",\"two\",\"three\"]], \"DEEPDATA\": {\"realm\": [1,2,3]}}";
        String xml = this.converter.convert(json);

        if (xml.length() != 138) {
            fail();
        }
        if (!xml.matches(".*<DATA>.{56}</DATA>.*") || !xml.matches(".*<DEEPDATA>.{48}</DEEPDATA>.*")) {
            fail();
        }
        for (String element : elements) {
            if (!xml.contains(element)) {
                fail();
            }
        }
    }

    /**
     * Test converting JSON-LD to XML.
     */
    public void testJSONLD1() {
        List<String> elements = new ArrayList<>();
        elements.add("<__at__context>http://json-ld.org/contexts/person.jsonld</__at__context>");
        elements.add("<name>John Lennon</name>");
        elements.add("<__at__id>http://dbpedia.org/resource/John_Lennon</__at__id>");
        // Correct XML (length: 156):
        // "<__at__context>http://json-ld.org/contexts/person.jsonld</__at__context><name>John Lennon</name><__at__id>http://dbpedia.org/resource/John_Lennon</__at__id>";

        String json = "{\"@context\": \"http://json-ld.org/contexts/person.jsonld\",\"@id\": \"http://dbpedia.org/resource/John_Lennon\",\"name\": \"John Lennon\"}";
        String xml = this.converter.convert(json);

        if (xml.length() != 156) {
            fail();
        }
        for (String element : elements) {
            if (!xml.contains(element)) {
                fail();
            }
        }
    }
}
