package de.apileipzig.mediahandbook.entity;

/**
 * A branch of the mediahandbook.
 * See http://www.apileipzig.de/wiki/show/Ressourcen for more details.
 *
 * TODO(cpieloth): Add missing getter/setter.
 *
 * @author cpieloth
 */
public interface Branch {
    int getId();
    void setId(int id);

    int getParentId();
    void setParentId(int id);

    String getInternalType();
    void setInternalType(String internalType);

    String getInternalKey();
    void setInternalKey(String internalKey);

    String getName();
    void setName(String name);

    String getDescription();
    void setDescription(String description);
}
