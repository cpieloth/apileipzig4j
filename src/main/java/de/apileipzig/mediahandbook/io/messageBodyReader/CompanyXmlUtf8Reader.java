package de.apileipzig.mediahandbook.io.messageBodyReader;

import de.apileipzig.mediahandbook.EntityFactoryBuilder;
import de.apileipzig.mediahandbook.entity.Company;
import de.apileipzig.mediahandbook.EntityFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
 * TODO
 *
 * @author cpieloth
 */
public class CompanyXmlUtf8Reader implements MessageBodyReader<Company> {

    private static final Logger log = LoggerFactory.getLogger(CompanyXmlUtf8Reader.class);

    public static Company fromXml(InputStream entityStream) {
        DocumentBuilder db;
        try {
            db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error("Could not create document builder!", e);
            return null;
        }
        Document doc;
        try {
            doc = db.parse(entityStream);
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

        return readCompanyNode(nodeList.item(0));
    }

    public static Company readCompanyNode(final Node companyNode) {
        EntityFactory ef = EntityFactoryBuilder.newEntityFactory();
        Company company = ef.createCompany();

        Node child = companyNode.getFirstChild();
        while (child != null) {
            if ("city".equals(child.getNodeName().toLowerCase())) {
                company.setCity(child.getTextContent());
            }
            else if ("id".equals(child.getNodeName().toLowerCase())) {
                company.setId(Integer.parseInt(child.getTextContent()));
            }
            else if ("old-id".equals(child.getNodeName().toLowerCase())) {
                company.setOldId(Integer.parseInt(child.getTextContent()));
            }
            else if ("name".equals(child.getNodeName().toLowerCase())) {
                company.setName(child.getTextContent());
            }
            else if ("street".equals(child.getNodeName().toLowerCase())) {
                company.setStreet(child.getTextContent());
            }
            else if ("housenumber".equals(child.getNodeName().toLowerCase())) {
                company.setHousenumber(Integer.parseInt(child.getTextContent()));
            }
            else if ("housenumber-additional".equals(child.getNodeName().toLowerCase())) {
                company.setHousenumberAdditional(child.getTextContent());
            }
            else if ("postcode".equals(child.getNodeName().toLowerCase())) {
                company.setPostcode(child.getTextContent());
            }

            child = child.getNextSibling();
        }

        return company;
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
        return fromXml(entityStream);
    }
}
