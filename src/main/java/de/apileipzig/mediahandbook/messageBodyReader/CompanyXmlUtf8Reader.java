package de.apileipzig.mediahandbook.messageBodyReader;

import de.apileipzig.mediahandbook.entity.EntityFactoryBuilder;
import de.apileipzig.mediahandbook.entity.Company;
import de.apileipzig.mediahandbook.entity.EntityFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * XML deserialization for company entity.
 *
 * @author cpieloth
 */
public class CompanyXmlUtf8Reader implements MessageBodyReader<Company> {

    private static final Logger log = LoggerFactory.getLogger(CompanyXmlUtf8Reader.class);

    /**
     * Deserialize XML company node.
     *
     * @param entityNode Parent node which contains company fields.
     * @return Filled Company object or null
     */
    public static Company readEntityNode(final Node entityNode) {
        // Use element nodes, only
        if (entityNode.getNodeType() != Node.ELEMENT_NODE) {
            return null;
        }

        EntityFactory ef = EntityFactoryBuilder.newEntityFactory();
        Company entity = ef.newCompany();

        Node child = entityNode.getFirstChild();
        while (child != null) {
            // Use element nodes, only
            if (child.getNodeType() != Node.ELEMENT_NODE) {
                child = child.getNextSibling();
                continue;
            }

            // Skip "nil=true" nodes
            if (child.hasAttributes()) {
                NamedNodeMap map = child.getAttributes();
                Node node = map.getNamedItem("nil");
                if ("true".equals(node.getNodeValue().toLowerCase())) {
                    child = child.getNextSibling();
                    continue;
                }
            }

            // Read data
            if ("city".equals(child.getNodeName().toLowerCase())) {
                entity.setCity(child.getTextContent());
            }
            else if ("id".equals(child.getNodeName().toLowerCase())) {
                entity.setId(Integer.parseInt(child.getTextContent()));
            }
            else if ("old-id".equals(child.getNodeName().toLowerCase())) {
                entity.setOldId(Integer.parseInt(child.getTextContent()));
            }
            else if ("name".equals(child.getNodeName().toLowerCase())) {
                entity.setName(child.getTextContent());
            }
            else if ("street".equals(child.getNodeName().toLowerCase())) {
                entity.setStreet(child.getTextContent());
            }
            else if ("housenumber".equals(child.getNodeName().toLowerCase())) {
                entity.setHousenumber(Integer.parseInt(child.getTextContent()));
            }
            else if ("housenumber-additional".equals(child.getNodeName().toLowerCase())) {
                entity.setHousenumberAdditional(child.getTextContent());
            }
            else if ("postcode".equals(child.getNodeName().toLowerCase())) {
                entity.setPostcode(child.getTextContent());
            }

            child = child.getNextSibling();
        }

        return entity;
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Company.class.isAssignableFrom(type) && MediaType.TEXT_XML_TYPE.withCharset("utf-8").toString()
            .toLowerCase()
            .equals(mediaType.toString().toLowerCase());
    }

    @Override
    public Company readFrom(Class<Company> type, Type genericType, Annotation[] annotations, MediaType mediaType,
        MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
        throws IOException, WebApplicationException {
        DocumentBuilder db;
        try {
            db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error("Could not create document builder!", e);
            return null;
        }
        Document doc;
        try {
            InputSource is = new InputSource(entityStream);
            is.setEncoding("UTF-8");
            doc = db.parse(is);
        } catch (SAXException e) {
            log.error("Parse error!", e);
            return null;
        } catch (IOException e) {
            log.error("I/O error!", e);
            return null;
        }

        final Element hashNode = doc.getDocumentElement();
        if (!"hash".equals(hashNode.getTagName())) {
            log.error("Expected hash node, found: {}", hashNode.getTagName());
            return null;
        }

        NodeList nodeList = hashNode.getElementsByTagName("model");
        if (nodeList == null || nodeList.getLength() != 1) {
            log.error("No or to many model nodes!");
            return null;
        }

        return readEntityNode(nodeList.item(0));
    }
}
