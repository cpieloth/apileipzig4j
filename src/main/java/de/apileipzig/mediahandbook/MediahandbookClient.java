package de.apileipzig.mediahandbook;

import de.apileipzig.ApiLeipzigClient;
import de.apileipzig.EntityUrlSetter;
import de.apileipzig.mediahandbook.entity.Branch;
import de.apileipzig.mediahandbook.entity.Company;
import de.apileipzig.mediahandbook.entity.People;
import de.apileipzig.mediahandbook.messageBodyReader.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
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
        clientBuilder.register(BranchXmlUtf8Reader.class);
        clientBuilder.register(BranchListXmlUtf8Reader.class);
        clientBuilder.register(PeopleXmlUtf8Reader.class);
        clientBuilder.register(PeopleListXmlUtf8Reader.class);
    }

    /**
     * Get all companies.
     *
     * @return List of companies or null.
     */
    public List<Company> getCompanies() {
        WebTarget companiesTarget = addQueryParam(mediabookTarget.path("companies"));

        Response response = companiesTarget.request(MEDIA_TYPE).get();
        final int status = response.getStatus();
        if (status != Response.Status.OK.getStatusCode()) {
            log.error("Unsuccessful request! Response status: {}", status);
            return null;
        }

        final List<Company> companies = response.readEntity(new GenericType<List<Company>>() {});
        companies.forEach(new EntityUrlSetter(mediabookTarget.path("companies").getUriBuilder()));
        return companies;
    }

    /**
     * Get a company.
     *
     * @param id ID of the desired company.
     * @return Company instance or null.
     */
    public Company getCompany(int id) {
        WebTarget companiesTarget = addQueryParam(mediabookTarget.path("companies/" + id));

        Response response = companiesTarget.request(MEDIA_TYPE).get();
        final int status = response.getStatus();
        if (status != Response.Status.OK.getStatusCode()) {
            log.error("Unsuccessful request! Response status: {}", status);
            return null;
        }

        Company company = response.readEntity(Company.class);
        new EntityUrlSetter(mediabookTarget.path("companies").getUriBuilder()).accept(company);
        return company;
    }

    /**
     * Get all branches.
     *
     * @return List of branches or null.
     */
    public List<Branch> getBranches() {
        WebTarget branchTarget = addQueryParam(mediabookTarget.path("branches"));

        Response response = branchTarget.request(MEDIA_TYPE).get();
        final int status = response.getStatus();
        if (status != Response.Status.OK.getStatusCode()) {
            log.error("Unsuccessful request! Response status: {}", status);
            return null;
        }

        final List<Branch> branches = response.readEntity(new GenericType<List<Branch>>() {});
        branches.forEach(new EntityUrlSetter(mediabookTarget.path("branches").getUriBuilder()));
        return branches;
    }

    /**
     * Get a branch.
     *
     * @param id ID of the desired branch.
     * @return Branch instance or null.
     */
    public Branch getBranch(int id) {
        WebTarget branchTarget = addQueryParam(mediabookTarget.path("branches/" + id));

        Response response = branchTarget.request(MEDIA_TYPE).get();
        final int status = response.getStatus();
        if (status != Response.Status.OK.getStatusCode()) {
            log.error("Unsuccessful request! Response status: {}", status);
            return null;
        }

        Branch branch = response.readEntity(Branch.class);
        new EntityUrlSetter(mediabookTarget.path("branches").getUriBuilder()).accept(branch);
        return branch;
    }

    /**
     * Get all people.
     *
     * @return List of people or null.
     */
    public List<People> getPeople() {
        WebTarget branchTarget = addQueryParam(mediabookTarget.path("people"));

        Response response = branchTarget.request(MEDIA_TYPE).get();
        final int status = response.getStatus();
        if (status != Response.Status.OK.getStatusCode()) {
            log.error("Unsuccessful request! Response status: {}", status);
            return null;
        }

        final List<People> people = response.readEntity(new GenericType<List<People>>() {});
        people.forEach(new EntityUrlSetter(mediabookTarget.path("people").getUriBuilder()));
        return people;
    }

    /**
     * Get a people.
     *
     * @param id ID of the desired people.
     * @return People instance or null.
     */
    public People getPeople(int id) {
        WebTarget branchTarget = addQueryParam(mediabookTarget.path("people/" + id));

        Response response = branchTarget.request(MEDIA_TYPE).get();
        final int status = response.getStatus();
        if (status != Response.Status.OK.getStatusCode()) {
            log.error("Unsuccessful request! Response status: {}", status);
            return null;
        }

        People people = response.readEntity(People.class);
        new EntityUrlSetter(mediabookTarget.path("people").getUriBuilder()).accept(people);
        return people;
    }
}
