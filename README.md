# XRd4J

XRd4J is a Java library for building X-Road v6 services and clients. The library implements X-Road v6 [SOAP profile](https://confluence.csc.fi/download/attachments/47580926/xroad_profile_of_soap_messages_0%205.pdf?version=1&modificationDate=1415865090158&api=v2). The library takes care of serialization and deserialization of SOAP messages: built-in support for standard X-Road SOAP headers, only processing of application specific ```request``` and ```response``` elements must be implemented.

##### Modules:

* ```client``` : SOAP client that generates X-Road v6 SOAP messages that can be sent to X-Road Security Server. Includes request seriazizer and response deserializer.
* ```server``` : Provides abstract servlet that can be use as a base class for Adapter Server implementation. Includes request deserializer and response serializer.
* ```common``` : General purpose utilities for processing SOAP messages and X-Road v6 message data models.
* ```rest``` : HTTP clients that can be used for sending requests to web services from Adapter Server.

### Maven Repository

#### Releases

Available in CSC's Maven Repository: https://maven.csc.fi/repository/internal/

Specify CSC's Maven Repository in a POM:

```
<repository>
  <id>csc-repo</id>
  <name>CSC's Maven repository</name>
  <url>https://maven.csc.fi/repository/internal/</url>
</repository>
```

#### Dependency Declaration

Declare the following depencies in a POM:

```
<!-- Module: common-->
<dependency>
  <groupId>com.pkrete.xrd4j</groupId>
  <artifactId>common</artifactId>
  <version>0.0.1</version>
</dependency>

<!-- Module: client-->
<dependency>
  <groupId>com.pkrete.xrd4j</groupId>
  <artifactId>client</artifactId>
  <version>0.0.1</version>
</dependency>

<!-- Module: server-->
<dependency>
  <groupId>com.pkrete.xrd4j</groupId>
  <artifactId>server</artifactId>
  <version>0.0.1</version>
</dependency>

<!-- Module: rest-->
<dependency>
  <groupId>com.pkrete.xrd4j</groupId>
  <artifactId>rest</artifactId>
  <version>0.0.1</version>
</dependency>
```

### Documentation

##### Client

Main class:

```
  // Security server URL
  String url = "http://security.server.com/cgi-bin/consumer_proxy";
  // Consumer that is calling a service
  ConsumerMember consumer = new ConsumerMember(SDSBInstance.FI_TEST, MemberClass.GOV, "1234567-8");
  // Producer providing the service
  ProducerMember producer = new ProducerMember(SDSBInstance.FI_TEST, MemberClass.GOV, "9876543-1", "Demo2Service", "helloService", "v1");
  producer.setNamespacePrefix("ts");
  producer.setNamespaceUrl("http://test.x-road.fi/producer");
  // Create a new ServiceRequest object, unique message id is generated by MessageHelper.
  // Type of the ServiceRequest is the type of the request data (String in this case)
  ServiceRequest<String> request = new ServiceRequest<String>(consumer, producer, MessageHelper.generateId());
  // Set username
  request.setUserId("jdoe");
  // Set request data
  request.setRequestData("Test message");

  // Application specific class that serializes request data
  ServiceRequestSerializer serializer = new HelloServiceRequestSerializer();
  // Application specific class that deserializes response data
  ServiceResponseDeserializer deserializer = new HelloServiceResponseDeserializer();

  // Create a new SOAP client
  SOAPClient client = new SOAPClientImpl();

  // Send the ServiceRequest, result is returned as ServiceResponse object
  ServiceResponse<String, String> serviceResponse = client.send(request, url, serializer, deserializer);

  // Print out the SOAP message received as response
  System.out.println(SOAPHelper.toString(serviceResponse.getSoapMessage()));

  // Print out only response data. In this case response data is a String.
  System.out.println(serviceResponse.getResponseData());
  
  // Check if response contains an error and print it out
  if (serviceResponse.hasError()) {
    System.out.println(serviceResponse.getErrorMessage().getErrorMessageType());
    System.out.println(serviceResponse.getErrorMessage().getFaultCode());
    System.out.println(serviceResponse.getErrorMessage().getFaultString());
    System.out.println(serviceResponse.getErrorMessage().getFaultActor());
  }
```

HelloServiceRequestSerializer:
```
  public class HelloServiceRequestSerializer extends AbstractServiceRequestSerializer {
  
    @Override
    protected void serializeRequest(ServiceRequest request, SOAPElement soapRequest, SOAPEnvelope envelope) throws SOAPException {
	  // Create element "name" and put request data inside the element
      SOAPElement data = soapRequest.addChildElement(envelope.createName("name"));
      data.addTextNode((String) request.getRequestData());
    }
  }
```

HelloServiceResponseDeserializer:
```
  public class HelloServiceResponseDeserializer extends AbstractResponseDeserializer<String, String> {

  @Override
  protected String deserializeRequestData(Node requestNode) throws SOAPException {
    // Loop through all the children of the "request" element
    for (int i = 0; i < requestNode.getChildNodes().getLength(); i++) {
	  // We're looking for "name" element
      if (requestNode.getChildNodes().item(i).getLocalName().equals("name")) {
	    // Return the text content of the element
        return requestNode.getChildNodes().item(i).getTextContent();
      }
    }
	// No "name" element was found, return null
    return null;
  }

  @Override
  protected String deserializeResponseData(Node responseNode, SOAPMessage message) throws SOAPException {
    // Loop through all the children of the "response" element
    for (int i = 0; i < responseNode.getChildNodes().getLength(); i++) {
	  // We're looking for "message" element
      if (responseNode.getChildNodes().item(i).getLocalName().equals("message")) {
	    // Return the text content of the element
        return responseNode.getChildNodes().item(i).getTextContent();
      }
    }
	// No "message" element was found, return null
    return null;
  }
}
```

##### Server

Coming soon...


