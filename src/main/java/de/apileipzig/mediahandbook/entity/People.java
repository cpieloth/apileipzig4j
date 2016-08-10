package de.apileipzig.mediahandbook.entity;

/**
 * A branch of the mediahandbook.
 * See http://www.apileipzig.de/wiki/show/Ressourcen for more details.
 *
 * TODO(cpieloth): Add missing getter/setter.
 *
 * @author cpieloth
 */
public interface People {
    int getId();
    void setId(int id);

    int getCompanyId();
    void setCompanyId(int id);

    String getFirstName();
    void setFirstName(String FirstName);

    String getLastName();
    void setLastName(String LastName);

    String getTitle();
    void setTitle(String Title);

    String getPosition();
    void setPosition(String Position);

    String getOccupation();
    void setOccupation(String occupation);
}
