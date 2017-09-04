/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.DatePicker;
import jtps.jTPS_Transaction;
import tam.workspace.ScheduleWorkspace;

/**
 *
 * @author Bilal
 */
public class AddCalenderBoundries_Transaction implements jTPS_Transaction {

    String startMondayDay, startMondayMonth, endFridayDay, endFridayMonth, oldStartMondayDay, oldMondayMonth, oldFridayDay, oldFridayMonth,year,oldYear;
    ScheduleData data;
    DatePicker startDatePicker, endDatePicker;
    ScheduleWorkspace workspace;

    public AddCalenderBoundries_Transaction(String startMondayDay, String startMondayMonth, String oldMondayDay, String oldMondayMonth, String year, String oldYear, ScheduleData data, DatePicker startDatePicker,
            String endFridayDay, String endFridayMonth, String oldFridayDay, String oldFridayMonth, DatePicker endDatePicker, ScheduleWorkspace workspace) {
        this.startMondayDay = startMondayDay;
        this.startMondayMonth = startMondayMonth;
        this.oldStartMondayDay = oldMondayDay;
        this.oldMondayMonth = oldMondayMonth;
        this.startDatePicker = startDatePicker;
        this.data = data;
        this.endDatePicker = endDatePicker;
        this.endFridayDay = endFridayDay;
        this.endFridayMonth = endFridayMonth;
        this.oldFridayDay = oldFridayDay;
        this.oldFridayMonth = oldFridayMonth;
        this.endDatePicker = endDatePicker;
        this.workspace = workspace;
        this.year = year;
        this.oldYear = oldYear;
    }

    @Override
    public void doTransaction() {
        data.setStartDay(Integer.parseInt(startMondayDay));
        data.setStartMonth(Integer.parseInt(startMondayMonth));
        data.setEndDay(Integer.parseInt(endFridayDay));
        data.setEndMonth(Integer.parseInt(endFridayMonth));
        data.setYear(Integer.parseInt(year));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        String year = Integer.toString(data.getYear());
        String month = Integer.toString(data.getStartMonth());
        String day = Integer.toString(data.getStartDay());
        String date = "";
        if (Integer.parseInt(month) < 10) {
            month = "0" + month;
        }
        if (Integer.parseInt(day) < 10) {
            day = "0" + day;
        }
        date = month + "/" + day + "/" + year;
        LocalDate d = LocalDate.parse(date, formatter);
        workspace.setFlag(false);
        startDatePicker.setValue(d);

        year = Integer.toString(data.getYear());
        month = Integer.toString(data.getEndMonth());
        day = Integer.toString(data.getEndDay());
        date = "";
        if (Integer.parseInt(month) < 10) {
            month = "0" + month;
        }
        if (Integer.parseInt(day) < 10) {
            day = "0" + day;
        }
        date = month + "/" + day + "/" + year;
        d = LocalDate.parse(date, formatter);
        workspace.setFlag3(false);
        endDatePicker.setValue(d);

    }

    @Override
    public void undoTransaction() {
        data.setStartDay(Integer.parseInt(oldStartMondayDay));
        data.setStartMonth(Integer.parseInt(oldMondayMonth));
        data.setEndDay(Integer.parseInt(oldFridayDay));
        data.setEndMonth(Integer.parseInt(oldFridayMonth));
        data.setYear(Integer.parseInt(oldYear));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        String year = Integer.toString(data.getYear());
        String month = Integer.toString(data.getStartMonth());
        String day = Integer.toString(data.getStartDay());
        String date = "";
        if (Integer.parseInt(month) < 10) {
            month = "0" + month;
        }
        if (Integer.parseInt(day) < 10) {
            day = "0" + day;
        }
        date = month + "/" + day + "/" + year;
        LocalDate d = LocalDate.parse(date, formatter);
        workspace.setFlag(false);
        startDatePicker.setValue(d);

        year = Integer.toString(data.getYear());
        month = Integer.toString(data.getEndMonth());
        day = Integer.toString(data.getEndDay());
        date = "";
        if (Integer.parseInt(month) < 10) {
            month = "0" + month;
        }
        if (Integer.parseInt(day) < 10) {
            day = "0" + day;
        }
        date = month + "/" + day + "/" + year;
        d = LocalDate.parse(date, formatter);
        workspace.setFlag3(false);
        endDatePicker.setValue(d);

    }

}
