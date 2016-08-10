package de.apileipzig.mediahandbook.entity;

/**
 * Default implementation of a Company.
 *
 * @author cpieloth
 */
public class DefaultCompany implements Company {
    private int id;
    private int oldId;
    private String name;
    private String street;
    private int housenumber;
    private String housenumberAdditional;
    private String postcode;
    private String city;


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getOldId() {
        return oldId;
    }

    @Override
    public void setOldId(int id) {
        this.oldId = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public int getHousenumber() {
        return housenumber;
    }

    @Override
    public void setHousenumber(int housenumber) {
        this.housenumber = housenumber;
    }

    public String getHousenumberAdditional() {
        return housenumberAdditional;
    }

    public void setHousenumberAdditional(String housenumberAdditional) {
        this.housenumberAdditional = housenumberAdditional;
    }

    @Override
    public String getPostcode() {
        return postcode;
    }

    @Override
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "" + this.getClass().getSimpleName() + "[id=" + id + ",name=" + name + ",...]";
    }
}
