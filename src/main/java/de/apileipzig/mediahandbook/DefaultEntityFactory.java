package de.apileipzig.mediahandbook;

import de.apileipzig.mediahandbook.entity.DefaultCompany;

/**
 * Default implementation of a EntityFactory.
 *
 * @author cpieloth
 */
public class DefaultEntityFactory implements EntityFactory {
    @Override
    public DefaultCompany createCompany() {
        return new DefaultCompany();
    }
}
