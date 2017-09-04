/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import tam.workspace.CSGWorkspace;
import tam.workspace.ScheduleWorkspace;

/**
 *
 * @author Bilal
 */
public class ScheduleData {

    // WE'LL NEED ACCESS TO THE APP TO NOTIFY THE GUI WHEN DATA CHANGES
    TAManagerApp app;

    // NOTE THAT THIS DATA STRUCTURE WILL DIRECTLY STORE THE
    // DATA IN THE ROWS OF THE TABLE VIEW
    ObservableList<ScheduleItems> scheduleTable;
    ObservableList<ScheduleTypeItems> scheduleHolidayType;
    int startDay, startMonth, endDay, endMonth, year;

    public ScheduleData(TAManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // CONSTRUCT THE LIST OF TAs FOR THE TABLE
        scheduleTable = FXCollections.observableArrayList();
        scheduleHolidayType = FXCollections.observableArrayList();

        // THESE ARE THE LANGUAGE-DEPENDENT OFFICE HOURS GRID HEADERS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
    }

    public void resetData() {
        scheduleTable.clear();
    }

    public ObservableList getScheduleTable() {
        return scheduleTable;
    }

    public ObservableList getScheduleHolidayType() {
        return scheduleHolidayType;
    }

    public int getStartDay() {
        return startDay;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public int getEndDay() {
        return endDay;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public int getYear() {
        return year;
    }
    
     public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void addCalenderBoundries(String initStartMondayDay, String initStartMondayMonth, String initEndFridayDay, String initEndFridayMonth, String initYear) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        startDay = Integer.parseInt(initStartMondayDay);
        startMonth = Integer.parseInt(initStartMondayMonth);
        endDay = Integer.parseInt(initEndFridayDay);
        endMonth = Integer.parseInt(initEndFridayMonth);
        year = Integer.parseInt(initYear);
        workspace.getScheduleWorkspace().reloadWorkspace();
    }

    public void addSchedule(String initType, String initDate, String initTitle, String time, String initTopic, String initLink, String initCriteria) {
        String Month = initDate.substring(0, 2);
        Month = Month.replaceAll("/", "");
        String Day = initDate.substring(2, 4);
        Day = Day.replaceAll("/", "");
        ScheduleItems schedule = new ScheduleItems(initType, initDate, initTitle, time, initTopic, initLink, initCriteria, Month, Day);
        scheduleTable.add(schedule);
     
    }

}
