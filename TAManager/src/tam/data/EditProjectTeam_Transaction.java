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
public class EditProjectTeam_Transaction implements jTPS_Transaction {

    ProjectTeamItems team;
    ProjectTeamExport export;
    String newName, newColor, newTextColor, newLink, oldName, oldColor, oldTextColor, oldLink, newRed, newGreen, newBlue, newExportTextColor, oldRed, oldGreen, oldBlue, oldExportTextColor;
    TableView teamTable;
    CSGData data;

    public EditProjectTeam_Transaction(ProjectTeamItems team, ProjectTeamExport export, String newRed, String newGreen, String newBlue, String newExportTextColor, 
            String oldRed, String oldGreen, String oldBlue, String oldExportTextColor,CSGData data, String newName, String newColor, String newTextColor, String newLink, 
            String oldName, String oldColor, String oldTextColor, String oldLink, TableView teamTable) {

        this.team = team;
        this.export = export;
        this.newRed = newRed;
        this.newGreen = newGreen;
        this.newBlue = newBlue;
        this.newExportTextColor = newExportTextColor;
        this.oldRed = oldRed;
        this.oldGreen = oldGreen;
        this.oldBlue = oldBlue;
        this.oldExportTextColor = oldExportTextColor;
        this.newName = newName;
        this.newColor = newColor;
        this.newTextColor = newTextColor;
        this.newLink = newLink;
        this.oldName = oldName;
        this.oldColor = oldColor;
        this.oldTextColor = oldTextColor;
        this.oldLink = oldLink;
        this.teamTable = teamTable;
        this.data = data;
    }

    @Override
    public void doTransaction() {
        team.setTeamName(newName);
        team.setTeamColor(newColor);
        team.setTeamTextColor(newTextColor);
        team.setTeamLink(newLink);
        teamTable.refresh();
        export.setName(newName);
        export.setRed(newRed);
        export.setGreen(newGreen);
        export.setBlue(newBlue);
        export.setTextColor(newExportTextColor);
    }

    @Override
    public void undoTransaction() {
        team.setTeamName(oldName);
        team.setTeamColor(oldColor);
        team.setTeamTextColor(oldTextColor);
        team.setTeamLink(oldLink);
        teamTable.refresh();
        export.setName(oldName);
        export.setRed(oldRed);
        export.setGreen(oldGreen);
        export.setBlue(oldBlue);
        export.setTextColor(oldExportTextColor);
    }

}
