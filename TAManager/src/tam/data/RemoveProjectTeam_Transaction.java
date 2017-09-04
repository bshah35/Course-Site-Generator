/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import java.util.Collections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;
import tam.workspace.CSGWorkspace;

/**
 *
 * @author Bilal
 */
public class RemoveProjectTeam_Transaction implements jTPS_Transaction {

    ObservableList<ProjectTeamItems> projectItems;
    ProjectTeamItems projectTeams;
    ObservableList<ProjectTeamExport> projectExport;
    ProjectTeamExport export;
    CSGData data;
    CSGWorkspace workspace;

    public RemoveProjectTeam_Transaction(ObservableList<ProjectTeamItems> projectItems, ProjectTeamItems projectTeams, ObservableList<ProjectTeamExport> projectExport, 
            ProjectTeamExport export, CSGData data,CSGWorkspace workspace) {
        this.projectItems = projectItems;
        this.projectTeams = projectTeams;
        this.export = export;
        this.projectExport = projectExport;
        this.data = data;
        this.workspace = workspace;
    }

    @Override
    public void doTransaction() {
        projectItems.remove(projectTeams);
        projectExport.remove(export);
        for(int i = 0; i < data.getProjectsData().getProjectsStudentTable().size(); i++){
            ProjectStudentItems student = (ProjectStudentItems) data.getProjectsData().getProjectsStudentTable().get(i);
            TableView studentTable = workspace.getProjectWorkspace().getStudentsTeamTable();
            if (projectTeams.getTeamName().equalsIgnoreCase(student.getTeam())) {
                    studentTable.getItems().remove(i);
                    studentTable.refresh();
                } 
        }
    }

    @Override
    public void undoTransaction() {
        projectItems.add(projectTeams);
        projectExport.add(export);
          for(int i = 0; i < data.getProjectsData().getProjectsStudentTable().size(); i++){
            ProjectStudentItems student = (ProjectStudentItems) data.getProjectsData().getProjectsStudentTable().get(i);
            ObservableList<ProjectStudentItems> studentItems = data.getProjectsData().getProjectsStudentTable();
            TableView studentTable = workspace.getProjectWorkspace().getStudentsTeamTable();
            studentItems.add(student);
        }
    }
}
