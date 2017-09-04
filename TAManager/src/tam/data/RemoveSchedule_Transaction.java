/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import javafx.collections.ObservableList;
import jtps.jTPS_Transaction;

/**
 *
 * @author Bilal
 */
public class RemoveSchedule_Transaction implements jTPS_Transaction {

    ObservableList<ScheduleItems> scheduleItems;
    ScheduleItems schedule;

    public RemoveSchedule_Transaction(ObservableList<ScheduleItems> scheduleItems, ScheduleItems schedule) {
        this.scheduleItems = scheduleItems;
        this.schedule = schedule;
    }

    @Override
    public void doTransaction() {
        scheduleItems.remove(schedule);

    }

    @Override
    public void undoTransaction() {
        scheduleItems.add(schedule);
    }
}
