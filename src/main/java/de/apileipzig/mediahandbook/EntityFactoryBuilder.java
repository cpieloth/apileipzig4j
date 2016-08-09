package de.apileipzig.mediahandbook;

/**
 * TODO
 *
 * @author cpieloth
 */
public final class EntityFactoryBuilder {

    private static EntityFactory instance = new DefaultEntityFactory();

    public static EntityFactory newEntityFactory() {
        return instance;
    }

    public static void setEntityFactory(EntityFactory ef) {
        instance = ef;
    }
}
