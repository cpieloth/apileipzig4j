package de.apileipzig.entity;

import java.net.URL;

/**
 * Basic entity entity interface.
 *
 * @author cpieloth
 */
public interface Entity {
    URL getUrl();
    void setUrl(URL uri);

    int getId();
    void setId(int id);
}
