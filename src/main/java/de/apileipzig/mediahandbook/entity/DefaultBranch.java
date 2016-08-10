package de.apileipzig.mediahandbook.entity;

/**
 * Default implementation of a Branch.
 *
 * @author cpieloth
 */
public class DefaultBranch implements Branch {
    private int id;
    private int parentId;
    private String internalType;
    private String internalKey;
    private String name;
    private String description;


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(int id) {
        this.parentId = id;
    }

    @Override
    public String getInternalType() {
        return name;
    }

    @Override
    public void setInternalType(String internalType) {
        this.internalType = internalType;
    }

    @Override
    public String getInternalKey() {
        return internalKey;
    }

    @Override
    public void setInternalKey(String internalKey) {
        this.internalKey = internalKey;
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
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String street) {
        this.description = street;
    }

    @Override
    public String toString() {
        return "" + this.getClass().getSimpleName() + "[id=" + id + ",name=" + name + ",...]";
    }
}
