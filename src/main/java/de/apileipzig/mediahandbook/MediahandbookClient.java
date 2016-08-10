package de.apileipzig.mediahandbook;

import de.apileipzig.ApiLeipzigClient;
import de.apileipzig.mediahandbook.entity.Company;
import de.apileipzig.mediahandbook.io.messageBodyReader.CompanyListXmlUtf8Reader;
import de.apileipzig.mediahandbook.io.messageBodyReader.CompanyXmlUtf8Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

/**
 * A client to access data from Mediahandbook.
 *
 * @author cpieloth
 */
public class MediahandbookClient extends ApiLeipzigClient {
    private static final Logger log = LoggerFactory.getLogger(MediahandbookClient.class);

    protected WebTarget mediabookTarget = null;

    public MediahandbookClient() {
        super();
    }

    public MediahandbookClient(final String baseUrl, final String version) {
        super(baseUrl, version);
    }

    @Override
    public boolean open() {
        final boolean rc = super.open();
        mediabookTarget = baseTarget.path("mediahandbook");
        return rc;
    }

    @Override
    protected void registerJaxRsComponents(ClientBuilder clientBuilder) {
        clientBuilder.register(CompanyXmlUtf8Reader.class);
        clientBuilder.register(CompanyListXmlUtf8Reader.class);
    }

    public List<Company> getCompanies() {
        LinkedList<Company> destination = new LinkedList<>();
        if(getCompanies(destination)) {
            return destination;
        }
        else {
            return null;
        }
    }

    public boolean getCompanies(final List<Company> destination) {
        WebTarget companiesTarget = addQueryParam(mediabookTarget.path("companies"));

        Response response = companiesTarget.request(MEDIA_TYPE).get();
        final int status = response.getStatus();
        if(status != Response.Status.OK.getStatusCode()) {
            log.error("Unsuccessful request! Response status: {}", status);
            return false;
        }

        final List<Company> list = response.readEntity(new GenericType<List<Company>>() {});
        destination.addAll(list);
        return list != null;
    }

    public Company getCompany(int id) {
        WebTarget companiesTarget = addQueryParam(mediabookTarget.path("companies/" + id));

        Response response = companiesTarget.request(MEDIA_TYPE).get();
        final int status = response.getStatus();
        if(status != Response.Status.OK.getStatusCode()) {
            log.error("Unsuccessful request! Response status: {}", status);
            return null;
        }

        return response.readEntity(Company.class);
    }

    public static void main(String[] args) {
        MediahandbookClient client = new MediahandbookClient();
        client.open();
        System.out.println(client.getCompanies().size());
        client.close();
    }
}
