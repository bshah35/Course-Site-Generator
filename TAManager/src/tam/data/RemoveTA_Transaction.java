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
import tam.workspace.TAController;
import tam.workspace.TAWorkspace;

/**
 *
 * @author Bilal
 */
public class RemoveTA_Transaction implements jTPS_Transaction {

    HashMap<String, StringProperty> officeHours;
    boolean flag = false;
    TeachingAssistant ta;
    String taName;
    CSGWorkspace workspace;
    CSGData data;
    ArrayList<String> key = new ArrayList();
    ObservableList<TeachingAssistant> teachingAssistants;
    HashMap<String, Pane> officeTACellPane;

    public RemoveTA_Transaction(String taName, HashMap<String, StringProperty> officeHours,
            ObservableList<TeachingAssistant> teachingAssistants, TeachingAssistant ta, HashMap<String, Pane> officeTACellPane, boolean flag, CSGWorkspace workspace, CSGData data) {
        this.ta = ta;
        this.teachingAssistants = teachingAssistants;
        this.taName = taName;
        this.officeHours = officeHours;
        this.officeTACellPane = officeTACellPane;
        this.flag = flag;
        this.workspace = workspace;
        this.data = data;
    }

    @Override
    public void doTransaction() {
        if (!flag) {
            for (Pane pane : officeTACellPane.values()) {
                String cellKey = pane.getId();
                removeTaFromTable(cellKey, taName);
            }

            flag = true;

        }

        if (flag) {
            teachingAssistants.remove(ta);
            for (int i = 0; i < data.getRecitationData().getRecitationTable().size(); i++) {
                RecitationItems recitation = (RecitationItems) data.getRecitationData().getRecitationTable().get(i);
                TableView recitTable = workspace.getRecitationWorkspace().getRecitationTable();
                if (ta.getName().equalsIgnoreCase(recitation.getTa1())) {
                    recitation.setName("");
                    recitTable.refresh();
                } else if (ta.getName().equalsIgnoreCase(recitation.getTa2())) {
                    recitation.setName2("");
                    recitTable.refresh();
                }
            }
            flag = false;
        }

    }

    @Override
    public void undoTransaction() {
        if (!flag) {
            for (int i = 0; i < key.size(); i++) {
                String cellKey = key.get(i);
                StringProperty cellProp = officeHours.get(cellKey);
                if (cellProp != null) {
                    String cellText = cellProp.getValue();

                    if (cellText.contains(taName)) {
                        removeTAFromCell(cellProp, taName);
                    } else {
                        cellProp.setValue(taName + "\n" + cellText);
                    }

                }
            }
            key.clear();
            flag = true;
        }

        if (flag) {
            if (!teachingAssistants.contains(ta)) {
                teachingAssistants.add(ta);
                for (int i = 0; i < data.getRecitationData().getRecitationTable().size(); i++) {
                    RecitationItems recitation = (RecitationItems) data.getRecitationData().getRecitationTable().get(i);
                    TableView recitTable = workspace.getRecitationWorkspace().getRecitationTable();
                    if (recitation.getTa1().equalsIgnoreCase("")) {
                        recitation.setName(ta.getName());
                        recitTable.refresh();
                    } else if (recitation.getTa2().equalsIgnoreCase("")) {
                        recitation.setName2(ta.getName());
                        recitTable.refresh();
                    }
                }
                flag = false;
            }
            Collections.sort(teachingAssistants);
        }
    }

    public void removeTaFromTable(String cellKey, String taName) {
        StringProperty cellProp = officeHours.get(cellKey);
        String cellText = cellProp.getValue();
        if (cellText.contains(taName)) {
            key.add(cellKey);
            removeTAFromCell(cellProp, taName);
        }

    }

    public void removeTAFromCell(StringProperty cellProp, String taName) {
        // GET THE CELL TEXT
        String cellText = cellProp.getValue();
        // IS IT THE ONLY TA IN THE CELL?
        if (cellText.equals(taName)) {
            cellProp.setValue("");
        } // IS IT THE FIRST TA IN A CELL WITH MULTIPLE TA'S?
        else if (cellText.indexOf(taName) == 0) {
            int startIndex = cellText.indexOf("\n") + 1;
            cellText = cellText.substring(startIndex);
            cellProp.setValue(cellText);
        } // IT MUST BE ANOTHER TA IN THE CELL
        else {
            // int startIndex = cellText.indexOf("\n" + taName);  
            cellText = cellText.replaceAll("\n" + taName, "");
            // cellText = cellText.substring(startIndex,LastIndex);

            cellProp.setValue(cellText);
        }
    }

}
