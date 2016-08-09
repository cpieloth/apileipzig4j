package de.apileipzig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * @author cpieloth
 */
public abstract class ApiLeipzigClient {
    private static final String DEFAULT_BASE_URL = "http://www.apileipzig.de/api";
    private static final String DEFAULT_VERSION = "v1";

    private static final String PARAM_API_KEY = "api_key";
    private static final String PARAM_FORMAT = "format";
    private static final String PARAM_FORMAT_VALUE = "xml";
    protected static final MediaType MEDIA_TYPE = MediaType.TEXT_XML_TYPE;

    private String version = DEFAULT_VERSION;
    private String baseUrl = DEFAULT_BASE_URL;
    private String apiKey = null;
    private Client client = null;
    protected WebTarget baseTarget = null;

    protected ApiLeipzigClient() {
        super();
        setApiKey(System.getenv("APILEIPZIG_API_KEY"));
    }

    protected ApiLeipzigClient(final String baseUrl, final String version) {
        this();
        this.baseUrl = baseUrl;
        this.version = version;
    }

    protected abstract void registerJaxRsComponents(ClientBuilder clientBuilder);

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public boolean open() {
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        registerJaxRsComponents(clientBuilder);
        client =clientBuilder.build();

        if(client != null) {
            baseTarget = client.target(baseUrl + "/" + version);
        }
        return this.client != null;
    }

    public void close() {
        if(client != null) {
            client.close();
        }
    }

    protected WebTarget addQueryParam(WebTarget webTarget) {
        return webTarget.queryParam(PARAM_API_KEY, apiKey).queryParam(PARAM_FORMAT, PARAM_FORMAT_VALUE);
    }
}
