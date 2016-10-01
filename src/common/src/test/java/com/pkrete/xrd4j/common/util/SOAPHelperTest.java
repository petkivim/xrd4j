package com.pkrete.xrd4j.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.soap.SOAPElement;
import junit.framework.TestCase;

/**
 * Test cases for SOAPHelper class.
 *
 * @author Petteri Kivimäki
 */
public class SOAPHelperTest extends TestCase {

    /**
     * Multiple parameters with the same name.
     */
    public void testNodesToMap1() {
        String xml = "<request><param1>value1</param1><param1>value2</param1><param1>value3</param1><param2>value1</param2><param2>value2</param2><param3></param3><param4>\n</param4></request>";
        SOAPElement soap = SOAPHelper.xmlStrToSOAPElement(xml);
        Map<String, String> nodes = SOAPHelper.nodesToMap(soap.getChildNodes());
        if (nodes.keySet().size() != 3) {
            fail("Too many keys found: " + nodes.keySet().size() + ". The right key count is 3.");
        }
        if (!nodes.get("param1").equals("value3")) {
            fail("\"param1\" invalid value: " + nodes.get("param1"));
        }
        if (!nodes.get("param2").equals("value2")) {
            fail("\"param2\" invalid value: " + nodes.get("param2"));
        }
        if (!nodes.get("param3").equals("")) {
            fail("\"param3\" invalid value: " + nodes.get("param3"));
        }
    }

    /**
     * Unique parameter names with tabs and line breaks.
     */
    public void testNodesToMap2() {
        String xml = "<test:request xmlns:test=\"http://test.com\">\n<test:param1>\tvalue1\n</test:param1><test:param2>value2</test:param2>\r\n<test:param3>value3</test:param3></test:request>";
        SOAPElement soap = SOAPHelper.xmlStrToSOAPElement(xml);
        Map<String, String> nodes = SOAPHelper.nodesToMap(soap.getChildNodes());
        if (nodes.keySet().size() != 3) {
            fail("Too many keys found: " + nodes.keySet().size() + ". The right key count is 3.");
        }
        if (!nodes.get("param1").equals("value1")) {
            fail("\"param1\" invalid value: " + nodes.get("param1"));
        }
        if (!nodes.get("param2").equals("value2")) {
            fail("\"param2\" invalid value: " + nodes.get("param2"));
        }
        if (!nodes.get("param3").equals("value3")) {
            fail("\"param3\" invalid value: " + nodes.get("param3"));
        }
    }

    /**
     * Multiple parameters with the same name inside en extra wrapper element.
     */
    public void testNodesToMap3() {
        String xml = "<request><wrapper><param1>value1</param1><param2>value2</param2><param3>value3</param3></wrapper></request>";
        SOAPElement soap = SOAPHelper.xmlStrToSOAPElement(xml);
        Map<String, String> nodes = SOAPHelper.nodesToMap(soap.getChildNodes());
        if (nodes.keySet().size() != 3) {
            fail("Too many keys found: " + nodes.keySet().size() + ". The right key count is 3.");
        }
        if (!nodes.get("param1").equals("value1")) {
            fail("\"param1\" invalid value: " + nodes.get("param1"));
        }
        if (!nodes.get("param2").equals("value2")) {
            fail("\"param2\" invalid value: " + nodes.get("param2"));
        }
        if (!nodes.get("param3").equals("value3")) {
            fail("\"param3\" invalid value: " + nodes.get("param3"));
        }
    }

    /**
     * Multiple parameters with the same name inside two extra wrapper elements
     * with tabs and line breaks.
     */
    public void testNodesToMap4() {
        String xml = "<request><wrapperext>\n<wrapper>\n\t<param1>value1</param1>\r\n<param2>value2</param2><param3>value3</param3></wrapper></wrapperext></request>";
        SOAPElement soap = SOAPHelper.xmlStrToSOAPElement(xml);
        Map<String, String> nodes = SOAPHelper.nodesToMap(soap.getChildNodes());
        if (nodes.keySet().size() != 3) {
            fail("Too many keys found: " + nodes.keySet().size() + ". The right key count is 3.");
        }
        if (!nodes.get("param1").equals("value1")) {
            fail("\"param1\" invalid value: " + nodes.get("param1"));
        }
        if (!nodes.get("param2").equals("value2")) {
            fail("\"param2\" invalid value: " + nodes.get("param2"));
        }
        if (!nodes.get("param3").equals("value3")) {
            fail("\"param3\" invalid value: " + nodes.get("param3"));
        }
    }

