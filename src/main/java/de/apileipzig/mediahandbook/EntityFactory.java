package de.apileipzig.mediahandbook;

import de.apileipzig.mediahandbook.entity.Company;

/**
 * A entity factory for mediahandbook.
 * Creation of a concrete entity types, e.g. for JPA implementation.
 *
 * @author cpieloth
 */
public interface EntityFactory {
    Company createCompany();
}
