package de.apileipzig.mediahandbook.io.messageBodyReader;

import de.apileipzig.mediahandbook.EntityFactoryBuilder;
import de.apileipzig.mediahandbook.entity.Company;
import de.apileipzig.mediahandbook.EntityFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
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
    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Company.class.isAssignableFrom(type) && MediaType.TEXT_XML_TYPE.withCharset("utf-8").toString().toLowerCase()
        .equals(mediaType.toString().toLowerCase());
    }

    @Override
    public Company readFrom(Class<Company> type, Type genericType, Annotation[] annotations, MediaType mediaType,
        MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
        throws IOException, WebApplicationException {
        EntityFactory ef = EntityFactoryBuilder.newEntityFactory();
        Company company = ef.createCompany();
        // TODO
        return company;
    }
}
