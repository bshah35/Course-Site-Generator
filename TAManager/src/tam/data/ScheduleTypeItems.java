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
public class ScheduleTypeItems {
     // THE TABLE WILL STORE RECITATIONS ITEMS   
    private final StringProperty month, day, title, link, criteria;

    public ScheduleTypeItems(String initMonth, String initTitle, String initLink, String initDay, String initCriteria, String time) {
        month = new SimpleStringProperty(initMonth);
        title = new SimpleStringProperty(initTitle);
        day = new SimpleStringProperty(initDay);
        link = new SimpleStringProperty(initLink);
        criteria = new SimpleStringProperty(initCriteria);

    }

    public String getMonth() {
        return month.get();
    }

    public String getDay() {
        return day.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getLink() {
        return link.get();
    }
    
     public String getCriteria() {
        return criteria.get();
    }

    public void setMonth(String initMonth) {
        month.set(initMonth);
    }

    public void setTitle(String initTitle) {
        title.set(initTitle);
    }

    public void setDay(String initDay) {
        day.set(initDay);
    }

    public void setLink(String initLink) {
        link.set(initLink);
    }
    
    public void setCriteria(String initCriteria) {
        criteria.set(initCriteria);
    }

    @Override
    public String toString() {
        return month.getValue() + day.getValue() + title.getValue() + link.getValue() + criteria.getValue();
    }
}
