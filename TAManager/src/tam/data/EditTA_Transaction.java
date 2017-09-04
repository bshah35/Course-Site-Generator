/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import jtps.jTPS_Transaction;
import tam.workspace.CSGWorkspace;
import tam.workspace.TAWorkspace;

/**
 *
 * @author Bilal
 */
public class EditTA_Transaction implements jTPS_Transaction {

    HashMap<String, StringProperty> officeHours;
    boolean flag = false;
    TeachingAssistant ta, newTa;
    String oldTaName, newTaname, oldEmail, newEmail;
    CSGWorkspace workspace;
    CSGData data;
    ArrayList<String> key = new ArrayList();
    ObservableList<TeachingAssistant> teachingAssistants;
    HashMap<String, Pane> officeTACellPane;
    TableView table;
    Object temp;

    public EditTA_Transaction(String oldTaName, String newTaName, String oldEmail, String newEmail, HashMap<String, StringProperty> officeHours,
            ObservableList<TeachingAssistant> teachingAssistants, TeachingAssistant ta, TeachingAssistant newTa, HashMap<String, Pane> officeTACellPane, TableView taTable, boolean flag, CSGWorkspace workspace, CSGData data) {
        this.ta = ta;
        this.newTa = newTa;
        this.teachingAssistants = teachingAssistants;
        this.oldTaName = oldTaName;
        this.newTaname = newTaName;
        this.oldEmail = oldEmail;
        this.newEmail = newEmail;
        this.officeHours = officeHours;
        this.officeTACellPane = officeTACellPane;
        this.table = taTable;
        this.flag = flag;
        this.workspace = workspace;
        this.data = data;
    }

    @Override
    public void doTransaction() {

        if (!flag) {

            for (Pane pane : officeTACellPane.values()) {
                String cellKey = pane.getId();
                editTA(cellKey, oldTaName, newTaname);
            }

            flag = true;
        }
        if (flag) {

            for (int i = 0; i < data.getRecitationData().getRecitationTable().size(); i++) {
                RecitationItems recitation = (RecitationItems) data.getRecitationData().getRecitationTable().get(i);
                TableView recitTable = workspace.getRecitationWorkspace().getRecitationTable();
                if (ta.getName().equalsIgnoreCase(recitation.getTa1())) {
                    recitation.setName(newTaname);
                    recitTable.refresh();
                } else if (ta.getName().equalsIgnoreCase(recitation.getTa2())) {
                    recitation.setName2(newTaname);
                    recitTable.refresh();
                }
            }
            ta.setName(newTaname);
            ta.setEmail(newEmail);

            table.refresh();
            flag = false;

        }

    }

    @Override
    public void undoTransaction() {

        if (!flag) {
            for (Pane pane : officeTACellPane.values()) {
                String cellKey = pane.getId();
                editTA(cellKey, newTaname, oldTaName);
            }

            flag = true;
        }

        if (flag) {
              for (int i = 0; i < data.getRecitationData().getRecitationTable().size(); i++) {
                RecitationItems recitation = (RecitationItems) data.getRecitationData().getRecitationTable().get(i);
                TableView recitTable = workspace.getRecitationWorkspace().getRecitationTable();
                if (ta.getName().equalsIgnoreCase(recitation.getTa1())) {
                    recitation.setName(oldTaName);
                    recitTable.refresh();
                } else if (ta.getName().equalsIgnoreCase(recitation.getTa2())) {
                    recitation.setName2(oldTaName);
                    recitTable.refresh();
                }
            }
            ta.setName(oldTaName);
            ta.setEmail(oldEmail);

            table.refresh();
            flag = false;
        }

    }

    public void editTA(String cellKey, String taName, String newName) {
        StringProperty cellProp = officeHours.get(cellKey);
        String cellText = cellProp.getValue();
        if (cellText.contains(taName)) {
            cellText = cellText.replaceAll(taName, newName);
            cellProp.setValue(cellText);

        }

    }

}
