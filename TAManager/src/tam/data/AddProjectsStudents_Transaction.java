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
public class AddProjectsStudents_Transaction implements jTPS_Transaction{
  ObservableList<ProjectStudentItems> projectItems;
    ProjectStudentItems projectStudents;

    public AddProjectsStudents_Transaction( ObservableList<ProjectStudentItems> projectItems, ProjectStudentItems projectStudents) {
        this.projectItems = projectItems;
        this.projectStudents = projectStudents;
    }

    @Override
    public void doTransaction() {
        projectItems.add(projectStudents);

    }

    @Override
    public void undoTransaction() {
        projectItems.remove(projectStudents);
    }

    
}
