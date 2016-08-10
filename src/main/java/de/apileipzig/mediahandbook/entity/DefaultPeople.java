package de.apileipzig.mediahandbook.entity;

/**
 * Default implementation of a People.
 *
 * @author cpieloth
 */
public class DefaultPeople implements People {
    private int id;
    private int companyId;
    private String firstName;
    private String lastName;
    private String title;
    private String position;
    private String occupation;


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(int id) {
        this.companyId = id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getPosition() {
        return position;
    }

    @Override
    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String getOccupation() {
        return occupation;
    }

    @Override
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @Override
    public String toString() {
        return "" + this.getClass().getSimpleName() + "[id=" + id + ",lastName=" + lastName + ",...]";
    }
}
