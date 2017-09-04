/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Bilal
 */
public class RecitationItems<E extends Comparable<E>> implements Comparable<E> {

    // THE TABLE WILL STORE RECITATIONS ITEMS   
    private final StringProperty sections, instructor, date, location, ta1, ta2;

    public RecitationItems(String initSections, String initInstructor, String initDate, String initLocation, String initName, String initName2) {
        sections = new SimpleStringProperty(initSections);
        instructor = new SimpleStringProperty(initInstructor);
        date = new SimpleStringProperty(initDate);
        location = new SimpleStringProperty(initLocation);
        ta1 = new SimpleStringProperty(initName);
        ta2 = new SimpleStringProperty(initName2);
    }

    public String getInstructor() {
        return instructor.get();
    }

    public String getSections() {
        return sections.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getLocation() {
        return location.get();
    }

    public String getTa1() {
        return ta1.get();
    }

    public String getTa2() {
        return ta2.get();
    }

    public void setInstructor(String initInstructor) {
        instructor.set(initInstructor);
    }

    public void setSections(String initSections) {
        sections.set(initSections);
    }

    public void setDate(String initDate) {
        date.set(initDate);
    }

    public void setLocation(String initLocation) {
        location.set(initLocation);
    }

    public void setName(String initName) {
        ta1.set(initName);
    }

    public void setName2(String initName) {
        ta2.set(initName);
    }

    @Override
    public int compareTo(E otherSections) {
        return getSections().compareTo(((RecitationItems) otherSections).getSections());
    }

    @Override
    public String toString() {
        return instructor.getValue() + sections.getValue() + date.getValue() + location.getValue() + ta1.getValue() + ta2.getValue();
    }

}
