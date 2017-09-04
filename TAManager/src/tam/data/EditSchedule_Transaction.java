/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;

/**
 *
 * @author Bilal
 */
public class EditSchedule_Transaction implements jTPS_Transaction {

    String oldTitle, newTitle, oldTime, newTime, oldLink, newLink, oldTopic, newTopic, oldDate, newDate, oldType, newType, oldCriteria, newCriteria, month, day, oldMonth, oldDay;
    ScheduleItems schedule;
    TableView scheduleTable;

    public EditSchedule_Transaction(ScheduleItems schedule, String oldType, String oldDate, String oldTitle, String oldTime, String oldTopic, String oldLink,
            String oldCriteria, String newType, String newDate, String newTitle, String newTime, String newTopic, String newLink, String newCriteria, TableView scheduleTable, 
            String month, String day, String oldMonth, String oldDay) {
        
        this.schedule = schedule;
        this.oldTitle = oldTitle;
        this.oldType = oldType;
        this.oldCriteria = oldCriteria;
        this.oldLink = oldLink;
        this.oldDate = oldDate;
        this.oldTopic = oldTopic;
        this.oldTime = oldTime;
        this.newTime = newTime;
        this.newDate = newDate;
        this.newLink = newLink;
        this.newTopic = newTopic;
        this.newTitle = newTitle;
        this.newType = newType;
        this.newCriteria = newCriteria;
        this.month = month;
        this.day = day;
        this.scheduleTable = scheduleTable;

    }

    @Override
    public void doTransaction() {
        schedule.setCriteria(newCriteria);
        schedule.setDate(newDate);
        schedule.setTopic(newTopic);
        schedule.setTitle(newTitle);
        schedule.setTime(newTime);
        schedule.setType(newType);
        schedule.setLink(newLink);
        schedule.setMonth(month);
        schedule.setDay(day);
        scheduleTable.refresh();
        
    }
    

    @Override
    public void undoTransaction() {
        schedule.setCriteria(oldCriteria);
        schedule.setDate(oldDate);
        schedule.setTopic(oldTopic);
        schedule.setTitle(oldTitle);
        schedule.setTime(oldTime);
        schedule.setType(oldType);
        schedule.setLink(oldLink);
        schedule.setMonth(oldMonth);
        schedule.setDay(oldDay);
        scheduleTable.refresh();
    }

}