    /**
     * Multiple parameters with the same name.
     */
    public void testNodesToMultiMap1() {
        String xml = "<request><param1>value1</param1><param1>value2</param1><param1>value3</param1><param2>value1</param2><param2>value2</param2><param3></param3></request>";
        SOAPElement soap = SOAPHelper.xmlStrToSOAPElement(xml);
        Map<String, List<String>> nodes = SOAPHelper.nodesToMultiMap(soap.getChildNodes());

        if (nodes.keySet().size() != 3) {
            fail("Too many keys found: " + nodes.keySet().size() + ". The right key count is 2.");
        }
        if (nodes.get("param1").size() != 3) {
            fail("Too many \"param1\" values found: " + nodes.get("param1").size() + ". The right value count is 3.");
        }
        if (nodes.get("param2").size() != 2) {
            fail("Too many \"param2\" values found: " + nodes.get("param2").size() + ". The right value count is 2.");
        }
        if (nodes.get("param3").size() != 1) {
            fail("Too many \"param3\" values found: " + nodes.get("param3").size() + ". The right value count is 1.");
        }
        List<String> params = new ArrayList<>();
        params.add("value1");
        params.add("value2");
        params.add("value3");
        for (String value : params) {
            boolean hit = false;
            for (String nodeValue : nodes.get("param1")) {
                if (nodeValue.equals(value)) {
                    hit = true;
                    break;
                }
            }
            if (!hit) {
                fail("Missing \"param1\" value: " + value);
            }
        }
        params = new ArrayList<>();
        params.add("value1");
        params.add("value2");
        for (String value : params) {
            boolean hit = false;
            for (String nodeValue : nodes.get("param2")) {
                if (nodeValue.equals(value)) {
                    hit = true;
                    break;
                }
            }
            if (!hit) {
                fail("Missing \"param2\" value: " + value);
            }
        }
        if (!nodes.get("param3").get(0).isEmpty()) {
            fail("\"param3\" value should be empty, but \"" + nodes.get("param3").get(0) + "\" was found.");
        }
    }

    /**
     * Multiple parameters with the same name inside an extra wrapper element.
     */
    public void testNodesToMultiMap2() {
        String xml = "<test:request xmlns:test=\"http://test.com\"><test:wrapper><test:param1>value1</test:param1><test:param1>value2</test:param1><test:param1>value3</test:param1><test:param2>value1</test:param2><test:param2>value2</test:param2></test:wrapper></test:request>";
        SOAPElement soap = SOAPHelper.xmlStrToSOAPElement(xml);
        Map<String, List<String>> nodes = SOAPHelper.nodesToMultiMap(soap.getChildNodes());

        if (nodes.keySet().size() != 2) {
            fail("Too many keys found: " + nodes.keySet().size() + ". The right key count is 2.");
        }
        if (nodes.get("param1").size() != 3) {
            fail("Too many \"param1\" values found: " + nodes.get("param1").size() + ". The right value count is 3.");
        }
        if (nodes.get("param2").size() != 2) {
            fail("Too many \"param2\" values found: " + nodes.get("param1").size() + ". The right value count is 2.");
        }
        List<String> params = new ArrayList<>();
        params.add("value1");
        params.add("value2");
        params.add("value3");
        for (String value : params) {
            boolean hit = false;
            for (String nodeValue : nodes.get("param1")) {
                if (nodeValue.equals(value)) {
                    hit = true;
                    break;
                }
            }
            if (!hit) {
                fail("Missing \"param1\" value: " + value);
            }
        }
        params = new ArrayList<>();
        params.add("value1");
        params.add("value2");
        for (String value : params) {
            boolean hit = false;
            for (String nodeValue : nodes.get("param2")) {
                if (nodeValue.equals(value)) {
                    hit = true;
                    break;
                }
            }
            if (!hit) {
                fail("Missing \"param2\" value: " + value);
            }
        }

    }

    /**
     * Multiple parameters with the same name inside two extra wrapper elements
     * with tabs and line breaks.
     */
    public void testNodesToMultiMap3() {
        String xml = "<request><extwrapper><wrapper>\n<param1>\nvalue1\n</param1>\n<param1>\t\tvalue2</param1>\r\n<param1>value3</param1>\t\n<param2>value1</param2><param2>value2</param2>\t</wrapper>\n</extwrapper></request>";
        SOAPElement soap = SOAPHelper.xmlStrToSOAPElement(xml);
        Map<String, List<String>> nodes = SOAPHelper.nodesToMultiMap(soap.getChildNodes());

        if (nodes.keySet().size() != 2) {
            fail("Too many keys found: " + nodes.keySet().size() + ". The right key count is 2.");
        }
        if (nodes.get("param1").size() != 3) {
            fail("Too many \"param1\" values found: " + nodes.get("param1").size() + ". The right value count is 3.");
        }
        if (nodes.get("param2").size() != 2) {
            fail("Too many \"param2\" values found: " + nodes.get("param1").size() + ". The right value count is 2.");
        }
        List<String> params = new ArrayList<>();
        params.add("value1");
        params.add("value2");
        params.add("value3");
        for (String value : params) {
            boolean hit = false;
            for (String nodeValue : nodes.get("param1")) {
                if (nodeValue.equals(value)) {
                    hit = true;
                    break;
                }
            }
            if (!hit) {
                fail("Missing \"param1\" value: " + value);
            }
        }
        params = new ArrayList<>();
        params.add("value1");
        params.add("value2");
        for (String value : params) {
            boolean hit = false;
            for (String nodeValue : nodes.get("param2")) {
                if (nodeValue.equals(value)) {
                    hit = true;
                    break;
                }
            }
            if (!hit) {
                fail("Missing \"param2\" value: " + value);
            }
        }

    }

}
