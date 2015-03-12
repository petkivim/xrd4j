package com.pkrete.xrd4j.server.utils;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 * This class offers helper methods for adapter servlet.
 *
 * @author Petteri Kivimäki
 */
public class AdapterUtils {

    /**
     * Fetches MIME header information from HTTP request object.
     * @param req HTTP request object
     * @return MimeHeaders that were extracted from the HTTP request
     */
    public static MimeHeaders getHeaders(HttpServletRequest req) {
        Enumeration enumeration = req.getHeaderNames();
        MimeHeaders headers = new MimeHeaders();

        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            String value = req.getHeader(name);

            StringTokenizer values = new StringTokenizer(value, ",");
            while (values.hasMoreTokens()) {
                headers.addHeader(name, values.nextToken().trim());
            }
        }
        return headers;
    }

    /**
     * Adds the given MIME headers into the HTTP response object.
     * @param headers MIME headers to be added
     * @param res the Http servlet response
     */
    public static void putHeaders(MimeHeaders headers, HttpServletResponse res) {
        for (Iterator it = headers.getAllHeaders(); it.hasNext();) {
            MimeHeader header = (MimeHeader) it.next();
            String[] values = headers.getHeader(header.getName());
            if (values.length == 1) {
                res.setHeader(header.getName(), header.getValue());
            } else {
                StringBuilder concat = new StringBuilder();
                for (int i = 0; i < values.length; i++) {
                    if (i != 0) {
                        concat.append(',');
                    }
                    concat.append(values[i]);
                }
                res.setHeader(header.getName(), concat.toString());
            }
        }
    }

    /**
     * Returns the MIME boundary that's used in the given SOAP message.
     * @param soap String containing a SOAP message
     * @return MIME boundary or null
     */
    public static String getMIMEBoundary(String soap) {
        if (!soap.startsWith("--")) {
            return null;
        }
        return soap.substring(2, soap.indexOf("\r\n"));
    }

    /**
     * Returns a string containing info about all the SOAP attachments.
     * @param soapMessage SOAP message
     * @return String containing attachments info
     */
    public static String getAttachmentsInfo(SOAPMessage soapMessage) {
        try {
            int numOfAttachments = soapMessage.countAttachments();
            Iterator attachments = soapMessage.getAttachments();

            StringBuilder buf = new StringBuilder("Number of attachments: ");
            buf.append(numOfAttachments);
            int count = 1;
            while (attachments.hasNext()) {
                AttachmentPart attachment = (AttachmentPart) attachments.next();
                buf.append(" | #").append(count);
                buf.append(" | Content Location: ").append(attachment.getContentLocation());
                buf.append(" | Content Id: ").append(attachment.getContentId());
                buf.append(" | Content Size: ").append(attachment.getSize());
                buf.append(" | Content Type: ").append(attachment.getContentType());
                count++;
            }
            return buf.toString();
        } catch (SOAPException e) {
            return "";
        }
    }

    /**
     * Returns a string containg all the HTTP request headers and their values.
     * @param req HTTP request
     * @return String containg all the HTTP request headers and their values
     */
    public static String getHeaderInfo(HttpServletRequest req) {
        StringBuilder builder = new StringBuilder("HTTP request headers :");

        Enumeration<String> headerNames = req.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            builder.append("\n\"").append(headerName).append("\" => \"");
            Enumeration<String> headers = req.getHeaders(headerName);
            while (headers.hasMoreElements()) {
                String headerValue = headers.nextElement();
                builder.append(headerValue);
                if (headers.hasMoreElements()) {
                    builder.append(",");
                }
            }
            builder.append("\"");
        }
        return builder.toString();
    }
}
