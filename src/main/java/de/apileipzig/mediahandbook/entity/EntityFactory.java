package de.apileipzig.mediahandbook.entity;

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

    /**
     * Get a new instance of a branch implementation.
     *
     * @return instance of a branch
     */
    Branch newBranch();
}
