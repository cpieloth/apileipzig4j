package de.apileipzig.mediahandbook.messageBodyReader;

import de.apileipzig.mediahandbook.entity.Branch;
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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * XML deserialization for branch entity lists.
 *
 * @author cpieloth
 */
public class BranchListXmlUtf8Reader implements MessageBodyReader<List<Branch>> {

    private static final Logger log = LoggerFactory.getLogger(BranchListXmlUtf8Reader.class);

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        // taken from:
        // http://christopherhunt-software.blogspot.de/2010/08/messagebodywriter-iswriteable-method.html
        boolean isReadable = false;
        if (List.class.isAssignableFrom(type) && genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] actualTypeArgs = (parameterizedType.getActualTypeArguments());
            isReadable = (actualTypeArgs.length == 1 && actualTypeArgs[0].equals(Branch.class));
        }

        return isReadable;
    }

    @Override
    public List<Branch> readFrom(Class<List<Branch>> type, Type genericType, Annotation[] annotations,
        MediaType mediaType,
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

        NodeList nodeList = hashNode.getElementsByTagName("data");
        if (nodeList == null || nodeList.getLength() != 1) {
            log.error("No or to many data nodes!");
            return null;
        }

        LinkedList<Branch> branches = new LinkedList<>();
        Node child = nodeList.item(0).getFirstChild();
        while (child != null) {
            Branch branch = BranchXmlUtf8Reader.readEntityNode(child);
            if (branch != null) {
                branches.add(branch);
            }

            child = child.getNextSibling();
        }

        return branches;
    }
}
