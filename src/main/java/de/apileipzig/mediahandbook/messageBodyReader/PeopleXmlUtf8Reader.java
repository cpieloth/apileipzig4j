package de.apileipzig.mediahandbook.messageBodyReader;

import de.apileipzig.mediahandbook.entity.EntityFactory;
import de.apileipzig.mediahandbook.entity.EntityFactoryBuilder;
import de.apileipzig.mediahandbook.entity.People;
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
 * XML deserialization for people entity.
 *
 * @author cpieloth
 */
public class PeopleXmlUtf8Reader implements MessageBodyReader<People> {

    private static final Logger log = LoggerFactory.getLogger(PeopleXmlUtf8Reader.class);

    /**
     * Deserialize XML people node.
     *
     * @param peopleNode Parent node which contains people fields.
     * @return Filled People object or null
     */
    public static People readPeopleNode(final Node peopleNode) {
        // Use element nodes, only
        if (peopleNode.getNodeType() != Node.ELEMENT_NODE) {
            return null;
        }

        EntityFactory ef = EntityFactoryBuilder.newEntityFactory();
        People people = ef.newPeople();

        Node child = peopleNode.getFirstChild();
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
            if ("company-id ".equals(child.getNodeName().toLowerCase())) {
                people.setCompanyId(Integer.parseInt(child.getTextContent()));
            }
            else if ("id".equals(child.getNodeName().toLowerCase())) {
                people.setId(Integer.parseInt(child.getTextContent()));
            }
            else if ("first-name".equals(child.getNodeName().toLowerCase())) {
                people.setFirstName(child.getTextContent());
            }
            else if ("last-name".equals(child.getNodeName().toLowerCase())) {
                people.setLastName(child.getTextContent());
            }
            else if ("occupation".equals(child.getNodeName().toLowerCase())) {
                people.setOccupation(child.getTextContent());
            }
            else if ("position ".equals(child.getNodeName().toLowerCase())) {
                people.setPosition(child.getTextContent());
            }
            else if ("title".equals(child.getNodeName().toLowerCase())) {
                people.setTitle(child.getTextContent());
            }

            child = child.getNextSibling();
        }

        return people;
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return People.class.isAssignableFrom(type) && MediaType.TEXT_XML_TYPE.withCharset("utf-8").toString()
            .toLowerCase()
            .equals(mediaType.toString().toLowerCase());
    }

    @Override
    public People readFrom(Class<People> type, Type genericType, Annotation[] annotations, MediaType mediaType,
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

        return readPeopleNode(nodeList.item(0));
    }
}
