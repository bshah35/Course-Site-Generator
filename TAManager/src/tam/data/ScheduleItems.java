/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 * @author Bilal
 */
public class ScheduleItems {

    // THE TABLE WILL STORE RECITATIONS ITEMS   
    private final StringProperty type, date, title, topic;
    private String link, time, criteria, month, day;

    public ScheduleItems(String initType, String initDate, String initTitle, String initTime, String initTopic, String initLink, String initCriteria, String initMonth, String initDay) {
        type = new SimpleStringProperty(initType);
        date = new SimpleStringProperty(initDate);
        title = new SimpleStringProperty(initTitle);
        topic = new SimpleStringProperty(initTopic);
        time = initTime;
        link = initLink;
        criteria = initCriteria;
        month = initMonth;
        day = initDay;
    }

    public String getLink() {
        return link;
    }

    public String getTime() {
        return time;
    }

    public String getCriteria() {
        return criteria;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getType() {
        return type.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getTopic() {
        return topic.get();
    }

    public void setType(String initType) {
        type.set(initType);
    }

    public void setTitle(String initTitle) {
        title.set(initTitle);
    }

    public void setDate(String initDate) {
        date.set(initDate);
    }

    public void setTopic(String initTopic) {
        topic.set(initTopic);
    }

    public void setLink(String initLink) {
        link = initLink;
    }

    public void setCriteria(String initCriteria) {
        criteria = initCriteria;
    }

    public void setTime(String initTime) {
        time = initTime;
    }

    public void setDay(String initDay) {
        day = initDay;
    }

    public void setMonth(String initMonth) {
        month = initMonth;
    }

    @Override
    public String toString() {
        return type.getValue() + title.getValue() + date.getValue() + topic.getValue();
    }
}
