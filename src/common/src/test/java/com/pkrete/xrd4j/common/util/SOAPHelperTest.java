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

    public void testNodesToMap1() {
        String xml = "<request><param1>value1</param1><param1>value2</param1><param1>value3</param1><param2>value1</param2><param2>value2</param2></request>";
        SOAPElement soap = SOAPHelper.xmlStrToSOAPElement(xml);
        Map<String, String> nodes = SOAPHelper.nodesToMap(soap.getChildNodes());
        if (!nodes.get("param1").equals("value3")) {
            fail("\"param1\" invalid value: " + nodes.get("param1"));
        }
        if (!nodes.get("param2").equals("value2")) {
            fail("\"param2\" invalid value: " + nodes.get("param2"));
        }
    }

    public void testNodesToMap2() {
        String xml = "<request><param1>value1</param1><param2>value2</param2><param3>value3</param3></request>";
        SOAPElement soap = SOAPHelper.xmlStrToSOAPElement(xml);
        Map<String, String> nodes = SOAPHelper.nodesToMap(soap.getChildNodes());
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

    public void testNodesToMultiMap1() {
        String xml = "<request><param1>value1</param1><param1>value2</param1><param1>value3</param1><param2>value1</param2><param2>value2</param2></request>";
        SOAPElement soap = SOAPHelper.xmlStrToSOAPElement(xml);
        Map<String, List<String>> nodes = SOAPHelper.nodesToMultiMap(soap.getChildNodes());

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

    public void testNodesToMultiMap2() {
        String xml = "<request><wrapper><param1>value1</param1></wrapper></request>";
        SOAPElement soap = SOAPHelper.xmlStrToSOAPElement(xml);
        Map<String, List<String>> nodes = SOAPHelper.nodesToMultiMap(soap.getChildNodes());

        List<String> params = new ArrayList<>();
        params.add("value1");
        for (String value : params) {
            boolean hit = false;
            for (String nodeValue : nodes.get("wrapper")) {
                if (nodeValue.equals(value)) {
                    hit = true;
                    break;
                }
            }
            if (!hit) {
                fail("Missing \"wrapper\" value: " + value);
            }
        }
    }

    public void testNodesToMultiMap3() {
        String xml = "<request><wrapper><param1>value1</param1></wrapper></request>";
        SOAPElement soap = SOAPHelper.xmlStrToSOAPElement(xml);
        Map<String, List<String>> nodes = SOAPHelper.nodesToMultiMap(soap.getChildNodes().item(0).getChildNodes());

        List<String> params = new ArrayList<>();
        params.add("value1");
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
    }

}
