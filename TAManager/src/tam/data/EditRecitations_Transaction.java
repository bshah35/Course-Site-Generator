/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;

/**
 *
 * @author Bilal
 */
public class EditRecitations_Transaction implements jTPS_Transaction {

    String oldSection, newSection, oldTa1, newTa1, oldTa2, newTa2, oldInstructor, newInstructor, oldDate, newDate, oldLocation, newLocation;
    RecitationItems recitations;
    TableView recitationTable;

    public EditRecitations_Transaction(RecitationItems recitations, String oldSection, String oldInstructor, String oldDate, String oldLocation, String oldTa1, String oldTa2,
            String newSection, String newInstructor, String newDate, String newLocation, String newTa1, String newTa2, TableView recitationTable) {

        this.recitations = recitations;
        this.oldSection = oldSection;
        this.oldInstructor = oldInstructor;
        this.oldDate = oldDate;
        this.oldLocation = oldLocation;
        this.oldTa1 = oldTa1;
        this.oldTa2 = oldTa2;
        this.newSection = newSection;
        this.newInstructor = newInstructor;
        this.newDate = newDate;
        this.newLocation = newLocation;
        this.newTa1 = newTa1;
        this.newTa2 = newTa2;
        this.recitationTable = recitationTable;
    }

    @Override
    public void doTransaction() {
        recitations.setSections(newSection);
        recitations.setInstructor(newInstructor);
        recitations.setDate(newDate);
        recitations.setLocation(newLocation);
        recitations.setName(newTa1);
        recitations.setName2(newTa2);
        recitationTable.refresh();
    }

    @Override
    public void undoTransaction() {
        recitations.setSections(oldSection);
        recitations.setInstructor(oldInstructor);
        recitations.setDate(oldDate);
        recitations.setLocation(oldLocation);
        recitations.setName(oldTa1);
        recitations.setName2(oldTa2);
        recitationTable.refresh();
    }

}
