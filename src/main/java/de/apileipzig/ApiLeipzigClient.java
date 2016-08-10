package de.apileipzig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Base class for API.Leipzig service endpoints.
 *
 * @author cpieloth
 */
public abstract class ApiLeipzigClient {
    private static final String DEFAULT_BASE_URL = "http://www.apileipzig.de/api";
    private static final String DEFAULT_VERSION = "v1";

    private static final String PARAM_API_KEY = "api_key";
    private static final String PARAM_FORMAT = "format";
    private static final String PARAM_FORMAT_VALUE = "xml";
    protected static final MediaType MEDIA_TYPE = MediaType.TEXT_XML_TYPE.withCharset("utf-8");

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

    /**
     * Register JAX-RS components, e.g. serializer.
     *
     * @param clientBuilder ClientBuilder which should use the registered components.
     */
    protected abstract void registerJaxRsComponents(ClientBuilder clientBuilder);

    /**
     * Set an API key to get access to the service.
     *
     * @param apiKey API key.
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Open/initialize the API.Leipzig client.
     *
     * @return True on success.
     */
    public boolean open() {
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        registerJaxRsComponents(clientBuilder);
        client = clientBuilder.build();

        if (client != null) {
            baseTarget = client.target(baseUrl + "/" + version);
        }
        return this.client != null;
    }

    /**
     * Close/deinitialize the API.Leipzig client.
     */
    public void close() {
        if (client != null) {
            client.close();
        }
    }

    /**
     * Add general query parameter, e.g. API key and format.
     *
     * @param webTarget WebTarget to add the query parameter.
     * @return A new WebTarget instance.
     */
    protected WebTarget addQueryParam(WebTarget webTarget) {
        return webTarget.queryParam(PARAM_API_KEY, apiKey).queryParam(PARAM_FORMAT, PARAM_FORMAT_VALUE);
    }
}
