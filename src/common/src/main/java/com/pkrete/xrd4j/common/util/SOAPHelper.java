package com.pkrete.xrd4j.common.util;

import com.pkrete.xrd4j.common.message.AbstractMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * This class offers some helper methods for handling SOAPMessage objects.
 *
 * @author Petteri Kivimäki
 */
public class SOAPHelper {

    private static final String CHARSET = "UTF-8";
    private static final Logger logger = LoggerFactory.getLogger(SOAPHelper.class);

    /**
     * Constructs and initializes a new SOAPHelper object. Should never be used.
     */
    private SOAPHelper() {
    }

    /**
     * Goes through all the child nodes of the given node and returns the first
     * child that matches the given name. If no child with the given is found,
     * null is returned.
     *
     * @param node parent node
     * @param nodeName name of the node to be searched
     * @return node with the given name or null
     */
    public static Node getNode(Node node, String nodeName) {
        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            if (node.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE
                    && node.getChildNodes().item(i).getLocalName().equals(nodeName)) {
                return (Node) node.getChildNodes().item(i);
            }
        }
        return null;
    }

    /**
     * Converts the given SOAPMessage to byte array.
     *
     * @param message SOAPMessage object to be converted
     * @return byte array containing the SOAPMessage or null
     */
    public static byte[] toByteArray(SOAPMessage message) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            message.writeTo(out);
            return out.toByteArray();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * Converts the given SOAPMessage to String.
     *
     * @param message SOAPMessage object to be converted
     * @return String presentation of the given SOAPMessage
     */
    public static String toString(SOAPMessage message) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            message.writeTo(out);
            String strMsg = new String(out.toByteArray(), CHARSET);
            return strMsg;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return "";
        }
    }

    /**
     * Converts the given Node to String.
     *
     * @param node Node object to be converted
     * @return String presentation of the given Node
     */
    public static String toString(Node node) {
        StringWriter sw = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            //t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            //t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty(OutputKeys.ENCODING, CHARSET);
            t.transform(new DOMSource(node), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return "";
        }
    }

    /**
     * Converts the given attachment part to string.
     *
     * @param att attachment part to be converted
     * @return string presentation of the attachment or null
     */
    public static String toString(AttachmentPart att) {
        try {
            return new Scanner(att.getRawContent(), CHARSET).useDelimiter("\\A").next();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Converts the given String to SOAPMessage. The String must contain a valid
     * SOAP message, otherwise null is returned.
     *
     * @param soap SOAP string to be converted
     * @return SOAPMessage or null
     */
    public static SOAPMessage toSOAP(String soap) {
        try {
            InputStream is = new ByteArrayInputStream(soap.getBytes(CHARSET));
            return SOAPHelper.toSOAP(is);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * Converts the given InputStream to SOAPMessage. The stream must contain a
     * valid SOAP message, otherwise null is returned.
     *
     * @param is InputStream to be converted
     * @return SOAPMessage or null
     */
    public static SOAPMessage toSOAP(InputStream is) {
        try {
            SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(new MimeHeaders(), is);
            return soapMessage;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * Converts the given InputStream to SOAPMessage. The stream must contain a
     * valid SOAP message, otherwise null is returned.
     *
     * @param is InputStream to be converted
     * @param mh MIME headers of the SOAP request
     * @return SOAPMessage or null
     */
    public static SOAPMessage toSOAP(InputStream is, MimeHeaders mh) {
        try {
            SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(mh, is);
            return soapMessage;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * Transfers the given NodeList to a Map that contains all the list items as
     * key-value-pairs, localName as the key and NodeValue as the value. The
     * given NodeList is parsed recursively.
     *
     * @param list NodeList to be transfered
     * @return Map that contains all the list items as key-value-pairs
     */
    public static Map<String, String> nodesToMap(NodeList list) {
        return SOAPHelper.nodesToMap(list, false);
    }

    /**
     * Transfers the given NodeList to a Map that contains all the list items as
     * key-value-pairs, localName as the key and NodeValue as the value. Each
     * key can have only one value. The given NodeList is parsed recursively.
     *
     * @param list NodeList to be transfered
     * @param upperCase store all keys in upper case
     * @return Map that contains all the list items as key-value-pairs
     */
    public static Map<String, String> nodesToMap(NodeList list, boolean upperCase) {
        Map<String, String> map = new HashMap<>();
        SOAPHelper.nodesToMap(list, upperCase, map);
        return map;
    }

    /**
     * Transfers the given NodeList to a Map that contains all the list items as
     * key-value-pairs, localName as the key and NodeValue as the value. Each
     * key can have only one value. The given NodeList is parsed recursively.
     *
     * @param list NodeList to be transfered
     * @param upperCase store all keys in upper case
     * @param map Map for the results
     */
    public static void nodesToMap(NodeList list, boolean upperCase, Map<String, String> map) {
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeType() == javax.xml.soap.Node.ELEMENT_NODE && list.item(i).hasChildNodes()) {
                nodesToMap(list.item(i).getChildNodes(), upperCase, map);
            } else {
                processMapNode(list, i, upperCase, map);
            }
        }
    }

    /**
     * Transfers the given Node to a Map as key - value pair.
     *
     * @param list NodeList containing the node to be transfered
     * @param index index of the node to be transfered
     * @param upperCase store all keys in upper case
     * @param map Map for the results
     */
    private static void processMapNode(NodeList list, int index, boolean upperCase, Map<String, String> map) {
        if (list.item(index).getNodeType() == javax.xml.soap.Node.ELEMENT_NODE && !list.item(index).hasChildNodes()) {
            String key = list.item(index).getLocalName();
            map.put(upperCase ? key.toUpperCase() : key, "");
        } else if (list.item(index).getNodeType() == javax.xml.soap.Node.TEXT_NODE) {
            String key = list.item(index).getParentNode().getLocalName();
            String value = list.item(index).getNodeValue();
            value = value.trim();
            if (!value.isEmpty()) {
                map.put(upperCase ? key.toUpperCase() : key, value);
            }
        }
    }

    /**
     * Transfers the given NodeList to a MultiMap that contains all the list
     * items as key - value list pairs. Each key can have multiple values that
     * are stored in a list. The given NodeList is parsed recursively.
     *
     * @param list NodeList to be transfered
     * @return Map that contains all the list items as key - value list pairs
     */
    public static Map<String, List<String>> nodesToMultiMap(NodeList list) {
        Map<String, List<String>> map = new HashMap<>();
        SOAPHelper.nodesToMultiMap(list, map);
        return map;
    }

    /**
     * Transfers the given NodeList to a MultiMap that contains all the list
     * items as key - value list pairs. Each key can have multiple values that
     * are stored in a list. The given NodeList is parsed recursively.
     *
     * @param list NodeList to be transfered
     * @param map Map for the results
     */
    public static void nodesToMultiMap(NodeList list, Map<String, List<String>> map) {
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeType() == javax.xml.soap.Node.ELEMENT_NODE && list.item(i).hasChildNodes()) {
                nodesToMultiMap(list.item(i).getChildNodes(), map);
            } else {
                processMultiMapNode(list, i, map);
            }
        }
    }

    /**
     * Transfers the given Node to a MultiMap as key - value list pair.
     *
     * @param list NodeList containing the Node to be transfered
     * @param index index of the Node to be transfered
     * @param map Map for the results
     */
    private static void processMultiMapNode(NodeList list, int index, Map<String, List<String>> map) {
        if (list.item(index).getNodeType() == javax.xml.soap.Node.ELEMENT_NODE && !list.item(index).hasChildNodes()) {
            String key = list.item(index).getLocalName();
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add("");
        } else if (list.item(index).getNodeType() == javax.xml.soap.Node.TEXT_NODE) {
            String key = list.item(index).getParentNode().getLocalName();
            String value = list.item(index).getNodeValue();
            value = value.trim();
            if (!value.isEmpty()) {
                if (!map.containsKey(key)) {
                    map.put(key, new ArrayList<>());
                }
                map.get(key).add(value);
            }
        }
    }

    /**
     * Adds the namespace URI and prefix of the ProvideMember related to the
     * given Message to the given Node and all its children. If the Node should
     * have another namespace, the old namespace is first removed and the new
     * namespace is added after that.
     *
     * @param node Node to be modified
     * @param message Message that contains the ProviderMember which namespace
     * URI and prefix are used
     */
    public static void addNamespace(Node node, AbstractMessage message) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            node.getOwnerDocument().renameNode(node, null, node.getLocalName());
            if (message.getProducer().getNamespacePrefix() != null && !message.getProducer().getNamespacePrefix().isEmpty()) {
                node = (Node) node.getOwnerDocument().renameNode(node, message.getProducer().getNamespaceUrl(), message.getProducer().getNamespacePrefix() + ":" + node.getNodeName());
            } else {
                node = (Node) node.getOwnerDocument().renameNode(node, message.getProducer().getNamespaceUrl(), node.getNodeName());
            }
        }

        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            SOAPHelper.addNamespace((Node) list.item(i), message);
        }
    }

    /**
     * Removes the namespace from the given Node and all its children.
     *
     * @param node Node to be modified
     */
    public static void removeNamespace(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            node.getOwnerDocument().renameNode(node, null, node.getLocalName());
        }
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            SOAPHelper.removeNamespace((Node) list.item(i));
        }
    }

    /**
     * Searches the attachment with the given content id and returns its string
     * contents. If there's no attachment with the given content id or its value
     * is not a string, null is returned.
     *
     * @param contentId content id of the attachment
     * @param attachments list of attachments to be searched
     * @return string value of the attachment or null
     */
    public static String getStringAttachment(String contentId, Iterator attachments) {
        if (attachments == null) {
            return null;
        }
        try {
            while (attachments.hasNext()) {
                AttachmentPart att = (AttachmentPart) attachments.next();
                if (att.getContentId().equals(contentId)) {
                    String input = new Scanner(att.getRawContent(), CHARSET).useDelimiter("\\A").next();
                    return input;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Returns the content type of the first SOAP attachment or null if there's
     * no attachments.
     *
     * @param message SOAP message
     * @return content type of the first attachment or null
     */
    public static String getAttachmentContentType(SOAPMessage message) {
        if (message.countAttachments() == 0) {
            return null;
        }
        AttachmentPart att = (AttachmentPart) message.getAttachments().next();
        return att.getContentType();

    }

    /**
     * Checks if the given SOAP message has attachments. Returns true if and
     * only if the message has attachments. Otherwise returns false.
     *
     * @param message SOAP message to be checked
     * @return true if and only if the message has attachments; otherwise false
     */
    public static boolean hasAttachments(SOAPMessage message) {
        if (message == null) {
            return false;
        }
        if (message.countAttachments() == 0) {
            return false;
        }
        return true;
    }

    /**
     * Converts the given XML string to SOAPElement.
     *
     * @param xml XML string
     * @return given XML string as a SOAPElement or null if the conversion
     * failed
     */
    public static SOAPElement xmlStrToSOAPElement(String xml) {
        logger.debug("Convert XML string to SOAPElement. XML : \"{}\"", xml);
        // Try to conver XML string to XML Document
        Document doc = SOAPHelper.xmlStrToDoc(xml);
        if (doc == null) {
            logger.warn("Convertin XML string to SOAP element failed.");
            return null;
        }

        try {
            // Use SAAJ to convert Document to SOAPElement
            // Create SoapMessage
            MessageFactory msgFactory = MessageFactory.newInstance();
            SOAPMessage message = msgFactory.createMessage();
            SOAPBody soapBody = message.getSOAPBody();
            // This returns the SOAPBodyElement
            // that contains ONLY the Payload
            SOAPElement payload = soapBody.addDocument(doc);
            if (payload == null) {
                logger.warn("Converting XML string to SOAPElement failed.");
            } else {
                logger.debug("Converting XML string to SOAPElement succeeded.");
            }
            return payload;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.warn("Converting XML document to SOAPElement failed.");
            return null;
        }
    }

    /**
     * Converts the given XML string to XML document. If the conversion fails,
     * null is returned.
     *
     * @param xml XML string to be converted
     * @return XML document
     */
    public static Document xmlStrToDoc(String xml) {
        logger.debug("Convert XML string to XML document.");
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        InputStream stream = null;
        Document doc = null;
        try {
            stream = new ByteArrayInputStream(xml.getBytes());
            doc = builderFactory.newDocumentBuilder().parse(stream);
            logger.debug("Converting XML string to XML document succeeded.");
        } catch (Exception e) {
            // If exception starts with "Invalid byte", it means that ISO-8859-1
            // character set is probably used. Try to convert the string to
            // UTF-8.
            if (e.getLocalizedMessage().startsWith("Invalid byte")) {
                logger.warn("Invalid characters detected.");
                try {
                    logger.debug("Try to convert XML string from ISO-8859-1 to UTF-8.");
                    stream = new ByteArrayInputStream(new String(xml.getBytes(), "ISO-8859-1").getBytes(CHARSET));
                    doc = builderFactory.newDocumentBuilder().parse(stream);
                    logger.debug("Converting XML string from ISO-8859-1 to UTF-8 succeeded.");
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    logger.warn("Converting XML string to XML document failed.");
                    logger.warn("Converting XML string from ISO-8859-1 to UTF-8 failed.");
                    return null;
                }
            } else {
                logger.error(e.getMessage());
                logger.warn("Converting XML string to XML document failed.");
                return null;
            }
        }
        return doc;
    }

    /**
     * Removes all the child nodes from the given node.
     *
     * @param node node to be modified
     */
    public static void removeAllChildren(Node node) {
        while (node.hasChildNodes()) {
            node.removeChild(node.getFirstChild());
        }
    }

    /**
     * Move all the children under from SOAPElement to under to SOAPElement. If
     * updateNamespaceAndPrefix is true and from elements do not have namespace
     * URI yet, to elements namespace URI and prefix are recursively copied to
     * them.
     *
     * @param from source element
     * @param to target element
     * @param updateNamespaceAndPrefix should elements namespace URI and prefix
     * be applied to all the copied elements if they do not have namespace URI
     * yet
     * @throws SOAPException
     */
    public static void moveChildren(SOAPElement from, SOAPElement to, boolean updateNamespaceAndPrefix) throws SOAPException {
        NodeList children = from.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = (Node) children.item(i);
            if (updateNamespaceAndPrefix && (child.getNamespaceURI() == null || child.getNamespaceURI().isEmpty())) {
                child = updateNamespaceAndPrefix(child, to.getNamespaceURI(), to.getPrefix());
                updateNamespaceAndPrefix(child.getChildNodes(), to.getNamespaceURI(), to.getPrefix());
            }
            child.setParentElement(to);
        }
    }

    /**
     * Updates the namespace URI and prefix of all the nodes in the list, if
     * node does not have namespace URI yet. The list is updated recursively, so
     * also the children of children (and so on) will be updated.
     *
     * @param list list of nodes to be updated
     * @param namespace target namespace
     * @param prefix target prefix
     */
    public static void updateNamespaceAndPrefix(NodeList list, String namespace, String prefix) {
        for (int i = 0; i < list.getLength(); i++) {
            Node node = (Node) list.item(i);
            if (node.getNamespaceURI() == null || node.getNamespaceURI().isEmpty()) {
                node = updateNamespaceAndPrefix(node, namespace, prefix);
            }
            updateNamespaceAndPrefix(node.getChildNodes(), namespace, prefix);
        }
    }

    /**
     * Updates the namespace URI and prefix of the given node with the given
     * values. If prefix is null or empty, only namespace URI is updated.
     *
     * @param node Node to be updated
     * @param namespace target namespace
     * @param prefix target prefix
     * @return updated Node
     */
    public static Node updateNamespaceAndPrefix(Node node, String namespace, String prefix) {
        if (node.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE) {
            if (prefix != null && !prefix.isEmpty()) {
                node = (Node) node.getOwnerDocument().renameNode(node, namespace, prefix + ":" + node.getLocalName());
            } else if (namespace != null && !namespace.isEmpty()) {
                node = (Node) node.getOwnerDocument().renameNode(node, namespace, node.getLocalName());
            }
        }
        return node;
    }
}
