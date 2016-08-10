package de.apileipzig.mediahandbook.entity;

/**
 * A company of the mediahandbook.
 * See http://www.apileipzig.de/wiki/show/Ressourcen for more details.
 *
 * TODO(cpieloth): Add missing getter/setter.
 *
 * @author cpieloth
 */
public interface Company {
    int getId();
    void setId(int id);

    int getOldId();
    void setOldId(int id);

    String getName();
    void setName(String name);

    String getStreet();
    void setStreet(String street);

    int getHousenumber();
    void setHousenumber(int housenumber);

    String getHousenumberAdditional();
    void setHousenumberAdditional(String housenumberAdditional);

    String getPostcode();
    void setPostcode(String postcode);

    String getCity();
    void setCity(String city);
}
