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
public class AddProjectsTeam_Transaction implements jTPS_Transaction {

    ObservableList<ProjectTeamItems> projectItems;
    ProjectTeamItems projectTeams;

    public AddProjectsTeam_Transaction(ObservableList<ProjectTeamItems> projectItems, ProjectTeamItems projectTeams) {
        this.projectItems = projectItems;
        this.projectTeams = projectTeams;
    }

    @Override
    public void doTransaction() {
        projectItems.add(projectTeams);

    }

    @Override
    public void undoTransaction() {
        projectItems.remove(projectTeams);
    }

}
