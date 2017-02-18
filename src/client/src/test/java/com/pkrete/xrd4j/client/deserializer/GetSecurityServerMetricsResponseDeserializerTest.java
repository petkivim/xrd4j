package com.pkrete.xrd4j.client.deserializer;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.member.ObjectType;
import com.pkrete.xrd4j.common.message.ServiceResponse;
import com.pkrete.xrd4j.common.util.SOAPHelper;
import java.util.Map;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;
import org.w3c.dom.NodeList;

/**
 * Test cases for GetSecurityServerMetricsResponseDeserializer class.
 *
 * @author Petteri Kivimäki
 */
public class GetSecurityServerMetricsResponseDeserializerTest extends TestCase {

    /**
     * getSecurityServerMetrics - installed X-Road packages and their versions
     *
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test1() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>client</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>service</id:subsystemCode><id:serviceCode>getSecurityServerMetrics</id:serviceCode></xrd:service><xrd:securityServer id:objectType=\"SERVER\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:serverCode>server1</id:serverCode></xrd:securityServer><xrd:userId>user</xrd:userId><xrd:id>ID-1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion><xrd:requestHash algorithmId=\"http://www.w3.org/2001/04/xmlenc#sha512\">r8gQxR++e6E5WAI9CNfy2t/87vwyhBKtlbKlYWXs5bGxYrGmJZXk3xjvUb62vB936g/TDVwXCWD+iKIgUgHRPw==</xrd:requestHash></SOAP-ENV:Header><SOAP-ENV:Body><m:getSecurityServerMetricsResponse xmlns:m=\"http://x-road.eu/xsd/monitoring\"><m:metricSet><m:name>SERVER:FI/GOV/MEMBER1/server1</m:name><m:stringMetric><m:name>proxyVersion</m:name><m:value>6.9.0-1.20170104084337gitb8fe14a</m:value></m:stringMetric><m:metricSet><m:name>systemMetrics</m:name><m:histogramMetric><m:name>CommittedVirtualMemory</m:name><m:updated>2017-02-18T05:13:54.864Z</m:updated><m:min>2331078656</m:min><m:max>2331078656</m:max><m:mean>2331078656</m:mean><m:median>2331078656</m:median><m:stddev>0.0</m:stddev></m:histogramMetric><m:numericMetric><m:name>DiskSpaceFree_/</m:name><m:value>4594552832</m:value></m:numericMetric><m:stringMetric><m:name>OperatingSystem</m:name><m:value>Linux version 3.13.0-106-generic (buildd@lcy01-30) (gcc version 4.8.4 (Ubuntu 4.8.4-2ubuntu1~14.04.3) ) #153-Ubuntu SMP Tue Dec 6 15:44:32 UTC 2016</m:value></m:stringMetric><m:metricSet><m:name>Processes</m:name><m:metricSet><m:name>1</m:name><m:stringMetric><m:name>processId</m:name><m:value>1</m:value></m:stringMetric><m:stringMetric><m:name>command</m:name><m:value>init</m:value></m:stringMetric><m:stringMetric><m:name>cpuLoad</m:name><m:value>5.1</m:value></m:stringMetric><m:stringMetric><m:name>memUsed</m:name><m:value>0.0</m:value></m:stringMetric><m:stringMetric><m:name>startTime</m:name><m:value>11:21</m:value></m:stringMetric><m:stringMetric><m:name>userId</m:name><m:value>root</m:value></m:stringMetric></m:metricSet>   </m:metricSet><m:metricSet><m:name>Xroad Processes</m:name><m:metricSet><m:name>1161</m:name><m:stringMetric><m:name>processId</m:name><m:value>1161</m:value></m:stringMetric><m:stringMetric><m:name>command</m:name><m:value>/usr/lib/jvm/java-1.8.0-openjdk-amd64/bin/java -Xmx192m -XX:MaxMetaspaceSize=100m -Djruby.compile.mode=OFF -Djna.tmpdir=/var/lib/xroad -Djetty.admin.port=8083 -Djetty.public.port=8084 -Daddon.extraClasspath= -Dlogback.configurationFile=/etc/xroad/conf.d/jetty-logback.xml -XX:+UseG1GC -Dfile.encoding=UTF-8 -Xshare:auto -Djdk.tls.ephemeralDHKeySize=2048 -cp /usr/share/xroad/jetty9/start.jar org.eclipse.jetty.start.Main jetty.home=/usr/share/xroad/jetty9</m:value></m:stringMetric><m:stringMetric><m:name>cpuLoad</m:name><m:value>9.3</m:value></m:stringMetric><m:stringMetric><m:name>memUsed</m:name><m:value>2.6</m:value></m:stringMetric><m:stringMetric><m:name>startTime</m:name><m:value>11:22</m:value></m:stringMetric><m:stringMetric><m:name>userId</m:name><m:value>xroad</m:value></m:stringMetric></m:metricSet><m:metricSet><m:name>1162</m:name><m:stringMetric><m:name>processId</m:name><m:value>1162</m:value></m:stringMetric><m:stringMetric><m:name>command</m:name><m:value>/usr/lib/jvm/java-1.8.0-openjdk-amd64/bin/java -Xmx50m -XX:MaxMetaspaceSize=30m -Dlogback.configurationFile=/etc/xroad/conf.d/confclient-logback-service.xml -XX:+UseG1GC -Dfile.encoding=UTF-8 -Xshare:auto -Djdk.tls.ephemeralDHKeySize=2048 -cp /usr/share/xroad/jlib/configuration-client.jar ee.ria.xroad.common.conf.globalconf.ConfigurationClientMain</m:value></m:stringMetric><m:stringMetric><m:name>cpuLoad</m:name><m:value>11.0</m:value></m:stringMetric><m:stringMetric><m:name>memUsed</m:name><m:value>2.7</m:value></m:stringMetric><m:stringMetric><m:name>startTime</m:name><m:value>11:22</m:value></m:stringMetric><m:stringMetric><m:name>userId</m:name><m:value>xroad</m:value></m:stringMetric></m:metricSet></m:metricSet><m:metricSet><m:name>Packages</m:name><m:stringMetric><m:name>accountsservice</m:name><m:value>0.6.35-0ubuntu7.3</m:value></m:stringMetric><m:stringMetric><m:name>acpid</m:name><m:value>1:2.0.21-1ubuntu2</m:value></m:stringMetric><m:stringMetric><m:name>xroad-addon-messagelog</m:name><m:value>6.9.0-1.20170104084337gitb8fe14a</m:value></m:stringMetric><m:stringMetric><m:name>xroad-addon-metaservices</m:name><m:value>6.9.0-1.20170104084337gitb8fe14a</m:value></m:stringMetric><m:stringMetric><m:name>xroad-addon-proxymonitor</m:name><m:value>6.9.0-1.20170104084337gitb8fe14a</m:value></m:stringMetric><m:stringMetric><m:name>xroad-addon-wsdlvalidator</m:name><m:value>6.9.0-1.20170104084337gitb8fe14a</m:value></m:stringMetric><m:stringMetric><m:name>xroad-common</m:name><m:value>6.9.0-1.20170104084337gitb8fe14a</m:value></m:stringMetric><m:stringMetric><m:name>xroad-jetty9</m:name><m:value>6.9.0-1.20170104084337gitb8fe14a</m:value></m:stringMetric><m:stringMetric><m:name>xroad-monitor</m:name><m:value>6.9.0-1.20170104084337gitb8fe14a</m:value></m:stringMetric><m:stringMetric><m:name>xroad-proxy</m:name><m:value>6.9.0-1.20170104084337gitb8fe14a</m:value></m:stringMetric><m:stringMetric><m:name>xroad-securityserver</m:name><m:value>6.9.0-1.20170104084337gitb8fe14a</m:value></m:stringMetric><m:stringMetric><m:name>xroad-securityserver-fi</m:name><m:value>6.9.0-1.20170104084337gitb8fe14a</m:value></m:stringMetric><m:stringMetric><m:name>xz-utils</m:name><m:value>5.1.1alpha+20120614-2ubuntu2</m:value></m:stringMetric><m:stringMetric><m:name>zerofree</m:name><m:value>1.0.2-1ubuntu1</m:value></m:stringMetric><m:stringMetric><m:name>zlib1g</m:name><m:value>1:1.2.8.dfsg-1ubuntu1</m:value></m:stringMetric></m:metricSet></m:metricSet></m:metricSet></m:getSecurityServerMetricsResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";

        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new GetSecurityServerMetricsResponseDeserializer();
        ServiceResponse<String, NodeList> response = deserializer.deserialize(msg);

        assertEquals("FI", response.getConsumer().getXRoadInstance());
        assertEquals("GOV", response.getConsumer().getMemberClass());
        assertEquals("MEMBER1", response.getConsumer().getMemberCode());
        assertEquals("client", response.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, response.getConsumer().getObjectType());

        assertEquals("FI", response.getProducer().getXRoadInstance());
        assertEquals("COM", response.getProducer().getMemberClass());
        assertEquals("MEMBER2", response.getProducer().getMemberCode());
        assertEquals("service", response.getProducer().getSubsystemCode());
        assertEquals("getSecurityServerMetrics", response.getProducer().getServiceCode());
        assertEquals(null, response.getProducer().getServiceVersion());
        assertEquals("FI", response.getSecurityServer().getXRoadInstance());
        assertEquals("COM", response.getSecurityServer().getMemberClass());
        assertEquals("MEMBER2", response.getSecurityServer().getMemberCode());
        assertEquals("server1", response.getSecurityServer().getServerCode());
        assertEquals("ID-1234567890", response.getId());
        assertEquals("user", response.getUserId());
        assertEquals("4.0", response.getProtocolVersion());
        assertEquals(ObjectType.SERVICE, response.getProducer().getObjectType());
        assertEquals(null, response.getRequestData());

        assertEquals("http://www.w3.org/2001/04/xmlenc#sha512", response.getRequestHashAlgorithm());
        assertEquals("r8gQxR++e6E5WAI9CNfy2t/87vwyhBKtlbKlYWXs5bGxYrGmJZXk3xjvUb62vB936g/TDVwXCWD+iKIgUgHRPw==", response.getRequestHash());
        assertEquals(true, response.getSoapMessage() != null);
        NodeList list = response.getResponseData();

        Map<String, String> results = SOAPHelper.getXRdVersionInfo(list);
        assertEquals("6.9.0-1.20170104084337gitb8fe14a", results.get("xroad-addon-messagelog"));
        assertEquals("6.9.0-1.20170104084337gitb8fe14a", results.get("xroad-addon-metaservices"));
        assertEquals("6.9.0-1.20170104084337gitb8fe14a", results.get("xroad-addon-proxymonitor"));
        assertEquals("6.9.0-1.20170104084337gitb8fe14a", results.get("xroad-addon-wsdlvalidator"));
        assertEquals("6.9.0-1.20170104084337gitb8fe14a", results.get("xroad-common"));
        assertEquals("6.9.0-1.20170104084337gitb8fe14a", results.get("xroad-jetty9"));
        assertEquals("6.9.0-1.20170104084337gitb8fe14a", results.get("xroad-monitor"));
        assertEquals("6.9.0-1.20170104084337gitb8fe14a", results.get("xroad-proxy"));
        assertEquals("6.9.0-1.20170104084337gitb8fe14a", results.get("xroad-securityserver"));
        assertEquals("6.9.0-1.20170104084337gitb8fe14a", results.get("xroad-securityserver-fi"));
    }

    /**
     * getSecurityServerMetrics - no installed X-Road packages
     *
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test2() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>client</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>service</id:subsystemCode><id:serviceCode>getSecurityServerMetrics</id:serviceCode></xrd:service><xrd:securityServer id:objectType=\"SERVER\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:serverCode>server1</id:serverCode></xrd:securityServer><xrd:userId>user</xrd:userId><xrd:id>ID-1234567890</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion><xrd:requestHash algorithmId=\"http://www.w3.org/2001/04/xmlenc#sha512\">r8gQxR++e6E5WAI9CNfy2t/87vwyhBKtlbKlYWXs5bGxYrGmJZXk3xjvUb62vB936g/TDVwXCWD+iKIgUgHRPw==</xrd:requestHash></SOAP-ENV:Header><SOAP-ENV:Body><m:getSecurityServerMetricsResponse xmlns:m=\"http://x-road.eu/xsd/monitoring\"><m:metricSet><m:name>SERVER:FI/GOV/MEMBER1/server1</m:name><m:stringMetric><m:name>proxyVersion</m:name><m:value>6.9.0-1.20170104084337gitb8fe14a</m:value></m:stringMetric><m:metricSet><m:name>systemMetrics</m:name><m:histogramMetric><m:name>CommittedVirtualMemory</m:name><m:updated>2017-02-18T05:13:54.864Z</m:updated><m:min>2331078656</m:min><m:max>2331078656</m:max><m:mean>2331078656</m:mean><m:median>2331078656</m:median><m:stddev>0.0</m:stddev></m:histogramMetric><m:numericMetric><m:name>DiskSpaceFree_/</m:name><m:value>4594552832</m:value></m:numericMetric><m:stringMetric><m:name>OperatingSystem</m:name><m:value>Linux version 3.13.0-106-generic (buildd@lcy01-30) (gcc version 4.8.4 (Ubuntu 4.8.4-2ubuntu1~14.04.3) ) #153-Ubuntu SMP Tue Dec 6 15:44:32 UTC 2016</m:value></m:stringMetric><m:metricSet><m:name>Processes</m:name><m:metricSet><m:name>1</m:name><m:stringMetric><m:name>processId</m:name><m:value>1</m:value></m:stringMetric><m:stringMetric><m:name>command</m:name><m:value>init</m:value></m:stringMetric><m:stringMetric><m:name>cpuLoad</m:name><m:value>5.1</m:value></m:stringMetric><m:stringMetric><m:name>memUsed</m:name><m:value>0.0</m:value></m:stringMetric><m:stringMetric><m:name>startTime</m:name><m:value>11:21</m:value></m:stringMetric><m:stringMetric><m:name>userId</m:name><m:value>root</m:value></m:stringMetric></m:metricSet>   </m:metricSet><m:metricSet><m:name>Xroad Processes</m:name><m:metricSet><m:name>1161</m:name><m:stringMetric><m:name>processId</m:name><m:value>1161</m:value></m:stringMetric><m:stringMetric><m:name>command</m:name><m:value>/usr/lib/jvm/java-1.8.0-openjdk-amd64/bin/java -Xmx192m -XX:MaxMetaspaceSize=100m -Djruby.compile.mode=OFF -Djna.tmpdir=/var/lib/xroad -Djetty.admin.port=8083 -Djetty.public.port=8084 -Daddon.extraClasspath= -Dlogback.configurationFile=/etc/xroad/conf.d/jetty-logback.xml -XX:+UseG1GC -Dfile.encoding=UTF-8 -Xshare:auto -Djdk.tls.ephemeralDHKeySize=2048 -cp /usr/share/xroad/jetty9/start.jar org.eclipse.jetty.start.Main jetty.home=/usr/share/xroad/jetty9</m:value></m:stringMetric><m:stringMetric><m:name>cpuLoad</m:name><m:value>9.3</m:value></m:stringMetric><m:stringMetric><m:name>memUsed</m:name><m:value>2.6</m:value></m:stringMetric><m:stringMetric><m:name>startTime</m:name><m:value>11:22</m:value></m:stringMetric><m:stringMetric><m:name>userId</m:name><m:value>xroad</m:value></m:stringMetric></m:metricSet><m:metricSet><m:name>1162</m:name><m:stringMetric><m:name>processId</m:name><m:value>1162</m:value></m:stringMetric><m:stringMetric><m:name>command</m:name><m:value>/usr/lib/jvm/java-1.8.0-openjdk-amd64/bin/java -Xmx50m -XX:MaxMetaspaceSize=30m -Dlogback.configurationFile=/etc/xroad/conf.d/confclient-logback-service.xml -XX:+UseG1GC -Dfile.encoding=UTF-8 -Xshare:auto -Djdk.tls.ephemeralDHKeySize=2048 -cp /usr/share/xroad/jlib/configuration-client.jar ee.ria.xroad.common.conf.globalconf.ConfigurationClientMain</m:value></m:stringMetric><m:stringMetric><m:name>cpuLoad</m:name><m:value>11.0</m:value></m:stringMetric><m:stringMetric><m:name>memUsed</m:name><m:value>2.7</m:value></m:stringMetric><m:stringMetric><m:name>startTime</m:name><m:value>11:22</m:value></m:stringMetric><m:stringMetric><m:name>userId</m:name><m:value>xroad</m:value></m:stringMetric></m:metricSet></m:metricSet></m:metricSet></m:metricSet></m:getSecurityServerMetricsResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";

        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceResponseDeserializer deserializer = new GetSecurityServerMetricsResponseDeserializer();
        ServiceResponse<String, NodeList> response = deserializer.deserialize(msg);

        assertEquals("FI", response.getConsumer().getXRoadInstance());
        assertEquals("GOV", response.getConsumer().getMemberClass());
        assertEquals("MEMBER1", response.getConsumer().getMemberCode());
        assertEquals("client", response.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, response.getConsumer().getObjectType());

        assertEquals("FI", response.getProducer().getXRoadInstance());
        assertEquals("COM", response.getProducer().getMemberClass());
        assertEquals("MEMBER2", response.getProducer().getMemberCode());
        assertEquals("service", response.getProducer().getSubsystemCode());
        assertEquals("getSecurityServerMetrics", response.getProducer().getServiceCode());
        assertEquals(null, response.getProducer().getServiceVersion());
        assertEquals("FI", response.getSecurityServer().getXRoadInstance());
        assertEquals("COM", response.getSecurityServer().getMemberClass());
        assertEquals("MEMBER2", response.getSecurityServer().getMemberCode());
        assertEquals("server1", response.getSecurityServer().getServerCode());
        assertEquals("ID-1234567890", response.getId());
        assertEquals("user", response.getUserId());
        assertEquals("4.0", response.getProtocolVersion());
        assertEquals(ObjectType.SERVICE, response.getProducer().getObjectType());
        assertEquals(null, response.getRequestData());

        assertEquals("http://www.w3.org/2001/04/xmlenc#sha512", response.getRequestHashAlgorithm());
        assertEquals("r8gQxR++e6E5WAI9CNfy2t/87vwyhBKtlbKlYWXs5bGxYrGmJZXk3xjvUb62vB936g/TDVwXCWD+iKIgUgHRPw==", response.getRequestHash());
        assertEquals(true, response.getSoapMessage() != null);
        NodeList list = response.getResponseData();

        Map<String, String> results = SOAPHelper.getXRdVersionInfo(list);
        assertEquals(0, results.size());
    }
}
