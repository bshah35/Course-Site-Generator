/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import java.util.Collections;
import javafx.collections.ObservableList;
import jtps.jTPS_Transaction;

/**
 *
 * @author Bilal
 */
public class AddSchedule_Transaction implements jTPS_Transaction {

    ObservableList<ScheduleItems> scheduleItems;
    ScheduleItems schedule;

    public AddSchedule_Transaction(ObservableList<ScheduleItems> scheduleItems, ScheduleItems schedule) {
        this.scheduleItems = scheduleItems;
        this.schedule = schedule;
    }

    @Override
    public void doTransaction() {
        scheduleItems.add(schedule);

    }

    @Override
    public void undoTransaction() {
        scheduleItems.remove(schedule);
    }

}
