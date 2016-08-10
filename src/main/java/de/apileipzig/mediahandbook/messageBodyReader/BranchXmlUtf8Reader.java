package de.apileipzig.mediahandbook.messageBodyReader;

import de.apileipzig.mediahandbook.entity.Branch;
import de.apileipzig.mediahandbook.entity.EntityFactory;
import de.apileipzig.mediahandbook.entity.EntityFactoryBuilder;
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
 * XML deserialization for branch entity.
 *
 * @author cpieloth
 */
public class BranchXmlUtf8Reader implements MessageBodyReader<Branch> {

    private static final Logger log = LoggerFactory.getLogger(BranchXmlUtf8Reader.class);

    /**
     * Deserialize XML branch node.
     *
     * @param entityNode Parent node which contains branch fields.
     * @return Filled Branch object or null
     */
    public static Branch readEntityNode(final Node entityNode) {
        // Use element nodes, only
        if (entityNode.getNodeType() != Node.ELEMENT_NODE) {
            return null;
        }

        EntityFactory ef = EntityFactoryBuilder.newEntityFactory();
        Branch entity = ef.newBranch();

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
            if ("description ".equals(child.getNodeName().toLowerCase())) {
                entity.setDescription(child.getTextContent());
            }
            else if ("id".equals(child.getNodeName().toLowerCase())) {
                entity.setId(Integer.parseInt(child.getTextContent()));
            }
            else if ("parent-id".equals(child.getNodeName().toLowerCase())) {
                entity.setParentId(Integer.parseInt(child.getTextContent()));
            }
            else if ("name".equals(child.getNodeName().toLowerCase())) {
                entity.setName(child.getTextContent());
            }
            else if ("internal-key".equals(child.getNodeName().toLowerCase())) {
                entity.setInternalKey(child.getTextContent());
            }
            else if ("internal-type".equals(child.getNodeName().toLowerCase())) {
                entity.setInternalType(child.getTextContent());
            }

            child = child.getNextSibling();
        }

        return entity;
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Branch.class.isAssignableFrom(type) && MediaType.TEXT_XML_TYPE.withCharset("utf-8").toString()
            .toLowerCase()
            .equals(mediaType.toString().toLowerCase());
    }

    @Override
    public Branch readFrom(Class<Branch> type, Type genericType, Annotation[] annotations, MediaType mediaType,
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
