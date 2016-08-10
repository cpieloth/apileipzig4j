package de.apileipzig.mediahandbook.entity;

import de.apileipzig.mediahandbook.entity.Company;

/**
 * A entity factory for mediahandbook.
 * Creation of a concrete entity types, e.g. for JPA implementation.
 *
 * @author cpieloth
 */
public interface EntityFactory {

    /**
     * Get a new instance of a company implementation.
     *
     * @return instance of a company
     */
    Company newCompany();
}
