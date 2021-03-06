package de.apileipzig.mediahandbook.entity;

/**
 * Default implementation of a EntityFactory.
 *
 * @author cpieloth
 */
public class DefaultEntityFactory implements EntityFactory {
    @Override
    public DefaultCompany newCompany() {
        return new DefaultCompany();
    }

    @Override
    public DefaultBranch newBranch() {
        return new DefaultBranch();
    }

    @Override
    public DefaultPeople newPeople() {
        return new DefaultPeople();
    }
}
