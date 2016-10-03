package de.apileipzig.entity;

import java.net.URL;

/**
 * Default implementation of an Entity.
 *
 * @author cpieloth
 */
public abstract class DefaultEntity implements Entity {
    protected int id;
    protected URL url;

    protected DefaultEntity() {
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
